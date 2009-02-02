package multi_t.ucm.project;
import multi_t.MultiT;
import multi_t.MultiTFrame;
import multi_t.MultiTMainUI;
import multi_t.MultiTProject;
import multi_t.ucm.settings.UCShowSettingsUI;
import pattern.ucm.UCController;

import javax.iox.FileX;
import javax.swingx.OpenFileUI;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/11/2006, Time: 17:15:40
 */
public class UCOpenProject extends OpenFileUI
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
    model.loadFromDisk(FileX.getFileName(file));
    model.setLastProjectFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();

    MultiTMainUI.getInstance().resetAll();
    new UCShowSettingsUI().run();
    return true;
  }
}

