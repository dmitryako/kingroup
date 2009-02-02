package qsar.bench.ucm.table.import_table;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.qsar.QsarTableFormatView;

import javax.iox.TableFormat;
import javax.iox.TableFormatView;
import javax.iox.table.TableView;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 13/03/2007, 10:48:16
 */
public class UCImportTestTable  extends UCImportTable {
  public UCImportTestTable(File file) {
    super(file);
  }
  public TableFormatView makeFormatView(QBench model) {
    return new QsarTableFormatView(model.getTestTableFormat());
  }
  public void setTableView(TableView tableView, QBenchMainUI ui) {
    ui.setTestTableView(tableView);
  }
  public TableFormat getTableFormat(QBench project) {
    return project.getTestTableFormat();
  }
}

