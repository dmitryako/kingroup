package qsar.bench.ucm.table.open;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchProject;
import qsar.bench.ucm.table.import_table.UCImportTable;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import javax.swingx.OpenFileUI;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2007, Time: 11:39:42
 */
public class UCOpenTable extends OpenFileUI
  implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCOpenTable.class.getName());
  protected UCOpenTable() {

  }
  public boolean run() {
    QBench model = QBenchProject.getInstance();
    String name = model.getTrainTableName();
    File file = model.makeFile(name);

    if (!file.exists()) { // TRY TEST file
      name = model.getTestTableName();
      file = model.makeFile(name);
    }

    file = selectFile(QBenchFrame.getInstance(), file);
    if (file == null)
      return false;
    model.setTrainTableName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();

    return read(file);
  }
  public boolean read(File file) {
    return new UCImportTable(file).run();
  }
}

