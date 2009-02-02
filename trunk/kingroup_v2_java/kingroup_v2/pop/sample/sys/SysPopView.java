package kingroup_v2.pop.sample.sys;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;
import java.util.Observable;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/09/2005, Time: 10:57:33
 */
public class SysPopView extends MVCTableView {
  private static String EMPTY = " ";
  private static final String ALLELE_DELIM = " / ";
  private final SysPop sysPopSample;
  private static int count = 0;
  private static final int ID_IDX = count++;
  private static final int GROUP_ID_IDX = count++;
  private static final int MAT_ID_IDX = count++;
  private static final int PAT_ID_IDX = count++;
  private static final int N_INFO_COLS = count;
  private static final String LOC = "loc";
  private static final String MAT = "mat";
  private static final String PAT = "pat";
  private static final String ID = "idx";
  private static final String GROUP_ID = "grp";

  public SysPopView(SysPop pop) {
    sysPopSample = pop;
    JTable t = makeTableView(pop);
    assemble(t);
//    pop.addObserver(this);
  }
  public void update(Observable o, Object arg) {
//    SysAlleleFreq model = (SysAlleleFreq)o;
    repaint();
  }
  private JTable makeTableView(SysPop model) {
    StringBuffer buff = new StringBuffer();
    int nL = model.getNumLoci();
    int n = model.size();
    int nRows = sizeToNumRows(n);
    int nCols = sizeToNumCols(nL);
    //rowData is an array of rows, so the value of the cell at row 1, column 5 can be obtained with the following code:
    //rowData[1][5]; All rows must be of the same length as columnNames.
    String[][] rowData = new String[nRows][nCols];
    String[] columnNames = new String[nCols];
    StrMtrx.set(rowData, EMPTY);
    StrVec.set(columnNames, EMPTY);
    for (int col = 0; col < nCols; col++) {
      int L = colToLocusIdx(col);
      if (L >= 0)
        columnNames[col] = LOC + (L+1);
      if (col == ID_IDX)
        columnNames[col] = ID;
      if (col == GROUP_ID_IDX)
        columnNames[col] = GROUP_ID;
      if (col == MAT_ID_IDX)
        columnNames[col] = MAT;
      if (col == PAT_ID_IDX)
        columnNames[col] = PAT;
      for (int row = 0; row < nRows; row++) {
        int idx = rowToIdx(row);
        if (idx >= 0 && L >= 0) {
          buff.setLength(0);
          int ma = model.getAllele(idx, L, model.MAT);
          int pa = model.getAllele(idx, L, model.PAT);
          buff.append(Integer.toString(ma)).append(ALLELE_DELIM);
          buff.append(Integer.toString(pa));
          rowData[row][col] = buff.toString();
        }
        if (col == ID_IDX && idx >= 0)
          rowData[row][col] = Integer.toString(model.getIdIdx(idx));
        if (col == GROUP_ID_IDX && idx >= 0)
          rowData[row][col] = Integer.toString(model.getGroupId(idx));
        if (col == MAT_ID_IDX && idx >= 0)
          rowData[row][col] = Integer.toString(model.getMatId(idx));
        if (col == PAT_ID_IDX && idx >= 0)
          rowData[row][col] = Integer.toString(model.getPatId(idx));
      }
    }
    return new JTable(new ReadOnlyTableModel(rowData, columnNames));
  }
  private int colToLocusIdx(int col) {
    return col - N_INFO_COLS;
  }
  private int rowToIdx(int row) {
    return row;
  }
  private int sizeToNumCols(int nL) {
    return nL + N_INFO_COLS;
  }
  private int sizeToNumRows(int n) {
    return n;
  }
  public SysPop getSysPop() {
    return sysPopSample;
  }
}

