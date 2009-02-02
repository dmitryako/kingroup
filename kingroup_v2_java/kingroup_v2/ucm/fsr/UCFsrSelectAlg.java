package kingroup_v2.ucm.fsr;
import kingroup_v2.KingroupFrame;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.fsr.FsrSelectAlgView;

import javax.swingx.ApplyDialogUI;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/09/2005, Time: 08:34:19
 */
public class UCFsrSelectAlg {
  public static boolean select(String mssg) {
    Kingroup project = KinGroupV2Project.getInstance();
    FsrSelectAlgView panel = new FsrSelectAlgView(project, mssg);
    ApplyDialogUI dlg = new ApplyDialogUI(panel, KingroupFrame.getInstance(), true);
    dlg.setTitle("Select");
    dlg.center();
    dlg.setFocusOnApply();
    dlg.setVisible(true);
    if (!dlg.apply()) {
      return false;
    }
    panel.loadTo(project);
    project.saveProjectToDefaultLocation();
    return true;
  }
}

