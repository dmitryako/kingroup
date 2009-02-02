package qsar.bench.ucm.smiles;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchProject;
import pattern.ucm.UCController;

import javax.iox.FileX;
import javax.swingx.OpenFileUI;
import java.io.File;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/03/2007, 15:05:33
 */
public class UCOpenSmilesTable extends OpenFileUI
  implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCOpenSmilesTable.class.getName());
//  protected UCOpenSmilesTable() {
//
//  }
  public boolean run() {
    QBench model = QBenchProject.getInstance();
    String name = model.getSmilesFileName();
    File file = model.makeFile(name);

    if (!file.exists()) { // TRY TRAINING file
      name = model.getTrainTableName();
      file = model.makeFile(name);
    }

    file = selectFile(QBenchFrame.getInstance(), file);
    if (file == null)
      return false;
    model.setSmilesFileName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();

    return read(file);
  }
  public boolean read(File file) {
    return new UCImportSmilesTable(file).run();
//    return new UCImportTrainTable(file).run(); //
  }
}

