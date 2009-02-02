package qsar.bench.ucm.table;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableFactory;
import javax.iox.table.TableView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 7/06/2007, 16:08:22
 */
public class UCSortZRowsByY   implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSortZRowsByY.class);
  private static final int IDX_Y = 0;
  private boolean ascend = true;

  public boolean run() {
    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();

    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      log.trace("train == null   ||  train.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    ascend = !ascend; // toggle
    Table res = TableFactory.sortRows(IDX_Y, zTrain, ascend);
    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(res, project.getTableDisplayOpt());
    ui.setZTableView(tableView);
    ui.setStatusWithTime("sorted.");
    return true;
  }
}

