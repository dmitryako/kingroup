package qsar.bench.ucm.project;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchProject;
import qsar.bench.QBenchProjectFileFilter;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import javax.swingx.SaveFileUI;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 25/04/2007, 13:36:32
 */
public class UCSaveProject extends SaveFileUI
  implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSaveProject.class);

  public boolean run() {
    log.trace("run()");
    QBench model = QBenchProject.getInstance();
    String name = model.getLastProjectFileName();
    File file = model.makeFile(name);

    if (!file.exists()) { // TRY train file
      log.debug("try trainning table file name");
      name = model.getTrainTableName();
      file = model.makeFile(name);
    }

    file = selectFile(QBenchFrame.getInstance(), file, new QBenchProjectFileFilter());
    if (file == null)
      return false;
    model.setLastProjectFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();
    model.writeProject(FileX.getFileName(file));
    return true;
  }
}

