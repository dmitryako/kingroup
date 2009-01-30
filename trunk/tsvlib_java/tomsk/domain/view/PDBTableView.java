package tomsk.domain.view;
import pattern.mvc.MVCTableView;
import tomsk.io.pdb.PDBAtom;
import tomsk.io.pdb.PDBModel;

import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.textx.FractionDigitsModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 8/05/2007, 14:40:02
 */
public class PDBTableView  extends MVCTableView {
  private static String EMPTY = " ";
//  private Mol mol;
  private FractionDigitsModel formater;
  private static final int HEADER_N_ROWS = 3;
  private static final int N_COLS = 5;

  public PDBTableView(PDBModel mol, FractionDigitsModel format) {
    loadFrom(mol, format);
  }
  public void loadFrom(PDBModel mol, FractionDigitsModel format) {
//    this.mol = mol;
    this.formater = format;
    JTable t = makeTableView(mol);
    assemble(t);
  }
  private JTable makeTableView(PDBModel t) {
    int nRows = t.size() + HEADER_N_ROWS;
    int nCols = N_COLS;
    String[][] rowData = new String[nRows][nCols];
    String[] colNames = new String[nCols];
    StrMtrx.set(rowData, EMPTY);
    StrVec.set(colNames, EMPTY);

    int r = 0;
    rowData[r][0] = "TITLE";
    rowData[r][1] = t.getTitle().getText();
    r++;

    rowData[r][0] = "HEADER";
    rowData[r][1] = t.getHeader().getText();
    r++;

    int c = 0;
    colNames[c++] = "Name";
    colNames[c++] = "Serial#";
    colNames[c++] = "x";
    colNames[c++] = "y";
    colNames[c++] = "z";
    for (c = 0; c < colNames.length; c++) {
      rowData[r][c] = colNames[c];
    }
    r++;

    for (int i = 0; i < t.size(); i++, r++) { // NOTE! r++
      PDBAtom a = t.get(i);
      c = 0;
      rowData[r][c++] = a.getName();
      rowData[r][c++] = Integer.toString(a.getSerialNum());
      rowData[r][c++] = formater.format(a.getX());
      rowData[r][c++] = formater.format(a.getY());
      rowData[r][c++] = formater.format(a.getZ());
    }
//    rowData[0][0] = t.getName();
//    for (int c = 0; c < nCols; c++) {
//      colNames[colToView(c)] = t.getColId(c);
//      rowData[0][colToView(c)] = t.getColId(c);
//      for (int r = 0; r < nRows; r++) {
//        if (c == 0)
//          rowData[rowToView(r)][c] = t.getRowId(r);
//        rowData[rowToView(r)][colToView(c)] = formater.format(table.get(r, c));
//      }
//    }
    return new JTable(new ReadOnlyTableModel(rowData, colNames));
  }

}

