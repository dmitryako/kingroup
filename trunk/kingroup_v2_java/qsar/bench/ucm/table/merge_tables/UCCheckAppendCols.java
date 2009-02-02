package qsar.bench.ucm.table.merge_tables;
import pattern.ucm.UCController;
import qsar.bench.QBenchMainUI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 3/06/2007, 15:06:39
 */
public class UCCheckAppendCols implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCCheckAppendCols.class);

  public boolean run() {
    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();

    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      log.trace("train == null   ||  train.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    Table xPred = ui.getXTable();
    if (xPred == null   ||  xPred.getNumRows() == 0) {
      log.trace("pred == null   ||  pred.getNumRows() == 0");
      ui.showLoadPredictTableFirst();
      return false;
    }

    if (zTrain.getNumRows() != xPred.getNumRows()) {
      log.trace("train.getNumRows() != pred.getNumRows()");
      JOptionPane.showMessageDialog(ui
        , "Unable to merge:"
      + "\nDifferent number of rows (NR) in TRAINING[NR=" + zTrain.getNumRows()
        +"] and PREDICTION[NR=" + xPred.getNumRows()
        +"] tables.");
      return false;
    }

    return true;
  }
}

