package kingroup_v2.ucm.tools;

import kingroup_v2.KingroupFrame;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import pattern.ucm.UCController;

import javax.iox.FileX;
import javax.swingx.OpenFileUI;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/01/2006, Time: 10:12:15
 */
public class UCCountCervusErrors extends OpenFileUI
  implements UCController {
  public boolean run() {
    Kingroup bean = KinGroupV2Project.getInstance();
    String name = bean.getLastImportedCervusParentage();
    if (name == null || name.length() == 0)
      name = bean.getLastImportedFileName();
    File file = bean.makeFile(name);
    File[] files = selectFiles(KingroupFrame.getInstance(), file);
    if (files == null || files.length == 0)
      return false;
    bean.setLastImportedCervusParentage(FileX.getFileName(files[0]));
    bean.saveProjectToDefaultLocation();
    new UCReadCervusParentageFile().read(files);
    return true;
  }
}