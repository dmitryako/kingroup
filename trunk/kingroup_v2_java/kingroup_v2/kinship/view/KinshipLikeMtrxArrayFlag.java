package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipLogMtrx;
import kingroup_v2.kinship.like.KinshipRatioSimTable;

import javax.swing.*;
import javax.swing.table.TableColumn;
import tsvlib.project.ProjectLogger;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/10/2005, Time: 12:16:51
 */
public class KinshipLikeMtrxArrayFlag extends KinshipLikeMtrxArrView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipLikeMtrxArrayFlag.class.getName());
  public KinshipLikeMtrxArrayFlag(KinshipLogMtrx[] mtrxArr, Kinship kinship
    , KinshipRatioSimTable sigTable) {
    KinshipLikeMtrxView[] views = new KinshipRatioMtrxFlag[mtrxArr.length];
    for (int i = 0; i < mtrxArr.length; i++) {
      views[i] = new KinshipRatioMtrxFlag(mtrxArr[i], kinship, sigTable);
    }
    JTable t = makeTableView(views);

    TableColumn col = t.getColumnModel().getColumn(0);
    if (kinship.getDisplaySigFlag())
      col.setHeaderValue("FLAG");
    else
      col.setHeaderValue("P-VALUES");
    
    assemble(t);
  }
}
