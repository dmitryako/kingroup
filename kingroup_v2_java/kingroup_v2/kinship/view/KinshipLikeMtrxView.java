package kingroup_v2.kinship.view;
import kingroup.genetics.Like;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.pop.sample.sys.SysPopMtrxI;
import kingroup_v2.pop.sample.usr.UsrPopView;
import pattern.mvc.MVCTableView;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swingx.ProgressWnd;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 09:00:59
 */
public class KinshipLikeMtrxView extends MVCTableView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipLikeMtrxView.class.getName());
  protected SysPopMtrxI mtrx;
  protected Kinship kinship;
  private ProgressWnd progress = null;
  private int MAX_N_ROWS = 100;
  private int MAX_N_COLS = 100;

  public KinshipLikeMtrxView(SysPopMtrxI m, Kinship kinship) {
//    log.setLevel(Level.OFF);
    this.mtrx = m;
    this.kinship = kinship;
    JTable t = makeTableView(m);

    TableColumn col = t.getColumnModel().getColumn(0);
    if (kinship.getDisplayLogs())
      col.setHeaderValue("Log10");
    else
      col.setHeaderValue("");
    assemble(t);
  }
  public KinshipLikeMtrxView() {
  }
  protected JTable makeTableView(SysPopMtrxI mtrx) {
    progress = new ProgressWnd(KingroupFrame.getInstance(), "Loading matrix view");
    if (kinship.getDisplaySorted()) {
//      sort();
    }
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
    StrMtrx.set(rowData, EMPTY);
    String[] columnNames = new String[nCol];
    StrVec.set(columnNames, EMPTY);
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
    return EMPTY;
  }
  protected String toString(int i, int k) {
//    // log.info("i="+i+", k="+k);
    if (i == k)
      return EMPTY;
    double d = mtrx.get(i, k);
    d = kinship.logToView(d);
    if (d == Like.MAX_LOG)
      return "max";
    if (d == Like.MIN_LOG)
      return "x";
    return kinship.format(d);
//    return kinship.formatLog(d);
  }
}