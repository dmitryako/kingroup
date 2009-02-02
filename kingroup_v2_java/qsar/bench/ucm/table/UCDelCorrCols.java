package qsar.bench.ucm.table;
import qsar.bench.QBenchMainUI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;
import javax.utilx.arrays.mtrx.Mtrx;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/06/2007, 15:26:34
 */
public class UCDelCorrCols extends UCDelColsCommon {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCDelCorrCols.class);
  private double maxCorr;
  private static final int IDX_X = 1;

  public UCDelCorrCols(double maxCorr) {
    this.maxCorr = maxCorr;
  }
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
    BitSet cols = Mtrx.getDiffCorrCols(zTrain.getByRows(), maxCorr, IDX_X);
    if (cols.cardinality() == nCols) {
      JOptionPane.showMessageDialog(ui
        , "There are no correlated columns in X with r>" + (float)maxCorr);
      return true;
    }
    return displayRes(zTrain, cols);

  }
}
