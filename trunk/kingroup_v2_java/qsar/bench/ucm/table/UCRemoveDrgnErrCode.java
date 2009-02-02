package qsar.bench.ucm.table;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.descriptors.dragon.EDragon;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableView;
import javax.swing.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/06/2007, 13:02:44
 */
public class UCRemoveDrgnErrCode implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSortZRowsByY.class);
  private static final int IDX_Y = 0;

  public boolean run() {
    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();

    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      log.trace("train == null   ||  train.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    zTrain.replace(EDragon.ERROR_CODE, 0);
    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(zTrain, project.getTableDisplayOpt());
    ui.setZTableView(tableView);
    JOptionPane.showMessageDialog(ui, "Done.");
    return true;
  }
}

