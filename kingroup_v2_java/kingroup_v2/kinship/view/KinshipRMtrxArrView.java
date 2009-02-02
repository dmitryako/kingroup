package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.relatedness.PairwiseRMtrx;
import pattern.mvc.MVCTableView;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/02/2006, Time: 11:33:02
 */
public class KinshipRMtrxArrView extends MVCTableView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRMtrxArrView.class.getName());
  public KinshipRMtrxArrView(PairwiseRMtrx[] mtrxArr, Kinship kinship, String tag)
  {
    KinshipRMtrxView[] views = new KinshipRMtrxView[mtrxArr.length];
    for (int i = 0; i < mtrxArr.length; i++) {
      views[i] = new KinshipRMtrxView(mtrxArr[i], kinship, tag);
    }
    JTable t = makeTableView(views);
    assemble(t);
  }
  public KinshipRMtrxArrView() {
  }
}
