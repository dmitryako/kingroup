package kingroup_v2.ucm.fsr;

import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.fsr.FsrLowerBoundBean;
import kingroup_v2.fsr.bound.FsrLowerBoundView;
import pattern.ucm.UCController;

import javax.swingx.ApplyDialogUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/11/2005, Time: 08:15:52
 */
public class UCFsrShowLBoundUI   implements UCController
 {
  public boolean run()
  {
    Kingroup project = KinGroupV2Project.getInstance();
    FsrLowerBoundBean fsr = project.getFsrLowerBound();
    FsrLowerBoundView panel = new FsrLowerBoundView(fsr);
    UCFsrCalcLBound approx = new UCFsrCalcLBound(panel);
    ApplyDialogUI gui = new ApplyDialogUI(panel, KingroupFrame.getInstance(), false);
    gui.removeActionListeners();
    gui.runOnApply(approx);
    gui.setTitle("Lower Bound Calculator");
    gui.setApplyBttnText("Calculate");
    gui.center();
    gui.setFocusOnApply();
    gui.setVisible(true);
    return true;
  }
}
