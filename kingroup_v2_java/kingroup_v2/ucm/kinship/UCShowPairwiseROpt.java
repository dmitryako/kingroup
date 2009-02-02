package kingroup_v2.ucm.kinship;

import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.view.PairwiseROptView;
import kingroup_v2.kinship.view.PairwiseRView;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.ucm.relatedness.UCCalcPairwiseR;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.UCClearTable;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/01/2006, Time: 16:25:25
 */
public class UCShowPairwiseROpt
  implements UCController {
  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW pop = ui.getUsrPop();
    if (pop == null   ||  pop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }

    Kinship model = project.getKinship();
    model.setHasGroupId(pop.getHasGroupId());
    PairwiseROptView optView = new PairwiseROptView(project);

    // THIS IS TRICKY:
    // UCKinshipCalcLikeMtrx is run on APPLY
    // when it finishes, it must update table view
    UCCalcPairwiseR calcRelated = new UCCalcPairwiseR(optView);
    PairwiseRView view = new PairwiseRView(optView, calcRelated);
    calcRelated.setViewForUpdate(view);
    ui.setPairwiseRView(view);

    view.setTableView(new JTable());
    view.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    view.setFocusOnApply();

    optView.onByGroupChange(new UCClearTable(view));
    optView.onPValueChange(new UCClearTable(view));
    return true;
  }
}
