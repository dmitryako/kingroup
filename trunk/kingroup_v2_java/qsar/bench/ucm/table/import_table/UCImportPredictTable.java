package qsar.bench.ucm.table.import_table;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.qsar.QsarTableFormatView;

import javax.iox.TableFormat;
import javax.iox.TableFormatView;
import javax.iox.table.TableView;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 13/03/2007, 14:32:22
 */
public class UCImportPredictTable extends UCImportTable {
  public UCImportPredictTable(File file) {
    super(file);
  }
  public TableFormatView makeFormatView(QBench model) {
    return new QsarTableFormatView(model.getPredictTableFormat());
  }
  public void setTableView(TableView tableView, QBenchMainUI ui) {
    ui.setXTableView(tableView);
  }
  public TableFormat getTableFormat(QBench project) {
    return project.getPredictTableFormat();
  }
}
