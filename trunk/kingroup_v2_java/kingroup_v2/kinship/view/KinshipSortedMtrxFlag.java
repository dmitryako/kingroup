package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipRatioSimTable;
import kingroup_v2.pop.sample.sys.SysPopMtrxI;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/04/2006, Time: 13:24:44
 */
public class KinshipSortedMtrxFlag extends KinshipSortedMtrxView
{
  private final KinshipRatioSimTable sigTable;
  public KinshipSortedMtrxFlag(SysPopMtrxI m, Kinship kinship
    , KinshipRatioSimTable sigTable, String tag) {
    this.sigTable = sigTable;
    this.mtrx = m;
    this.kinship = kinship;
    JTable t = makeTableView(m, tag);
    assemble(t);
  }
  protected String loadValue(double logVal) {
    if (kinship.getDisplaySigFlag())
      return sigTable.toFlag(logVal);
    else        {
      throw new RuntimeException("use KinshipSortedMtrxView to display sorted p-values");
    }
  }
  protected void loadMeanValue(String[][] rowData, double avr) {}
//  protected void loadMeanCol(String[][] rowData, int r, double v) {}
//  protected void loadMeanDataCol(String[][] rowData, String tag) {}
//  protected void loadMeanCol(String[] columnNames, String tag){}
//  protected int getNCol() {return 3;}
}
