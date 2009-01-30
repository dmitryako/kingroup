package javax.iox.table;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.textx.FractionDigitsModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;
import java.awt.*;

/**
 * Copyright www.DmitryKonovalov.org
 * User: jc138691, Date: 6/03/2007, Time: 15:18:30
 */
public class TableView  extends MVCTableView {
  private static String EMPTY = " ";
  private Table table;
  private ProgressWnd progress = null;

  public TableView(Table pop, TableDisplayOpt opt) {
    loadFrom(pop, opt);
  }

  public void loadFrom(Table table, TableDisplayOpt opt) {
    this.table = table;
    JTable t = makeTableView(table, opt, null);
    assemble(t);
  }
  public JTable makeTableView(TableDisplayOpt opt, Component parent) {
    return makeTableView(table, opt, parent);
  }
  public JTable makeTableView(Table t, TableDisplayOpt opt
    , Component parent) {
    FractionDigitsModel formater = opt.getDigitsModel();
    progress = new ProgressWnd(parent, "Loading matrix view");
    int nRows = t.getNumRows();
    int nCols = t.getNumCols();
    nRows = opt.calcMaxNumRows(nRows);
    nCols = opt.calcMaxNumCols(nCols);

    String[][] rowData = new String[rowToView(nRows)][colToView(nCols)];
    String[] colNames = new String[colToView(nCols)];
    StrMtrx.set(rowData, EMPTY);
    StrVec.set(colNames, EMPTY);
    rowData[0][0] = t.getName();
    for (int c = 0; c < nCols; c++) {
      if (progress != null
        && progress.isCanceled(c, 0, nCols)) {
        return new JTable();
      }
      if (opt.getCountOn())
        colNames[colToView(c)] = "" + (c+1) + "." + t.getColId(c);
      else
        colNames[colToView(c)] = t.getColId(c);
      rowData[0][colToView(c)] = colNames[colToView(c)];
      for (int r = 0; r < nRows; r++) {
        if (c == 0) {
          if (opt.getCountOn())
            rowData[rowToView(r)][c] = "" + (r+1) + "." + t.getRowId(r);
          else
            rowData[rowToView(r)][c] = t.getRowId(r);
        }
        rowData[rowToView(r)][colToView(c)] = formater.format(table.get(r, c));
      }
    }
    if (progress != null)
      progress.close();
    return new JTable(new ReadOnlyTableModel(rowData, colNames));
  }

  private int rowToView(int r)
  {
    return r + 1;
  }

  private int colToView(int c)
  {
    return c + 1;
  }

  public Table getTable() {
    return table;
  }

}


