package kingroup_v2.ucm.fsr.accuracy;
import kingroup.KinGroupError;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.fsr.FsrAlgFactory;
import kingroup_v2.fsr.accuracy.FsrAccuracyOptionsView;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 07:40:15
 */
public class UCFsrRunAccuracy implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCFsrRunAccuracy.class.getName());

  private TableViewWithOpt updateView;
  private final FsrAccuracyOptionsView optionsView;
  private final FsrAlgFactory algFactory;

  public UCFsrRunAccuracy(FsrAccuracyOptionsView optionsView, FsrAlgFactory alg)
  {
    this.optionsView = optionsView;
    algFactory = alg;
  }
  public boolean run() {
//    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
//    SysPop sysPop = ui.getSysPop();
//    if (sysPop == null) {
//      ui.showMessageLoadPopulationFirst();
//      return;
//    }

    Kingroup project = KinGroupV2Project.getInstance();
    optionsView.loadTo(project);
    project.saveProjectToDefaultLocation();
    PopBuilderModel builderModel = project.getPopBuilder();

    int nTrials = project.getNumAccuracyTrials();
    double[] dist = new double[nTrials];
    int popSize = -1;
    for (int i = 0; i < nTrials; i++)
    {
      SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
//      sysPop = SysPopFactory.makeReshuffled(sysPop);
      if (popSize == -1)
        popSize = sysPop.size();
      else if (popSize != sysPop.size())
      {
        throw new KinGroupError("popSize != sysPop.size()");
      }
      PartitionAlg method = algFactory.makeAlg(sysPop, project);
      Partition partB = method.partition();
      Partition partA = PartitionFactory.makePartitionFrom(sysPop);
      dist[i] = new LitowDistance().distance(partA, partB);
    }
    Vec.mult(100./popSize, dist);
    StatsRes stats = new Stats(dist);
    optionsView.setAccurError(stats.getMean());
    optionsView.setAccurErrorStdDev(stats.getS());

//    JTable table;
//    if (project.getFsrUserView()) {
//      UsrPopSLOW userPop = ui.getUserPop();
//      UsrPopSLOW userPart = new UsrPopPart(partB, userPop);
//      if (project.getFsrSortByGroup())
//        userPart = new UsrPopByGroup(userPart);
//      UsrPopView userView = new UsrPopView(userPart, project.getKinshipFileFormat());
//      table = userView.getTable();
//    }else{
//      SysPop sysPart = new SysPopPart(partB, sysPop);
//      if (project.getFsrSortByGroup())
//        sysPart = new SysPopByGroup(sysPart);
//      SysPopView sysView = new SysPopView(sysPart);
//      table = sysView.getTable();
//    }
//    if (updateView != null) {
//      updateView.setTable(table);
//      updateView.assembleTableWithOptions();
//    }
    return true;
  }
  public void setUpdateView(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}
