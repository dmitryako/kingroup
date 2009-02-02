package qsar.bench.ucm.alg.cv;
import pattern.ucm.UCUpdateTable;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.MLR.MlrRes;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.cv.LoocvMethod;
import qsar.bench.qsar.table.QsarYZView;
import qsar.bench.ucm.UCShowErrorMssg;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.swing.*;
import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.arrays.mtrx.Mtrx;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 10:33:33
 */
public class UCRunLOOCV implements UCUpdateTable {
  private static ProjectLogger log = ProjectLogger.getLogger(UCRunLOOCV.class);

  private TableViewWithOpt updateView;
  private final QBenchViewI optView;
  private final QsarAlgFactory algFactory;

  public UCRunLOOCV(QBenchViewI optView, QsarAlgFactory alg) {
    this.optView = optView;
    algFactory = alg;
  }
  public boolean run() {
    log.trace("run()");
    QBench project = QBenchProject.getInstance();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table tTrain = ui.getZTable();
    if (tTrain == null   ||  tTrain.getNumRows() == 0) {
      ui.showLoadTrainTableFirst();
      return false;
    }

    double[][] Z = tTrain.getMtrx();
    double[] Y = tTrain.getColCopy(0);
    if (project.getQsarAlgId() == project.ALG_kNN  &&  project.getMeanZeroVarOne()) {
      Z = Stats.makeColsMeanZeroVarOne(Z);
      Mtrx.setCol(0, Y, Z); // restore y
    }

    LoocvMethod cv = new LoocvMethod(project, algFactory);
    double[] Yp = cv.calcY(Z, true);
    StatsRes stats = new MlrRes(Y, Yp, tTrain.getNumCols() - 1);

    if (Yp == null) {
      UCShowErrorMssg.showMessageDialog(ui, cv.getLastError());
      return false;
    }
//    String info = project.getMccv().getInfo();
    JTable tableView = new QsarYZView(cv.getName(), Yp, stats, tTrain
      , project.getDigitsModel()).getTableView();
//    JTable tableView = new MccvZView(info, stats, zTrain
//      , project.getDigitsModel()).getTableView();
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

