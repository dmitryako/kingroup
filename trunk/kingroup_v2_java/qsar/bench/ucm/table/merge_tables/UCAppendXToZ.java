package qsar.bench.ucm.table.merge_tables;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 3/06/2007, 14:22:04
 */
public class UCAppendXToZ extends UCCheckAppendCols  {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCAppendXToZ.class);

  public boolean run() {
    log.trace("run()");
    if (!super.run())
      return false;
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table zTrain = ui.getZTable();
    Table xPred = ui.getXTable();

    Table res = zTrain.appendCols(xPred);
    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(res, project.getTableDisplayOpt());
    ui.setZTableView(tableView);
    return true;
  }
}
