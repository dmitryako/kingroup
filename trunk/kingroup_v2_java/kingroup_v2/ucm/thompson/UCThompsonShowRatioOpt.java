package kingroup_v2.ucm.thompson;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.like.thompson.view.ThompsonRatioOptView;
import kingroup_v2.like.thompson.view.ThompsonRatioView;
import kingroup_v2.pedigree.view.PedigreeMainUI;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 10:51:05
 */
public class UCThompsonShowRatioOpt
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
    ThompsonRatioOptView optView = new ThompsonRatioOptView(project);

    // THIS IS TRICKY:
    // UCKinshipCalcLikeMtrx is run on APPLY
    // when it finishes, it must update table view
    UCThompsonCalcRatioMtrx calcRatio = new UCThompsonCalcRatioMtrx(optView);
    ThompsonRatioView ratioView = new ThompsonRatioView(optView, calcRatio);
    calcRatio.setViewForUpdate(ratioView);
    pedigreeUI.setThompsonRatioView(ratioView);

    ratioView.setTableView(new JTable());
    ratioView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    ratioView.setFocusOnApply();

    return true;
  }
}