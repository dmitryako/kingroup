package kingroup_v2.ucm.cervus;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.cervus.AlleleAnalysisByCol;
import kingroup_v2.cervus.AlleleAnalysisByRow;
import kingroup_v2.cervus.Cervus;
import kingroup_v2.cervus.view.AlleleAnalysisOptView;
import kingroup_v2.cervus.view.AlleleAnalysisTableView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.ucm.kinship.UCKinshipCalcLikeMtrx;
import pattern.mvc.MVCTableView;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.tablex.TableViewWithOpt;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/03/2006, Time: 10:09:42
 */
public class UCRunAlleleAnalysis implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCKinshipCalcLikeMtrx.class.getName());
  private TableViewWithOpt updateView;
  private final AlleleAnalysisOptView optView;
  public UCRunAlleleAnalysis(AlleleAnalysisOptView optView)
  {
    this.optView = optView;
  }

  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    Cervus cervus = project.getCervus();
    optView.loadTo(cervus);
    project.saveProjectToDefaultLocation();
    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
    SysPop pop = mainUI.getSysPop();
//    UsrPopView popView = mainUI.getUsrPopView();
    MVCTableView view = null;
    AlleleAnalysisByRow m;
    if (cervus.getLociByCol())
      m = new AlleleAnalysisByCol(pop, cervus);
    else
      m = new AlleleAnalysisByRow(pop, cervus);

    view = new AlleleAnalysisTableView(m, cervus);
    if (updateView != null) {
      updateView.setTableView(view.getTableView());
      updateView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    }
    return true;
  }
  public void setViewForUpdate(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}
