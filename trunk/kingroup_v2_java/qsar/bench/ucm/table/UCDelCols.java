package qsar.bench.ucm.table;
import qsar.bench.QBenchMainUI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 5/07/2007, 11:07:06
 */
public class UCDelCols extends UCDelColsCommon  {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCDelCols.class);

  public boolean run() {
    log.trace("run()");
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
    BitSet delCols = UCSelectCols.getSelected();
    if (delCols == null)
      return false;

    BitSet cols = new BitSet();
    cols.set(0, zTrain.getNumCols(), true);
    cols.xor(delCols);
    if (cols.cardinality() == 0) {
      JOptionPane.showMessageDialog(ui, "Unable to delete ALL columns.");
      return true;
    }
    return displayRes(zTrain, cols);
  }
}

