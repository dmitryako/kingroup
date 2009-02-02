package qsar.bench.ucm.table;
import pattern.mvc.MVCTableView;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.table.CorrTableView;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableDisplayOpt;
import javax.iox.table.TableFactory;
import javax.iox.table.TableView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/06/2007, 15:36:34
 */
public class UCSortZColsByAbsCorrToY   implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSortZColsByAbsCorrToY.class);
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
    Table res = TableFactory.sortColsByAbsCorr(zTrain, ascend);
    QBench project = QBenchProject.getInstance();
    TableDisplayOpt opt = project.getTableDisplayOpt();
    TableView tableView = new TableView(res, opt);
    ui.setZTableView(tableView);

    MVCTableView corrView = new CorrTableView(IDX_Y, res, opt);
    ui.setResultView(corrView);
    ui.setStatusWithTime("sorted.");
    return true;
  }
}

