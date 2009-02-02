package kingroup_v2.ucm.project;
import kingroup_v2.KingroupFrame;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import pattern.ucm.UCController;

import javax.iox.FileX;
import javax.swingx.SaveFileUI;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/10/2005, Time: 11:40:44
 */
public class UCSaveProject extends SaveFileUI
  implements UCController {
  public boolean run() {
    Kingroup model = KinGroupV2Project.getInstance();
    String name = model.getLastProjectFileName();
    File file = model.makeFile(name);
    file = selectFile(KingroupFrame.getInstance(), file);
    if (file == null)
      return false;
    model.setLastProjectFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();
    model.writeProject(FileX.getFileName(file));
    return true;
  }
}