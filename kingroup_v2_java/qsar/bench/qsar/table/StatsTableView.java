package qsar.bench.qsar.table;
import pattern.mvc.MVCTableView;

import javax.iox.table.Table;
import javax.stats.StatsRes;
import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.textx.FractionDigitsModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/03/2007, 11:40:51
 */
public class StatsTableView extends MVCTableView {
  private static String EMPTY = " ";
  private final Table table;
  private FractionDigitsModel formater;

  public StatsTableView(String name, StatsRes stats, Table z, FractionDigitsModel format) {
    table = z;
    this.formater = format;

    JTable t = makeTableView(stats, z, name);
    assemble(t);
  }
  private JTable makeTableView(StatsRes stats, Table t, String name) {
    int nRows = 16;
    int nCols = 2;
    String[][] rowData = new String[nRows][nCols];
    String[] colNames = new String[nCols];
    StrMtrx.set(rowData, EMPTY);
    StrVec.set(colNames, EMPTY);
    rowData[0][0] = t.getName();

    int c = 0;
    colNames[c] = "statistic";
    colNames[c + 1] = name;

    int r = 0;
    rowData[r][c] = "data set:";
    rowData[r][c+1] = t.getName();

    r++;
    rowData[r][c] = colNames[c];
    rowData[r][c + 1] = colNames[c + 1];

    r++;
    rowData[r][c] = "robust corr";
    rowData[r][c + 1] = formater.format(stats.getRobCorr());

    r++;
    rowData[r][c] = "corr";
    rowData[r][c + 1] = formater.format(stats.getCorr());

    r++;
    rowData[r][c] = "robust R^2";
    rowData[r][c + 1] = formater.format(stats.getRobR2());

    r++;
    rowData[r][c] = "R^2=1-SSE/SST";
    rowData[r][c + 1] = formater.format(stats.getR2());

    r++;
    rowData[r][c] = "Adjusted R^2=1-MSE/MST";
    rowData[r][c + 1] = formater.format(stats.getRa2());

    r++;
    rowData[r][c] = "MedAE";
    rowData[r][c + 1] = formater.format(stats.getMedAE());

//    r++;
//    rowData[r][c] = "MAD";
//    rowData[r][c + 1] = formater.format(stats.getMad());
//
    r++;
    rowData[r][c] = "F=MSR/MSE";
    rowData[r][c + 1] = formater.format(stats.getF());

    r++;
    rowData[r][c] = "s=sqrt(MSE)";
    rowData[r][c + 1] = formater.format(stats.getS());

    r++;
    rowData[r][c] = "RMSEP=sqrt(MSEP)";
    rowData[r][c + 1] = formater.format(stats.getRmse());

    r++;
    rowData[r][c] = "MAE";
    rowData[r][c + 1] = formater.format(stats.getMae());

    r++;
    rowData[r][c] = "MSE=SSE/(n-p-1)";
    rowData[r][c + 1] = formater.format(stats.getS2());

    r++;
    rowData[r][c] = "MSEP=SSE/n";
    rowData[r][c + 1] = formater.format(stats.getMse());

    r++;
    rowData[r][c] = "MSR=SSR/p";
    rowData[r][c + 1] = formater.format(stats.getMsr());

    r++;
    rowData[r][c] = "MST=SST/(n-1)";
    rowData[r][c + 1] = formater.format(stats.getMst());

    return new JTable(new ReadOnlyTableModel(rowData, colNames));
  }

  public Table getTable() {
    return table;
  }
}

