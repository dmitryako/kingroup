package qsar.bench.ucm.table;
import qsar.bench.QBenchMainUI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;
import javax.utilx.arrays.mtrx.Mtrx;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/06/2007, 12:26:41
 */
public class UCDelDuplicateCols extends UCDelColsCommon  {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCDelDuplicateCols.class);

  public boolean run() {
    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();

    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      log.trace("train == null   ||  train.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    int nCols = zTrain.getNumCols();
    BitSet cols = Mtrx.getDiffCols(zTrain.getByRows());
    if (cols.cardinality() == nCols) {
      JOptionPane.showMessageDialog(ui, "There are no duplicate columns");
      return true;
    }
    return displayRes(zTrain, cols);
  }
}
