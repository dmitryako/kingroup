package qsar.bench.ucm.table;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableView;
import javax.utilx.arrays.vec.Vec;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/11/2007, Time: 10:00:41
 */
public class UCInvertCols  implements UCController
{
  private final static ProjectLogger log = ProjectLogger.getLogger(UCInvertCols.class);

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
    return invert(zTrain, cols);
  }

  protected boolean invert(Table zTrain, BitSet cols) {  log.trace("cols:", cols);
    Table zT = zTrain.trans();
    zTrain = null;  System.gc();
    double[][] mtrx = zT.getMtrx();
    for (int i = 0; i < mtrx.length; i++) {
      double[] v = mtrx[i];
      if (cols.get(i)  &&  Vec.findFirstIdx(v, 0) == -1)
        Vec.invert(v);
    }

    zTrain = zT.trans();
    zT = null; System.gc();

    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(zTrain, project.getTableDisplayOpt());
    QBenchMainUI ui = QBenchMainUI.getInstance();
    ui.setZTableView(tableView);

    return true;
  }
}

