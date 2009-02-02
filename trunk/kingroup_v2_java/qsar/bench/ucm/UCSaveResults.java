package qsar.bench.ucm;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import javax.swing.*;
import javax.swingx.SaveFileUI;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/03/2007, 12:23:13
 */
public class UCSaveResults extends SaveFileUI
  implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSaveResults.class);

  public boolean run() {
//    if (!UCSelectFileFormat.select()) {
//      return false;
//    }
    QBench model = QBenchProject.getInstance();
    String name = model.getResultsFileName();   log.debug("getResultsFileName()=", name);
    File file = model.makeFile(name);
    if (!file.exists())  {
      name = model.getTrainTableName();         log.debug("getTrainTableName()=", name);
      file = model.makeFile(name);
    }
    file = selectFile(QBenchFrame.getInstance(), file);
    if (file == null)
      return false;
    if (file.exists()) {
      if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(QBenchFrame.getInstance()
        , "Replace existing \"" + file.getName() + "\" ?"))
        return false;
    }
    model.setResultsFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();
    new QBenchResults(file).run();
    file = null;  System.gc(); //TODO: is this how one suppose to release a file?
    return true;
  }
}

