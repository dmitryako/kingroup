package qsar.bench.ucm.table;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableView;
import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/11/2007, Time: 14:42:07
 */
public class UCTranspose  implements UCController
{
  private final static ProjectLogger log = ProjectLogger.getLogger(UCTranspose.class);

  public boolean run() {
    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();

    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      log.trace("train == null   ||  train.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }

    Table res = zTrain.trans();
    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(res, project.getTableDisplayOpt());
    ui.setZTableView(tableView);
    JOptionPane.showMessageDialog(ui, "Done.");
    return true;
  }
}

