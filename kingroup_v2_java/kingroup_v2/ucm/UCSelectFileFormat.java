package kingroup_v2.ucm;
import kingroup_v2.KingroupFrame;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.ucm.import_sample.SelectImportFormatView;

import javax.swingx.ApplyDialogUI;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 15:29:07
 */
public class UCSelectFileFormat //extends OpenFileUI
{
  public static boolean select() {
    Kingroup bean = KinGroupV2Project.getInstance();
    SelectImportFormatView panel = new SelectImportFormatView(bean);
    ApplyDialogUI dlg = new ApplyDialogUI(panel, KingroupFrame.getInstance(), true);
    dlg.setTitle("File");
    dlg.center();
    dlg.setFocusOnApply();
    dlg.setApplyBttnText("Ok");
    dlg.setVisible(true);
    if (!dlg.apply()) {
      return false;
    }
    panel.loadTo(bean);
    bean.saveProjectToDefaultLocation();
    return true;
  }
}
