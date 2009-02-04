package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.relatedness.PairwiseRMtrx;
import pattern.mvc.MVCTableView;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 17:52:22
 */
public class KinshipRelatedSortedArrayView  extends MVCTableView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRMtrxArrView.class.getName());
  public KinshipRelatedSortedArrayView(PairwiseRMtrx[] mtrxArr, Kinship kinship, String tag)
  {
    KinshipSortedMtrxView[] views = new KinshipSortedMtrxView[mtrxArr.length];
    for (int i = 0; i < mtrxArr.length; i++) {
      if (i == 0) {
        
      }
      views[i] = new KinshipSortedMtrxView(mtrxArr[i], kinship, tag);
    }
    JTable t = makeTableView(views);
    assemble(t);

//    TableModel tm = t.getModel();
//    tm.getValueAt()
  }
}

