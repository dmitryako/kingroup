package qsar.bench.ucm.table;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableFactory;
import javax.iox.table.TableView;
import javax.swing.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 8/06/2007, 10:24:16
 */
public class UCMeanZeroVarOne  implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCMeanZeroVarOne.class);
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

    double[] Y = zTrain.getColCopy(0); // save
    Table res = TableFactory.makeMeanZeroVarOne(zTrain);
    res.setCol(IDX_Y, Y);   // restore
    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(res, project.getTableDisplayOpt());
    ui.setZTableView(tableView);

    JOptionPane.showMessageDialog(ui, "Done.");
    return true;
  }
}

