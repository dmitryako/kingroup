package qsar.bench.ucm.table;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableView;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/11/2007, Time: 09:43:51
 */
public class UCRandomizeCols implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCRandomizeCols.class);

  public boolean run() {    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      log.trace("train == null   ||  train.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    if (!UCSelectCols.select("Select column(s)")) {
      return false;
    }
    BitSet cols = UCSelectCols.getSelected();
    if (cols == null)
      return false;
    return makeRandom(zTrain, cols);
  }

  protected boolean makeRandom(Table zTrain, BitSet cols) {  log.trace("cols:", cols);
    int nCols = zTrain.getNumCols();
    BitSet constCols = new BitSet(nCols);
    constCols.set(0, nCols);     // making remaining
    constCols.xor(cols);     log.trace("constCols:\n", constCols);
    Table cTable = zTrain.getCols(constCols);  log.trace("not selected Table:\n", cTable);

    Table rTable = zTrain.getCols(cols);  log.trace("selected Table:\n", rTable);
    rTable = rTable.randomEachCol();      log.trace("random Table:\n", rTable);

    cTable = cTable.appendCols(rTable);            log.trace("final Table:\n", cTable);

    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(cTable, project.getTableDisplayOpt());
    QBenchMainUI ui = QBenchMainUI.getInstance();
    ui.setZTableView(tableView);

    return true;
  }
}

