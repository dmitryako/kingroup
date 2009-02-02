package kingroup_v2.ucm.kinship;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.view.KinshipRatioOptView;
import kingroup_v2.kinship.like.view.KinshipRatioView;
import kingroup_v2.kinship.view.KinshipMainUI;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.UCClearTable;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/09/2005, Time: 11:27:21
 */
public class UCKinshipShowRatioOpt implements UCController {
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
    KinshipRatioOptView optView = new KinshipRatioOptView(project);

    // THIS IS TRICKY:
    // UCKinshipCalcLikeMtrx is run on APPLY
    // when it finishes, it must update table view
    UCKinshipCalcRatioMtrx calcRatio = new UCKinshipCalcRatioMtrx(optView);
    KinshipRatioView ratioView = new KinshipRatioView(optView, calcRatio);
    calcRatio.setViewForUpdate(ratioView);
    likeUI.setKinshipRatioView(ratioView);

    ratioView.setTableView(new JTable());
    ratioView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    ratioView.setFocusOnApply();

    optView.onByGroupChange(new UCClearTable(ratioView));
    optView.onDisplayLogsChange(new UCClearTable(ratioView));
//    optView.onDisplaySortedChange(new UCClearTable(likeView));
    optView.onDisplaySigFlagChange(new UCClearTable(ratioView));
    optView.onDisplayPValueChange(new UCClearTable(ratioView));
    optView.onIBDChange(new UCClearTable(ratioView));
    optView.onTreatHaploidChange(new UCClearTable(ratioView));
    return true;
  }
}
