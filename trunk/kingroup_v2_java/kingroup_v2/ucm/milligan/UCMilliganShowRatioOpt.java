package kingroup_v2.ucm.milligan;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.like.milligan.view.MilliganRatioOptView;
import kingroup_v2.like.milligan.view.MilliganRatioView;
import kingroup_v2.pedigree.view.PedigreeMainUI;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/03/2006, Time: 12:40:03
 */
public class UCMilliganShowRatioOpt
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
    Kinship kinship = project.getKinship();
    kinship.setHasGroupId(pop.getHasGroupId());
    MilliganRatioOptView optView = new MilliganRatioOptView(project);

    // THIS IS TRICKY:
    // UCKinshipCalcLikeMtrx is run on APPLY
    // when it finishes, it must update table view
    UCMilliganCalcRatioMtrx calcRatio = new UCMilliganCalcRatioMtrx(optView);
    MilliganRatioView likeView = new MilliganRatioView(optView, calcRatio);
    calcRatio.setViewForUpdate(likeView);
    pedigreeUI.setMilliganRatioView(likeView);

    likeView.setTableView(new JTable());
    likeView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    likeView.setFocusOnApply();

    return true;
  }
}
