package kingroup_v2.ucm.kinship;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipLikeCalculator;
import kingroup_v2.kinship.like.KinshipLikeMtrx;
import kingroup_v2.kinship.view.KinshipLikeMtrxArrView;
import kingroup_v2.kinship.view.KinshipLikeMtrxView;
import kingroup_v2.kinship.view.KinshipLikeOptView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.usr.UsrPopView;
import pattern.mvc.MVCTableView;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.tablex.TableViewWithOpt;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2005, Time: 15:25:44
 */
public class UCKinshipCalcLikeMtrx implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCKinshipCalcLikeMtrx.class.getName());
  private TableViewWithOpt updateView;
  private final KinshipLikeOptView optView;
  public UCKinshipCalcLikeMtrx(KinshipLikeOptView optView) {
    this.optView = optView;
  }
  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    Kinship kinship = project.getKinship();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();
    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
    SysPop pop = mainUI.getSysPop();
    UsrPopView popView = mainUI.getUsrPopView();
    MVCTableView view = null;
    if (kinship.getDisplayByGroup()) {
      SysPop[] arr = mainUI.getSysGroups();
      KinshipLikeMtrx[] mtrxArr = new KinshipLikeMtrx[arr.length];
      for (int i = 0; i < arr.length; i++) {
        // log.info("group =\n" + arr[i]);
        mtrxArr[i] = new KinshipLikeMtrx(arr[i]);
        KinshipLikeCalculator c = new KinshipLikeCalculator(arr[i]
          , optView.getKinshipIBDModel(kinship), kinship);
        if (!mtrxArr[i].calcLogs(c))
          return false;
        int idx = arr[i].getIdIdx(0);
        mtrxArr[i].setName(popView.getGroupId(idx));
      }
      view = new KinshipLikeMtrxArrView(mtrxArr, kinship);
    } else {
      KinshipLikeMtrx m = new KinshipLikeMtrx(pop);
      KinshipLikeCalculator c = new KinshipLikeCalculator(pop
        , optView.getKinshipIBDModel(kinship), kinship);
      if (!m.calcLogs(c))
        return false;
      view = new KinshipLikeMtrxView(m, kinship);
    }
    if (updateView != null) {
//      updateView.setTableView(view);
      updateView.setTableView(view.getTableView());
      updateView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    }
    return true;
  }
  public void setViewForUpdate(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}
