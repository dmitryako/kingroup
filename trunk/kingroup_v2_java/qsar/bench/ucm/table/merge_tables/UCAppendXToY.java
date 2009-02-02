package qsar.bench.ucm.table.merge_tables;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 3/06/2007, 12:55:08
 */
public class UCAppendXToY  extends UCCheckAppendCols {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCAppendXToY.class);

  public boolean run() {
    log.trace("run()");
    if (!super.run())
      return false;
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table zTrain = ui.getZTable();
    Table xPred = ui.getXTable();

    Table Y = zTrain.getTableCols(0, 1);
    Table res = Y.appendCols(xPred); // ONLY Y
    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(res, project.getTableDisplayOpt());
    ui.setZTableView(tableView);
    return true;
  }
}
