package kingroup_v2.kinship.view;
import kingroup.genetics.Like;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipRatioSimTable;
import kingroup_v2.pop.sample.sys.SysPopMtrxI;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swingx.tablex.JTableFactory;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/10/2005, Time: 10:17:54
 */
public class KinshipRatioMtrxFlag extends KinshipLikeMtrxView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRatioMtrxFlag.class.getName());
  private final KinshipRatioSimTable sigTable;
  public KinshipRatioMtrxFlag(SysPopMtrxI m, Kinship kinship
    , KinshipRatioSimTable sigTable) {
//    log.setLevel(Level.OFF);
    init();
    this.mtrx = m;
    this.kinship = kinship;
    if (sigTable == null || sigTable.getSigLevelLogs() == null) {
      assemble(new JTable());
    }
    this.sigTable = sigTable;
    JTable t = makeTableView(m);

    TableColumn col = t.getColumnModel().getColumn(0);
    if (kinship.getDisplaySigFlag())
      col.setHeaderValue("FLAG");
    else
      col.setHeaderValue("PVALUE");

    assemble(t);
  }
  private void init() {
  }
  protected String toString(int i, int k) {
    if (i == k)
      return JTableFactory.EMPTY;
    double logVal = mtrx.get(i, k);
    if (logVal == Like.MIN_LOG)
      return "x";
    if (logVal == Like.IGNORE)
      return " ";
    if (kinship.getDisplaySigFlag())
      return sigTable.toFlag(logVal);
    else {
      double pv = sigTable.toPValue(logVal);
      return sigTable.formatPValue(pv);
    }
  }
}
