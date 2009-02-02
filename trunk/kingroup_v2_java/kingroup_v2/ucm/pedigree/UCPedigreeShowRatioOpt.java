package kingroup_v2.ucm.pedigree;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.pedigree.ratio.view.PedigreeRatioOptView;
import kingroup_v2.pedigree.ratio.view.PedigreeRatioView;
import kingroup_v2.pedigree.view.PedigreeMainUI;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.UCClearTable;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 12:56:47
 */
public class UCPedigreeShowRatioOpt
  implements UCController {
  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW pop = ui.getUsrPop();
    if (pop == null   ||  pop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }
    PedigreeMainUI pedigreeUI = ui.getPedigreeMainUI();
    if (pedigreeUI == null) {
      pedigreeUI = new PedigreeMainUI();
      ui.setPedigreeMainUI(pedigreeUI);
    }
    Kinship model = project.getKinship();
    model.setHasGroupId(pop.getHasGroupId());
    PedigreeRatioOptView optView = new PedigreeRatioOptView(project);

    // THIS IS TRICKY:
    // UCKinshipCalcLikeMtrx is run on APPLY
    // when it finishes, it must update table view
    UCPedigreeCalcRatioMtrx calcRatio = new UCPedigreeCalcRatioMtrx(optView);
    PedigreeRatioView ratioView = new PedigreeRatioView(optView, calcRatio);
    calcRatio.setViewForUpdate(ratioView);
    pedigreeUI.setPedigreeRatioView(ratioView);

    ratioView.setTableView(new JTable());
    ratioView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    ratioView.setFocusOnApply();

//    optView.onByGroupChange(new UCClearTable(likeView));
//    optView.onDisplayLogsChange(new UCClearTable(likeView));
////    optView.onDisplaySortedChange(new UCClearTable(likeView));
//    optView.onDisplaySigFlagChange(new UCClearTable(likeView));
//    optView.onDisplayPValueChange(new UCClearTable(likeView));
    optView.onIBDChange(new UCClearTable(ratioView));
    return true;
  }
}
