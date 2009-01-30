package javax.iox;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 30/03/2007, 10:14:02
 */
public class StrTableView extends MVCTableView {
  private static String EMPTY = " ";
  private final StrTable table;

  public StrTableView(StrTable pop) {
    table = pop;
    JTable t = makeTableView(pop);
    assemble(t);
  }
  private JTable makeTableView(StrTable t) {
    int nRows = t.getNumRows();
    int nCols = t.getNumCols();
    String[][] rowData = new String[rowToView(nRows)][colToView(nCols)];
    String[] colNames = new String[colToView(nCols)];
    StrMtrx.set(rowData, EMPTY);
    StrVec.set(colNames, EMPTY);
    rowData[0][0] = t.getName();
    for (int c = 0; c < nCols; c++) {
      colNames[colToView(c)] = t.getColId(c);
      rowData[0][colToView(c)] = t.getColId(c);
      for (int r = 0; r < nRows; r++) {
        if (c == 0)
          rowData[rowToView(r)][c] = t.getRowId(r);
        rowData[rowToView(r)][colToView(c)] = table.get(r, c);
      }
    }
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

  public StrTable getTable() {
    return table;
  }
}
