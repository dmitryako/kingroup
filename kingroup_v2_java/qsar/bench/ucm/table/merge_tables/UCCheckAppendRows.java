package qsar.bench.ucm.table.merge_tables;
import pattern.ucm.UCController;
import qsar.bench.QBenchMainUI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/11/2007, Time: 09:24:35
 */
public class UCCheckAppendRows  implements UCController
{
  private final static ProjectLogger log = ProjectLogger.getLogger(UCCheckAppendRows.class);

  public boolean run() {
    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();

    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumCols() == 0) {
      log.trace("zTrain == null   ||  zTrain.getNumCols() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    Table Zv = ui.getTestZ();   // validating set
    if (Zv == null   ||  Zv.getNumCols() == 0) {
      log.trace("Zv == null   ||  Zv.getNumCols() == 0");
      ui.showLoadTestTableFirst();
      return false;
    }

    if (zTrain.getNumCols() != Zv.getNumCols()) {
      log.trace("train.getNumCols() != pred.getNumCols()");
      JOptionPane.showMessageDialog(ui
        , "Unable to merge:"
      + "\nDifferent number of columns (NC) in TRAINING[NC=" + zTrain.getNumCols()
        +"] and VALIDATION[NC=" + Zv.getNumCols()
        +"] tables.");
      return false;
    }

    return true;
  }
}

