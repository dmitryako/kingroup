package qsar.bench.ucm.alg.cv;
import pattern.ucm.UCUpdateTable;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.cv.mccv.MccvAlg;
import qsar.bench.qsar.cv.mccv.MccvZView;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.swing.*;
import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.arrays.mtrx.Mtrx;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 10:33:44
 */
public class UCRunMCCV implements UCUpdateTable {
  private static ProjectLogger log = ProjectLogger.getLogger(UCRunMCCV.class);

  private TableViewWithOpt updateView;
  private final QBenchViewI optView;
  private final QsarAlgFactory algFactory;

  public UCRunMCCV(QBenchViewI optView, QsarAlgFactory alg) {
    this.optView = optView;
    algFactory = alg;
  }
  public boolean run() {
    log.trace("run()");
    QBench project = QBenchProject.getInstance();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      ui.showLoadTrainTableFirst();
      return false;
    }

    double[][] Z = zTrain.getMtrx();
    if (project.getQsarAlgId() == project.ALG_kNN  &&  project.getMeanZeroVarOne()) {
      double[] Y = zTrain.getColCopy(0);
      Z = Stats.makeColsMeanZeroVarOne(Z);
      Mtrx.setCol(0, Y, Z); // restore y
    }

    MccvAlg cv = new MccvAlg(project, algFactory);
    StatsRes stats = cv.calcStats(Z, true);
//    if (yp == null) {
//      UCShowErrorMssg.showMessageDialog(ui, alg.getLastError());
//      return false;
//    }
    String mccvInfo = project.getMccv().getInfo();
    JTable tableView = new MccvZView(mccvInfo, stats, zTrain
      , project.getTableDisplayOpt()).getTableView();
    if (updateView != null) {
      updateView.setTableView(tableView);
      updateView.assembleWithOpt();
    }
    return true;
  }

  public void setUpdateView(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}

