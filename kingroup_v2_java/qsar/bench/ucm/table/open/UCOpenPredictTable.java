package qsar.bench.ucm.table.open;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchProject;
import qsar.bench.ucm.table.import_table.UCImportPredictTable;

import javax.iox.FileX;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 13/03/2007, 14:31:31
 */
public class UCOpenPredictTable extends UCOpenTable
{
  public boolean run() {
    QBench model = QBenchProject.getInstance();
    String name = model.getPredictTableName();
    File file = model.makeFile(name);

    if (!file.exists()) { // TRY TRAIN
      name = model.getTrainTableName();
      file = model.makeFile(name);
    }

    file = selectFile(QBenchFrame.getInstance(), file);
    if (file == null)
      return false;
    model.setPredictTableName(FileX.getFileName(file));
    model.saveProjectToDefaultLocation();
    return read(file);
  }
  public boolean read(File file) {
    new UCImportPredictTable(file).run();
    return true;
  }
}
