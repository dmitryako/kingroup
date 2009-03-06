package kingroup_v2.ucm.save_results;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.ucm.UCSelectFileFormat;
import pattern.ucm.UCController;

import javax.iox.FileX;
import javax.swing.*;
import javax.swingx.SaveFileUI;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 15:21:35
 */
public class UCSaveResultsFileUI extends SaveFileUI
  implements UCController {
  private UCController uc;
  public UCSaveResultsFileUI(UCController uc)
  {
    this.uc = uc;
  }
  public UCSaveResultsFileUI()
  {
    this.uc = null;
  }

  public boolean run() {
    if (!UCSelectFileFormat.select()) {
      return false;
    }
    Kingroup model = KinGroupV2Project.getInstance();
    String name = model.getLastSavedFileName();
    if (name == null || name.length() == 0)
      name = model.getLastImportedFileName();
    if (name == null || name.length() == 0)
      name = model.getLastProjectFileName();
    File file = model.makeFile(name);
    file = selectFile(KingroupFrame.getInstance(), file);
    if (file == null)
      return false;
    if (file.exists()) {
      if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(KingroupFrame.getInstance()
        , "Replace existing \"" + file.getName() + "\" ?"))
        return false;
    }
    model.setLastSavedFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();
    file = null;  System.gc();
    if (uc != null) {
      uc.run();
    }
    return true;
  }
}
