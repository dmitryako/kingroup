package qsar.bench.qsar.table;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.textx.FractionDigitsModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/06/2007, 16:25:31
 */
public class MlrCoeffsView extends MVCTableView
{
  private int SHIFT  = 0;
  public MlrCoeffsView(String title, double[] arr, FractionDigitsModel formatter)
  {
    this(0, title, arr, formatter);
  }
  public MlrCoeffsView(int shift, String title, double[] arr, FractionDigitsModel formatter)
  {
    SHIFT = shift;
    JTable t = makeTableView(shift, title, arr, formatter);
    assemble(t);
  }
  public JTable makeTableView(int shift, String name, double[] arr
    , FractionDigitsModel formatter) {
    if (arr == null)
      return null;
    int nRows = 2;
    int nCols = arr.length;
    String[][] rowData = new String[nRows][colToView(nCols)];
    String[] colNames = new String[colToView(nCols)];
    StrMtrx.set(rowData, EMPTY);
    StrVec.set(colNames, EMPTY);
    rowData[0][0] = "#";
    rowData[1][0] = name;
    for (int c = 0; c < nCols; c++) {
      colNames[colToView(c)] = "b_" + c;
      rowData[0][colToView(c)] = colNames[colToView(c)];
      rowData[1][colToView(c)] = formatter.format(arr[c]);
    }
    return new JTable(new ReadOnlyTableModel(rowData, colNames));
  }

  private int colToView(int c)
  {
    return c + 1 + SHIFT;
  }
}



