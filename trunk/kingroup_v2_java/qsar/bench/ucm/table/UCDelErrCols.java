package qsar.bench.ucm.table;
import qsar.bench.QBenchMainUI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;
import javax.utilx.arrays.mtrx.Mtrx;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/12/2007, Time: 17:25:42
 */
public class UCDelErrCols extends UCDelColsCommon  {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCDelConstCols.class);
  private int code;

  public UCDelErrCols(int code) {
    this.code = code;
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
    BitSet cols = Mtrx.findColsWith(code, zTrain.getByRows());
    if (cols.cardinality() == 0) {
      JOptionPane.showMessageDialog(ui, "There are no columns with error " + code);
      return true;
    }
    BitSet keep = new BitSet();
    int nCols = zTrain.getNumCols();
    keep.set(0, nCols);
    keep.xor(cols);
    return displayRes(zTrain, keep);

  }
}

