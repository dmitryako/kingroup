package kingroup_v2.kinship.view;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipREstimator;
import kingroup_v2.pop.sample.usr.UsrPopView;
import kingroup_v2.relatedness.PairwiseRMtrx;
import pattern.mvc.MVCTableView;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.swingx.tablex.JTableFactory;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/01/2006, Time: 12:23:22
 */
public class KinshipRMtrxView extends MVCTableView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRMtrxView.class.getName());
  protected PairwiseRMtrx mtrx;
  protected Kinship kinship;
  private ProgressWnd progress = null;
  private static final int MAX_N_ROWS = 100;

  public KinshipRMtrxView(PairwiseRMtrx m, Kinship kinship, String tag) {
//    log.setLevel(Level.OFF);
    this.mtrx = m;
    this.kinship = kinship;
    JTable t = makeTableView(m, tag);
    assemble(t);
  }
  public KinshipRMtrxView() {
  }
  protected JTable makeTableView(PairwiseRMtrx mtrx, String tag) {
    progress = new ProgressWnd(KingroupFrame.getInstance(), "Loading matrix view");
    UsrPopView popView = KinGroupV2MainUI.getInstance().getUsrPopView();
    int n = mtrx.size();
    int nRow = sizeToNumRow(n);
    int nCol = sizeToNumCol(n);
        
    if (nRow > sizeToNumRow(MAX_N_ROWS)) {
      KinGroupV2MainUI.getInstance().setStatus(" !!!NOTE!!! Displayed only "+MAX_N_ROWS+" out of "+n+" rows.");
      nRow = sizeToNumRow(MAX_N_ROWS);
      nCol = sizeToNumCol(MAX_N_ROWS);
    }

    String[][] rowData = new String[nRow][nCol];
    StrMtrx.set(rowData, JTableFactory.EMPTY);
    String[] columnNames = new String[nCol];
    StrVec.set(columnNames, JTableFactory.EMPTY);
    if (mtrx.getName() != null && mtrx.getName().length() > 0)
      rowData[0][0] = "group: " + mtrx.getName();
    for (int c = 0; c < nCol; c++) {
      if (progress != null
        && progress.isCanceled(c, 0, nCol)) {
        return new JTable();
      }
      int idCol = columnToIdIdx(c); // actual id in columns
      if (idCol != -1) {
        columnNames[c] = popView.getId(mtrx.getId(idCol));
        rowData[0][c] = popView.getId(mtrx.getId(idCol));
      }
      for (int r = 0; r < nRow; r++) {
        int idRow = rowToIdIdx(r);
        if (idRow <= -1)
          continue;
        if (idCol > -1)
          rowData[r][c] = makeStringFor(idRow, idCol);
        else
          rowData[r][c] = popView.getId(mtrx.getId(idRow));
        if (idRow == 0  && idRow == idCol)
          rowData[r][c] = tag;
      }
    }
    if (progress != null)
      progress.close();
    return new JTable(new ReadOnlyTableModel(rowData, columnNames));
  }
  protected int sizeToNumCol(int n) {
    return n + 1;
  }
  protected int sizeToNumRow(int n) {
    return n + 1; //one row as a duplicate header
  }
  protected int rowToIdIdx(int r) {
    return r - 1;
  }
  protected int columnToIdIdx(int c) {
    return c - 1;
  }
  protected String makeStringFor(int i, int k) {
//    if (i == k) {
//      return toStringFromSorted(i, k);
//    }
    if (!kinship.getDisplayHalfMatrix()) {
      return toString(i, k);
    }
    if (k < i) {
      return toString(i, k);
    }
    return JTableFactory.EMPTY;
  }
  protected String toString(int i, int k) {
//    // log.info("i="+i+", k="+k);
    if (i == k)
      return JTableFactory.EMPTY;
    double d = mtrx.get(i, k);
//    if (!kinship.getDisplayNegR() && d < 0)
//      return "0";
    if (d == KinshipREstimator.ERROR_VALUE)
      return "x";
    return kinship.format(d);
  }
}