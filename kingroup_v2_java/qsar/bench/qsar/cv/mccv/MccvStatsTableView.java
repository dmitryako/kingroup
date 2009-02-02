package qsar.bench.qsar.cv.mccv;
import pattern.mvc.MVCTableView;

import javax.iox.table.Table;
import javax.stats.StatsRes;
import javax.swing.*;
import javax.textx.FractionDigitsModel;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 14:41:15
 */
public class MccvStatsTableView  extends MVCTableView {
  private static String EMPTY = " ";
  private final Table table;
  private FractionDigitsModel formater;

  public MccvStatsTableView(String name, StatsRes stats, Table z, FractionDigitsModel format) {
    table = z;
    this.formater = format;

    JTable t = makeTableView(stats, z, name);
    assemble(t);
  }
  private JTable makeTableView(StatsRes stats, Table t, String name) {
//    int nRows = 7;
//    int nCols = 2;
//    String[][] rowData = new String[nRows][nCols];
//    String[] colNames = new String[nCols];
//    StrVec.set(rowData, EMPTY);
//    StrVec.set(colNames, EMPTY);
//    rowData[0][0] = t.getName();
//
//    int c = 0;
//    colNames[c] = "statistic";
//    colNames[c + 1] = name;
//
//    int r = 0;
//    rowData[r][c] = "data set:";
//    rowData[r][c+1] = t.getName();
//
//    r++;
//    rowData[r][c] = colNames[c];
//    rowData[r][c + 1] = colNames[c + 1];
//
//    r++;
//    rowData[r][c] = "RMSE";
//    rowData[r][c + 1] = formater.format(stats.getRmse());
//
//    r++;
//    rowData[r][c] = "MAE";
//    rowData[r][c + 1] = formater.format(stats.getMae());
//
//    r++;
//    rowData[r][c] = "s";
//    rowData[r][c + 1] = formater.format(stats.getS());
//
//    r++;
//    rowData[r][c] = "s^2";
//    rowData[r][c + 1] = formater.format(stats.getS2());
//
//    r++;
//    rowData[r][c] = "MSE";
//    rowData[r][c + 1] = formater.format(stats.getMse());
//
//    return new JTable(new ReadOnlyTableModel(rowData, colNames));
    return null;
  }

  public Table getTable() {
    return table;
  }
}

