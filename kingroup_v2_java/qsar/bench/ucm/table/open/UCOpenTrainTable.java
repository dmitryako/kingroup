package qsar.bench.ucm.table.open;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchProject;
import qsar.bench.ucm.table.import_table.UCImportTrainTable;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 15:48:30
 */
public class UCOpenTrainTable extends UCOpenTable
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCOpenTrainTable.class);

  public boolean run() {
    QBench model = QBenchProject.getInstance();
    String name = model.getTrainTableName();
    File file = model.makeFile(name);

    if (!file.exists()) { // TRY TEST file
      name = model.getTestTableName();
      file = model.makeFile(name);
    }

    if (!file.exists()) { // TRY PREDICT file
      name = model.getPredictTableName();
      file = model.makeFile(name);
    }

    file = selectFile(QBenchFrame.getInstance(), file);
    if (file == null) {
//      log.info("file was not selected");
      return false;
    }
    model.setTrainTableName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();

    return read(file);
  }
  public boolean read(File file) {
    return new UCImportTrainTable(file).run();
  }
}

