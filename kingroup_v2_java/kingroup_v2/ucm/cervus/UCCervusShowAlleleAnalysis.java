package kingroup_v2.ucm.cervus;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.cervus.Cervus;
import kingroup_v2.cervus.view.AlleleAnalysisOptView;
import kingroup_v2.cervus.view.CervusAlleleAnalysisView;
import kingroup_v2.cervus.view.CervusMainUI;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/03/2006, Time: 09:22:29
 */
public class UCCervusShowAlleleAnalysis   implements UCController
{
  public boolean run()
  {
    Kingroup project = KinGroupV2Project.getInstance();
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW pop = ui.getUsrPop();
    if (pop == null  ||  pop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }
    CervusMainUI cervusUI = ui.getCervusMainUI();
    if (cervusUI == null) {
      cervusUI = new CervusMainUI();
      ui.setCervusMainUI(cervusUI);
    }
    Cervus model = project.getCervus();
    AlleleAnalysisOptView optView = new AlleleAnalysisOptView(model);

    // THIS IS TRICKY:
    // UCKinshipCalcLikeMtrx is run on APPLY
    // when it finishes, it must update table view
    UCRunAlleleAnalysis uc = new UCRunAlleleAnalysis(optView);
    CervusAlleleAnalysisView view = new CervusAlleleAnalysisView(optView, uc);
    uc.setViewForUpdate(view);
    cervusUI.setAlleleAnalysisView(view);

    view.setTableView(new JTable());
    view.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    view.setFocusOnApply();

//    optView.onByGroupChange(new UCClearTable(likeView));
    return true;
  }
}
