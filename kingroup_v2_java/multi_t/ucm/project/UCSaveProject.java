package multi_t.ucm.project;
import multi_t.MultiT;
import multi_t.MultiTFrame;
import multi_t.MultiTProject;
import pattern.ucm.UCController;

import javax.iox.FileX;
import javax.swingx.SaveFileUI;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/11/2006, Time: 17:17:48
 */
public class UCSaveProject  extends SaveFileUI
  implements UCController {
  public boolean run() {
    MultiT model = MultiTProject.getInstance();
    String name = model.getLastProjectFileName();
    File file = model.makeFile(name);
    file = selectFile(MultiTFrame.getInstance(), file);
    if (file == null)
      return false;
    model.setLastProjectFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();
    model.writeProject(FileX.getFileName(file));
    return true;
  }
}
