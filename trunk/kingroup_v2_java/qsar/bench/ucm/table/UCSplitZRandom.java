package qsar.bench.ucm.table;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBench;
import qsar.bench.QBenchProject;

import javax.iox.table.Table;
import javax.iox.table.TableFactory;
import javax.iox.table.TableView;
import javax.iox.table.TablePair;
import javax.utilx.arrays.mtrx.MtrxPair;
import javax.utilx.arrays.mtrx.MtrxFactory;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/02/2008, Time: 11:57:10
 */
public class UCSplitZRandom implements UCController
{
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSplitZRandom.class);

  public boolean run() {
    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();

    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      log.trace("train == null   ||  train.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    int n = zTrain.getNumRows();
    int nV = n / 2;

    TablePair split;
    split = TableFactory.splitRandRows(nV, zTrain);
    Table V = split.getA(); // validate/test
    Table C = split.getB(); // calibrate/train

    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(V, project.getTableDisplayOpt());
    ui.setTestTableView(tableView);
    tableView = new TableView(C, project.getTableDisplayOpt());
    ui.setZTableView(tableView);
    ui.setStatusWithTime("done.");
    return true;
  }
}

