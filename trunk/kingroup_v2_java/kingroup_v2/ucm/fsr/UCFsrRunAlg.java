package kingroup_v2.ucm.fsr;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.fsr.DisplayOptView;
import kingroup_v2.fsr.FsrAlgFactory;
import kingroup_v2.fsr.FsrAlgOptView;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swingx.tablex.TableViewWithOpt;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/10/2005, Time: 17:38:36
 */
public class UCFsrRunAlg implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCFsrRunAlg.class.getName());

  private TableViewWithOpt updateView;
  private final FsrAlgOptView optView;
  private final FsrAlgFactory algFactory;

  public UCFsrRunAlg(FsrAlgOptView optionsView, FsrAlgFactory alg) {
    this.optView = optionsView;
    algFactory = alg;
  }
  public boolean run() {
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    SysPop sysPop = ui.getSysPop();
    if (sysPop == null   ||  sysPop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }

    Kingroup project = KinGroupV2Project.getInstance();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    PartitionAlg method = algFactory.makeAlg(sysPop, project);
    Partition partB = method.partition();
    UsrPopSLOW usrPop = ui.getUsrPop();
    DisplayOptView.updateView(updateView, partB, sysPop, usrPop, project);
    return true;
  }
  public void setUpdateView(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}
