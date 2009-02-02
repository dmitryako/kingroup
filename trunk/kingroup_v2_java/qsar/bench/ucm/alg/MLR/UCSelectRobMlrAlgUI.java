package qsar.bench.ucm.alg.MLR;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.MLR.MlrAlgOptView;
import qsar.bench.qsar.MLR.MlrAlgUI;
import qsar.bench.qsar.MLR.robust.MlrAlgFactoryRobust;
import qsar.bench.qsar.MLR.robust.RobMlrSelectAlgView;
import qsar.bench.qsar.cv.mccv.MccvMlrOptView;
import qsar.bench.qsar.cv.mcvs.McvsMlrOptView;
import qsar.bench.qsar.cv.mcvs.ga.McvsGaFactory;
import qsar.bench.qsar.view.QsarAlgOptView;
import qsar.bench.qsar.view.QsarAlgView;
import qsar.bench.ucm.alg.UCRunQsarAlg;
import qsar.bench.ucm.alg.cv.UCRunMCCV;
import qsar.bench.ucm.alg.cv.UCRunMcvs;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/11/2007, Time: 16:47:31
 */
public class UCSelectRobMlrAlgUI extends UCSelectMlrAlgUI
{
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSelectRobMlrAlgUI.class);

  public boolean run() {
    log.trace("run()");
    QBench project = QBenchProject.getInstance();
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table table = ui.getZTable();
    if (table == null   ||  table.getNumRows() == 0) {
      log.trace("table == null   ||  table.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    if (!UCSelectMlrAlg.select(new RobMlrSelectAlgView(project, null), project)) {
      return false;
    }
//    project.getMcvs().setMaeNorm(true);
    MlrAlgUI mlrUI = ui.getMlrView(); // NOTE: it makes it as well

    if (project.getCrossValidId() == project.CV_NONE) {   // no cross-validation
      if (project.getQsarAlgId() == project.ALG_MLR) {      // MLR
        QsarAlgOptView optView = new MlrAlgOptView(project);
        UCRunQsarAlg runOnApply = new UCRunQsarAlg(optView, new MlrAlgFactoryRobust());
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setMlrAlgView(algView);
      }
      else if (project.getQsarAlgId() == project.ALG_kNN) {  // kNN-MLR
        ui.showNotImplemented();
        //TODO
//        MlrKnnOptView optView = new MlrKnnOptView(project);
//        UCRunQsarAlg runOnApply = new UCRunQsarAlg(optView, new MlrAlgFactoryKnn());
//        QsarAlgView algView = makeAlgView(optView, runOnApply);
//        mlrUI.setKnnAlgView(algView);
      }
    }
    else if (project.getCrossValidId() == project.CV_MCCV) {
      project.setQsarType(QBench.QSAR_CALIB);
      if (project.getQsarAlgId() == project.ALG_MLR) {
        QsarAlgOptView optView = new MccvMlrOptView(project);
        UCRunMCCV runOnApply = new UCRunMCCV(optView, new MlrAlgFactoryRobust());
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setMccvAlgView(algView);
      }
      else if (project.getQsarAlgId() == project.ALG_kNN) {
        ui.showNotImplemented();
//        QsarAlgOptView optView = new MccvKnnOptView(project);
//        UCRunMCCV runOnApply = new UCRunMCCV(optView, new MlrAlgFactoryKnn());
//        QsarAlgView algView = makeAlgView(optView, runOnApply);
//        mlrUI.setMccvKnnAlgView(algView);
      }
    }
    else if (project.getCrossValidId() == project.CV_MCVS_SA) {
      ui.showNotImplemented();
      //TODO

//      project.setQsarType(QBench.QSAR_CALIB);
//      if (project.getQsarAlgId() == project.ALG_MLR) {
//        QsarAlgOptView optView = new McvsMlrOptView(project);
//        UCRunMcvs runOnApply = new UCRunMcvs(optView
//          , new MlrAlgFactoryMLR()
//          , new McvsSaFactory());  // SA
//        QsarAlgView algView = makeAlgView(optView, runOnApply);
//        mlrUI.setMcvsSaView(algView); // SA view
//      }
//      else if (project.getQsarAlgId() == project.ALG_kNN) {
//        JOptionPane.showMessageDialog(ui
//          , "MCVS has been implemented only for MLR.");
//      }
    }
    else if (project.getCrossValidId() == project.CV_MCVS_GA) {
      project.setQsarType(QBench.QSAR_CALIB);
      if (project.getQsarAlgId() == project.ALG_MLR) {
        QsarAlgOptView optView = new McvsMlrOptView(project);
        UCRunMcvs runOnApply = new UCRunMcvs(optView
//          , new MlrAlgFactoryMlrHuber()
          , new MlrAlgFactoryRobust()
          , new McvsGaFactory());  // GA
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setMcvsGaView(algView); // GA view
      }
      else if (project.getQsarAlgId() == project.ALG_kNN) {
        ui.showNotImplemented();
//        JOptionPane.showMessageDialog(ui
//          , "MCVS has been implemented only for MLR.");
      }
    }
    return true;
  }
}
