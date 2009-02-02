package kingroup_v2.ucm.import_sample;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.ucm.UCSelectFileFormat;
import pattern.ucm.UCController;

import javax.iox.FileX;
import javax.swingx.OpenFileUI;
import java.io.File;
import tsvlib.project.ProjectLogger;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/09/2005, Time: 15:42:08
 */
public class UCImportPop extends OpenFileUI
  implements UCController
 {
  private static ProjectLogger log = ProjectLogger.getLogger(UCImportPop.class.getName());
  public boolean run() {
    if (!UCSelectFileFormat.select()) {
      return false;
    }
    Kingroup bean = KinGroupV2Project.getInstance();
    String name = bean.getLastImportedFileName();
    File file = bean.makeFile(name);
    file = selectFile(KingroupFrame.getInstance(), file);
    if (file == null)
      return false;
    bean.setLastImportedFileName(FileX.getFileName(file));
    bean.saveProjectToDefaultLocation();

    new UCImportFile().read(file);
    return true;
  }
}
