package kingroup_v2.ucm.fsr.accuracy;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.fsr.FsrAlgFactoryMS2;
import kingroup_v2.fsr.accuracy.FsrAccuracyOptionsMS2View;
import kingroup_v2.fsr.accuracy.FsrAccuracyOptionsView;
import kingroup_v2.fsr.accuracy.FsrAccuracyUI;
import kingroup_v2.fsr.accuracy.FsrAccuracyView;
import kingroup_v2.ucm.fsr.UCFsrSelectAlg;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.UCClearTable;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/11/2005, Time: 14:08:43
 */
public class UCFsrAccuracySelectAlgUI implements UCController {
  public boolean run() {
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
//    UsrPopSLOW pop = ui.getUserPop();
//    if (pop == null) {
//      ui.showMessageLoadPopulationFirst();
//      return;
//    }

    if (!UCFsrSelectAlg.select("WARNING: WORK IN PROGRESS!!!")) {
      return false;
    }
    FsrAccuracyUI accView = ui.getFsrAccuracyView();
    if (accView == null) {
      accView = new FsrAccuracyUI();
    }
    ui.setFsrAccuracyView(accView);

    Kingroup project = KinGroupV2Project.getInstance();
    if (project.getFsrAlg() == project.FSR_SIMPS) {
//      FsrAlgOptionsSIMPSView optView = new FsrAlgOptionsSIMPSView(project);
//      UCFsrRunAlg runOnApply = new UCFsrRunSimpsAlg(optView);
//      FsrAlgView algView = makeAlgView(optView, runOnApply);
//      ui.getFsrView().setSimpsAlgView(algView);
    }
    else if (project.getFsrAlg() == project.FSR_MS) {
//      FsrAlgOptionsMSView optView = new FsrAlgOptionsMSView(project);
//      UCFsrRunAlg runOnApply = new UCFsrRunMSAlg(optView);
//      FsrAlgView algView = makeAlgView(optView, runOnApply);
//      ui.getFsrView().setMSAlgView(algView);
    }
    else if (project.getFsrAlg() == project.FSR_MS2) {
      FsrAccuracyOptionsMS2View optView = new FsrAccuracyOptionsMS2View(project);
      UCFsrRunAccuracy runOnApply = new UCFsrRunAccuracy(optView, new FsrAlgFactoryMS2());
      FsrAccuracyView algView = makeAlgView(optView, runOnApply);
      accView.setMS2AlgView(algView);
    }
    else if (project.getFsrAlg() == project.FSR_DR) {
//      FsrAlgOptionsDRView optView = new FsrAlgOptionsDRView(project);
//      UCFsrRunAlg runOnApply = new UCFsrRunDRAlg(optView);
//      FsrAlgView algView = makeAlgView(optView, runOnApply);
//      ui.getFsrView().setDRAlgView(algView);
    }
    else if (project.getFsrAlg() == project.FSR_SDR) {
//      FsrAlgOptionsSDRView optView = new FsrAlgOptionsSDRView(project);
//      UCFsrRunAlg runOnApply = new UCFsrRunSDRAlg(optView);
//      FsrAlgView algView = makeAlgView(optView, runOnApply);
//      ui.getFsrView().setSDRAlgView(algView);
    }
    return true;
  }
  private FsrAccuracyView makeAlgView(FsrAccuracyOptionsView optView
  , UCFsrRunAccuracy runOnApply) {
    FsrAccuracyView algView = new FsrAccuracyView(optView, runOnApply);
    runOnApply.setUpdateView(algView);

    algView.setTableView(new JTable());
    algView.assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
    algView.setFocusOnApply();

    optView.onOptionChange(new UCClearTable(algView));
    return algView;
  }
}