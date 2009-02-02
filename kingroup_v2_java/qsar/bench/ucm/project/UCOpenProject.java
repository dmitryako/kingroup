package qsar.bench.ucm.project;
import pattern.ucm.UCController;
import qsar.bench.*;

import javax.iox.FileX;
import javax.swingx.OpenFileUI;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 25/04/2007, 13:19:27
 */
public class UCOpenProject extends OpenFileUI
  implements UCController {
  public boolean run() {
    QBench model = QBenchProject.getInstance();
    String name = model.getLastProjectFileName();
    File file = model.makeFile(name);

    if (!file.exists()) { // TRY train file
      name = model.getTrainTableName();
      file = model.makeFile(name);
    }

    file = selectFile(QBenchFrame.getInstance(), file, new QBenchProjectFileFilter());
    if (file == null)
      return false;
    model.setLastProjectFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();
    model.loadFromDisk(FileX.getFileName(file));
    model.setLastProjectFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();

    QBenchMainUI.getInstance().resetAll();
//    new UCShowSettingsUI().run();
    return true;
  }
}

