package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipLogMtrx;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 10:54:35
 */
public class KinshipLikeMtrxArrView extends MVCTableView {
//  private static ProjectLogger log = ProjectLogger.getLogger(KinshipLikeMtrxArrView.class.getName());
  public KinshipLikeMtrxArrView(KinshipLogMtrx[] mtrxArr, Kinship kinship) {
    MVCTableView[] views = new MVCTableView[mtrxArr.length];
    for (int i = 0; i < mtrxArr.length; i++) {
      views[i] = new KinshipLikeMtrxView(mtrxArr[i], kinship);
    }
    JTable t = makeTableView(views);
    TableColumn col = t.getColumnModel().getColumn(0);
    if (kinship.getDisplayLogs())
      col.setHeaderValue("Log10");
    else
      col.setHeaderValue("");
    assemble(t);
  }
  public KinshipLikeMtrxArrView() {
  }
  public KinshipLikeMtrxArrView(MVCTableView[] views, Kinship kinship) {
    JTable t = makeTableView(views);
    TableColumn col = t.getColumnModel().getColumn(0);
    if (kinship.getDisplayLogs())
      col.setHeaderValue("Log10");
    else
      col.setHeaderValue("");
    assemble(t);
  }
}
