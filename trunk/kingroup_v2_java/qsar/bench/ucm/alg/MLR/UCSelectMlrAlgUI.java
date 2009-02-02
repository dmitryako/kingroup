package qsar.bench.ucm.alg.MLR;
import pattern.ucm.UCController;
import pattern.ucm.UCUpdateTable;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.MLR.*;
import qsar.bench.qsar.cv.LooKnnOptView;
import qsar.bench.qsar.cv.LooOptView;
import qsar.bench.qsar.cv.mccv.MccvMlrOptView;
import qsar.bench.qsar.cv.mcvs.McvsMlrOptView;
import qsar.bench.qsar.cv.mcvs.ga.McvsGaFactory;
import qsar.bench.qsar.cv.mcvs.sa.McvsSaFactory;
import qsar.bench.qsar.view.QsarAlgOptView;
import qsar.bench.qsar.view.QsarAlgView;
import qsar.bench.ucm.alg.UCRunQsarAlg;
import qsar.bench.ucm.alg.cv.UCRunLOOCV;
import qsar.bench.ucm.alg.cv.UCRunMCCV;
import qsar.bench.ucm.alg.cv.UCRunMcvs;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 13:10:36
 */
public class UCSelectMlrAlgUI  implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSelectMlrAlgUI.class);

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

    if (!UCSelectMlrAlg.select(new MlrSelectAlgView(project, null), project)) {
      return false;
    }
    project.getMcvs().setMaeNorm(false);
    MlrAlgUI mlrUI = ui.getMlrView(); // NOTE: it makes it as well

    if (project.getCrossValidId() == project.CV_NONE) {   // no cross-validation
      if (project.getQsarAlgId() == project.ALG_MLR) {      // MLR
        QsarAlgOptView optView = new MlrAlgOptView(project);
        UCRunQsarAlg runOnApply = new UCRunQsarAlg(optView, new MlrAlgFactoryMLR());
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setMlrAlgView(algView);
      }
      else if (project.getQsarAlgId() == project.ALG_kNN) {  // kNN-MLR
        MlrKnnOptView optView = new MlrKnnOptView(project);
        UCRunQsarAlg runOnApply = new UCRunQsarAlg(optView, new MlrAlgFactoryKnn());
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setKnnAlgView(algView);
      }
    }
    else if (project.getCrossValidId() == project.CV_LOO) { //LOOCV
      project.setQsarType(QBench.QSAR_CALIB);
      if (project.getQsarAlgId() == project.ALG_MLR) {
        QsarAlgOptView optView = new LooOptView(project);
        UCRunLOOCV runOnApply = new UCRunLOOCV(optView, new MlrAlgFactoryMLR());
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setLooAlgView(algView);
      }
      else if (project.getQsarAlgId() == project.ALG_kNN) {
        MlrKnnOptView optView = new LooKnnOptView(project);
        UCRunLOOCV runOnApply = new UCRunLOOCV(optView, new MlrAlgFactoryKnn());
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setLooKnnAlgView(algView);
      }
    }
    else if (project.getCrossValidId() == project.CV_MCCV) {
      project.setQsarType(QBench.QSAR_CALIB);
      if (project.getQsarAlgId() == project.ALG_MLR) {
        QsarAlgOptView optView = new MccvMlrOptView(project);
        UCRunMCCV runOnApply = new UCRunMCCV(optView, new MlrAlgFactoryMLR());
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setMccvAlgView(algView);
      }
      else if (project.getQsarAlgId() == project.ALG_kNN) {
        QsarAlgOptView optView = new MccvKnnOptView(project);
        UCRunMCCV runOnApply = new UCRunMCCV(optView, new MlrAlgFactoryKnn());
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setMccvKnnAlgView(algView);
      }
    }
    else if (project.getCrossValidId() == project.CV_MCVS_SA) {
      project.setQsarType(QBench.QSAR_CALIB);
      if (project.getQsarAlgId() == project.ALG_MLR) {
        QsarAlgOptView optView = new McvsMlrOptView(project);
        UCRunMcvs runOnApply = new UCRunMcvs(optView
          , new MlrAlgFactoryMLR()
          , new McvsSaFactory());  // SA
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setMcvsSaView(algView); // SA view
      }
      else if (project.getQsarAlgId() == project.ALG_kNN) {
        JOptionPane.showMessageDialog(ui
          , "MCVS has been implemented only for MLR.");
//        QsarAlgOptView optView = new MccvKnnOptView(project);
//        UCRunMCCV runOnApply = new UCRunMCCV(optView, new MlrAlgFactoryKnn());
//        QsarAlgView algView = makeAlgView(optView, runOnApply);
//        mlrUI.setMccvKnnAlgView(algView);
      }
    }
    else if (project.getCrossValidId() == project.CV_MCVS_GA) {
      project.setQsarType(QBench.QSAR_CALIB);
      if (project.getQsarAlgId() == project.ALG_MLR) {
        QsarAlgOptView optView = new McvsMlrOptView(project);
        UCRunMcvs runOnApply = new UCRunMcvs(optView
          , new MlrAlgFactoryMLR()
          , new McvsGaFactory());  // GA
        QsarAlgView algView = makeAlgView(optView, runOnApply);
        mlrUI.setMcvsGaView(algView); // GA view
      }
      else if (project.getQsarAlgId() == project.ALG_kNN) {
        JOptionPane.showMessageDialog(ui
          , "MCVS has been implemented only for MLR.");
//        QsarAlgOptView optView = new MccvKnnOptView(project);
//        UCRunMCCV runOnApply = new UCRunMCCV(optView, new MlrAlgFactoryKnn());
//        QsarAlgView algView = makeAlgView(optView, runOnApply);
//        mlrUI.setMccvKnnAlgView(algView);
      }
    }
    return true;
  }
  protected QsarAlgView makeAlgView(QsarAlgOptView optView
    , UCUpdateTable runOnApply) {
    QsarAlgView algView = new QsarAlgView(optView, runOnApply);
    runOnApply.setUpdateView(algView);

    algView.setTableView(new JTable());
    algView.assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
    algView.setFocusOnApply();

//    optView.onOptionChange(new UCClearTable(algView));
    return algView;
  }
}
