package qsar.bench.ucm.alg;
import pattern.ucm.UCUpdateTable;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.MLR.MlrRes;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.QsarAlg;
import qsar.bench.qsar.table.QsarYTableView;
import qsar.bench.qsar.table.QsarYZView;
import qsar.bench.ucm.UCShowErrorMssg;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.stats.Stats;
import javax.swing.*;
import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.arrays.mtrx.Mtrx;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 13:47:18
 */
public class UCRunQsarAlg implements UCUpdateTable {
  private static ProjectLogger log = ProjectLogger.getLogger(UCRunQsarAlg.class.getName());

  private TableViewWithOpt updateView;
  private final QBenchViewI optView;
  private final QsarAlgFactory algFactory;

  public UCRunQsarAlg(QBenchViewI optView, QsarAlgFactory alg) {
    this.optView = optView;
    algFactory = alg;
  }
  public boolean run() {
    QBench project = QBenchProject.getInstance();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();
    if (project.getQsarType() == QBench.QSAR_CALIB)
      return train();
    if (project.getQsarType() == QBench.QSAR_VALID)
      return test();
    if (project.getQsarType() == QBench.QSAR_PREDICT)
      return predict();
    return false;
  }

  public boolean train() {
    QBench project = QBenchProject.getInstance();
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table tTrain = ui.getZTable();
    if (tTrain == null   ||  tTrain.getNumRows() == 0) {
      ui.showLoadTrainTableFirst();
      return false;
    }

    double[][] zTrain = tTrain.getByRows();
    // NOTE: ONLY available for TRAINING 
    if (project.getQsarAlgId() == project.ALG_kNN  &&  project.getMeanZeroVarOne()) {
      double[] Y = Mtrx.getCol(0, zTrain);
      zTrain = Stats.makeColsMeanZeroVarOne(zTrain);
      Mtrx.setCol(0, Y, zTrain); // restore y
    }
    QsarAlg alg = algFactory.makeAlg(project, zTrain);
//    optView.setTitle(); // todo

    Mtrx mTrain = new Mtrx(zTrain);
    double[][] xTrain = mTrain.getCols(1, mTrain.getNumCols());
    log.trace("xTrain = \n", new Mtrx(xTrain));

    double[] yp = alg.calcYFromXZ(xTrain);
    if (yp == null) {
      UCShowErrorMssg.showMessageDialog(ui, alg.getLastError());
      return false;
    }
    MlrRes mlrRes = alg.getMlrRes();
    mlrRes.calcMLR(tTrain.getColCopy(0), yp, tTrain.getNumCols() - 1);
    JTable tableView = new QsarYZView(alg.getName(), yp, mlrRes, tTrain
      , project.getDigitsModel()).getTableView();
    if (updateView != null) {
      updateView.setTableView(tableView);
      updateView.assembleWithOpt();
    }
    return true;
  }

  public boolean test() {
    QBench project = QBenchProject.getInstance();
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table tTrain = ui.getZTable();
    if (tTrain == null   ||  tTrain.getNumRows() == 0) {
      ui.showLoadTrainTableFirst();
      return false;
    }
    Table tTest = ui.getTestZ();
    if (tTest == null   ||  tTest.getNumRows() == 0) {
      ui.showLoadTestTableFirst();
      return false;
    }
    double[][] zTrain = tTrain.getByRows();
    QsarAlg alg = algFactory.makeAlg(project, zTrain);

    if (tTrain.getNumCols() != tTest.getNumCols()) {
      String error = "Training and Test tables (data sets) must have the same number of columns";
      log.severe(error);
      UCShowErrorMssg.showMessageDialog(ui, error);
      return false;
    }
    double[][] xTest = tTest.getCols(1, tTest.getNumCols());
    double[] yp = alg.calcYFromXZ(xTest);
    if (yp == null) {
      UCShowErrorMssg.showMessageDialog(ui, alg.getLastError());
      return false;
    }
    MlrRes mlrRes = alg.getMlrRes();
    mlrRes.calcMLR(tTest.getColCopy(0), yp, tTest.getNumCols() - 1);
    JTable tableView = new QsarYZView(alg.getName(), yp, mlrRes, tTest
      , project.getDigitsModel()).getTableView();
    if (updateView != null) {
      updateView.setTableView(tableView);
      updateView.assembleWithOpt();
    }
    return true;
  }

  public boolean predict() {
    QBench project = QBenchProject.getInstance();
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table tTrain = ui.getZTable();
    if (tTrain == null   ||  tTrain.getNumRows() == 0) {
      ui.showLoadTrainTableFirst();
      return false;
    }
    Table tPredict = ui.getXTable();
    if (tPredict == null   ||  tPredict.getNumRows() == 0) {
      ui.showLoadPredictTableFirst();
      return false;
    }

    double[][] zTrain = tTrain.getByRows();
    QsarAlg alg = algFactory.makeAlg(project, zTrain);

    if (tTrain.getNumCols() - 1 != tPredict.getNumCols()) {
      String error = "PREDICTION dataset must have one less column comparing to TRAINING dataset " +
        "\n(Y column is missing).";
      log.severe(error);
      UCShowErrorMssg.showMessageDialog(ui, error);
      return false;
    }
    double[][] xPredict = tPredict.getByRows();
    double[] yp = alg.calcYFromXZ(xPredict);
    if (yp == null) {
      UCShowErrorMssg.showMessageDialog(ui, alg.getLastError());
      return false;
    }

    JTable tableView = new QsarYTableView(alg.getName(), yp, tPredict
      , project.getDigitsModel()).getTableView();
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
