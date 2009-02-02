package qsar.bench.qsar.cv.mcvs;
import pattern.mvc.MVCTableView;
import qsar.bench.QBench;

import javax.iox.table.Table;
import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.textx.FractionDigitsModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/06/2007, 13:59:04
 */
public class McvsItemView extends MVCTableView
{
  private static String EMPTY = " ";
  private static final int N_ROWS = 1;
  private static final int N_START_COLS = 0;
  private static final int N_END_COLS = 5;

  public McvsItemView(McvsItem res, Table zTable, QBench project, boolean showHeaders)
  {
    JTable t = makeTableView(res, zTable, project.getDigitsModel(), showHeaders);
    assemble(t);
  }

  public JTable makeTableView(McvsItem res, Table zTable
    , FractionDigitsModel formater
    , boolean showHeaders) {
    String[] colIds = zTable.getColIds();
    String[] resIds = StrVec.get(res, colIds);
    int nRows = N_ROWS;
    if (showHeaders)
      nRows++;
    int idx = nRows - 1;
    int k = resIds.length;
    int nCols = k + N_START_COLS + N_END_COLS;

    String[][] rowData = new String[nRows][nCols];
    StrMtrx.set(rowData, EMPTY);

    String[] colNames = new String[nCols];
    StrVec.set(colNames, EMPTY);
    colNames[N_START_COLS + k] = "F";
    colNames[N_START_COLS + k + 1] = "RMSEP";
    colNames[N_START_COLS + k + 2] = "selected";
    colNames[N_START_COLS + k + 3] = "P-value";
    colNames[N_START_COLS + k + 4] = "best";

//    rowData[0][0] = t.getName();
    for (int c = 0; c < k; c++) {
      colNames[c + N_START_COLS] = Integer.toString(c);
      rowData[idx][c + N_START_COLS] = resIds[c];
    }
    colNames[N_START_COLS] = "Y";

    if (showHeaders) {
      for (int c = 0; c < nCols; c++) {
        rowData[0][c] = colNames[c];
      }
    }

    rowData[idx][N_START_COLS + k] = formater.format(res.getLastF());
    rowData[idx][N_START_COLS + k + 1] = formater.format(Math.sqrt(res.getAvrLoss()));
    rowData[idx][N_START_COLS + k + 2] = "" + res.getCalcCount();
    rowData[idx][N_START_COLS + k + 3] = formater.format(res.getPVal());
    rowData[idx][N_START_COLS + k + 4] = "" + res.getBestCount();
    return new JTable(new ReadOnlyTableModel(rowData, colNames));
  }
}
