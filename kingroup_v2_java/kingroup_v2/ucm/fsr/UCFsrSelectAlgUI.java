package kingroup_v2.ucm.fsr;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.fsr.*;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.UCClearTable;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 07:40:40
 */
public class UCFsrSelectAlgUI implements UCController {
  public boolean run() {
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW pop = ui.getUsrPop();
    if (pop == null   ||  pop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }

    if (!UCFsrSelectAlg.select(null)) {
      return false;
    }
    FsrAlgUI fsrView = ui.getFsrView();
    if (fsrView == null) {
      fsrView = new FsrAlgUI();
    }
    ui.setFsrView(fsrView);

    Kingroup project = KinGroupV2Project.getInstance();
    if (project.getFsrAlg() == project.FSR_SIMPS) {
      FsrAlgOptionsSIMPSView optView = new FsrAlgOptionsSIMPSView(project);
      UCFsrRunAlg runOnApply = new UCFsrRunAlg(optView, new FsrAlgFactorySIMPS());
      FsrAlgView algView = makeAlgView(optView, runOnApply);
      fsrView.setSIMPSAlgView(algView);
    }
    if (project.getFsrAlg() == project.FSR_MCS) {
      FsrAlgOptionsMCSView optView = new FsrAlgOptionsMCSView(project);
      UCFsrRunAlg runOnApply = new UCFsrRunAlg(optView, new FsrAlgFactoryMCS());
      FsrAlgView algView = makeAlgView(optView, runOnApply);
      fsrView.setMCSAlgView(algView);
    }
    else if (project.getFsrAlg() == project.FSR_MS) {
      FsrAlgOptionsMSView optView = new FsrAlgOptionsMSView(project);
      UCFsrRunAlg runOnApply = new UCFsrRunAlg(optView, new FsrAlgFactoryMS());
      FsrAlgView algView = makeAlgView(optView, runOnApply);
      fsrView.setMSAlgView(algView);
    }
    else if (project.getFsrAlg() == project.FSR_MS2) {
      FsrAlgOptionsMS2View optView = new FsrAlgOptionsMS2View(project);
      UCFsrRunAlg runOnApply = new UCFsrRunAlg(optView, new FsrAlgFactoryMS2());
      FsrAlgView algView = makeAlgView(optView, runOnApply);
      fsrView.setMS2AlgView(algView);
    }
    else if (project.getFsrAlg() == project.FSR_DR) {
      FsrAlgOptionsDRView optView = new FsrAlgOptionsDRView(project);
      UCFsrRunAlg runOnApply = new UCFsrRunAlg(optView, new FsrAlgFactoryDR());
      FsrAlgView algView = makeAlgView(optView, runOnApply);
      fsrView.setDRAlgView(algView);
    }
    else if (project.getFsrAlg() == project.FSR_SDR) {
      FsrAlgOptionsSDRView optView = new FsrAlgOptionsSDRView(project);
      UCFsrRunAlg runOnApply = new UCFsrRunAlg(optView, new FsrAlgFactorySDR());
      FsrAlgView algView = makeAlgView(optView, runOnApply);
      fsrView.setSDRAlgView(algView);
    }
    return true;
  }
  private FsrAlgView makeAlgView(FsrAlgOptView optView
  , UCFsrRunAlg runOnApply) {
    FsrAlgView algView = new FsrAlgView(optView, runOnApply);
    runOnApply.setUpdateView(algView);

    algView.setTableView(new JTable());
    algView.assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
    algView.setFocusOnApply();

    optView.onOptionChange(new UCClearTable(algView));
    return algView;
  }
}