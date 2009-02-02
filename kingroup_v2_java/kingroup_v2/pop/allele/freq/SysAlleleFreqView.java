package kingroup_v2.pop.allele.freq;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;
import java.util.Observable;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/09/2005, Time: 09:29:18
 */
public class SysAlleleFreqView extends MVCTableView {
  private static final String HEADER = "loc";
  private SysAlleleFreq sysAlleleFreq;
  private static final String EMPTY = " ";
  public SysAlleleFreqView(SysAlleleFreq model) {
    sysAlleleFreq = model;
    JTable view = makeView(model);
    assemble(view);
//    model.addObserver(this);
  }
  public void update(Observable o, Object arg) {
//    SysAlleleFreq model = (SysAlleleFreq)o;
    repaint();
  }
  private JTable makeView(SysAlleleFreq model) {
    int nL = model.getNumLoci();
    int nA = model.getMaxNumAlleles();
    //rowData is an array of rows, so the value of the cell at row 1, column 5 can be obtained with the following code:
    //rowData[1][5]; All rows must be of the same length as columnNames.
    String[][] rowData = new String[nA][nL];
    String[] columnNames = new String[nL];
    StrMtrx.set(rowData, EMPTY);
    StrVec.set(columnNames, EMPTY);
    for (int L = 0; L < nL; L++) {
      columnNames[L] = HEADER + (L+1);
      for (int a = 0; a < model.getLocusSize(L); a++) {
        rowData[a][L] = Float.toString((float) model.getFreq(L, a));
      }
    }
    return new JTable(new ReadOnlyTableModel(rowData, columnNames));
  }
  public SysAlleleFreq getSysAlleleFreq() {
    return sysAlleleFreq;
  }
}
