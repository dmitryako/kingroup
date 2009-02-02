package qsar.bench.ucm.table.import_table;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.qsar.QsarTableFormatView;

import javax.iox.TableFormatView;
import javax.iox.table.TableView;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 15:47:52
 */
public class UCImportTrainTable  extends UCImportTable {
  public UCImportTrainTable(File file) {
    super(file);
  }
  public TableFormatView makeFormatView(QBench model) {
    return new QsarTableFormatView(model.getTrainTableFormat());
  }
  public void setTableView(TableView tableView, QBenchMainUI ui) {
    ui.setZTableView(tableView);
  }
}

