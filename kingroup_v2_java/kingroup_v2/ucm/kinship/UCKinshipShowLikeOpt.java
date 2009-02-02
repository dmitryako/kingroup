package kingroup_v2.ucm.kinship;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.view.KinshipLikeOptView;
import kingroup_v2.kinship.view.KinshipLikeView;
import kingroup_v2.kinship.view.KinshipMainUI;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.UCClearTable;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2005, Time: 13:40:25
 */
public class UCKinshipShowLikeOpt implements UCController {
  private final boolean prim;
  public UCKinshipShowLikeOpt(boolean prim) {
    this.prim = prim;
  }
  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW pop = ui.getUsrPop();
    if (pop == null   ||  pop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }
    KinshipMainUI likeUI = ui.getKinshipMainUI();
    if (likeUI == null) {
      likeUI = new KinshipMainUI();
      ui.setKinshipMainUI(likeUI);
    }

    Kinship model = project.getKinship();
    model.setHasGroupId(pop.getHasGroupId());
    KinshipLikeOptView optView = new KinshipLikeOptView(prim, project);
    UCKinshipCalcLikeMtrx runOnApply = new UCKinshipCalcLikeMtrx(optView);
    KinshipLikeView likeView;
    if (prim) {
      // THIS IS TRICKY:
      // UCKinshipCalcLikeMtrx is run on APPLY
      // when it finishes, it must update the table view
      likeView = new KinshipLikeView(optView, runOnApply);
      likeUI.setKinshipPrimView(likeView);
    } else {
      likeView = new KinshipLikeView(optView, runOnApply);
      likeUI.setKinshipNullView(likeView);
    }
    runOnApply.setViewForUpdate(likeView);

    likeView.setTableView(new JTable());
    likeView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    likeView.setFocusOnApply();

    optView.onByGroupChange(new UCClearTable(likeView));
    optView.onDisplayLogsChange(new UCClearTable(likeView));
//    optView.onDisplaySortedChange(new UCClearTable(likeView));
    optView.onIBDChange(new UCClearTable(likeView));
    optView.onTreatHaploidChange(new UCClearTable(likeView));
    return true;
  }
}
