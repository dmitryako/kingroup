package qsar.bench.ucm.table.merge_tables;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.iox.table.TableView;
import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/11/2007, Time: 09:23:59
 */
public class UCAppendZvToZ  extends UCCheckAppendRows  {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCAppendZvToZ.class);

  public boolean run() {
    log.trace("run()");
    if (!super.run())
      return false;
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table zTrain = ui.getZTable();
    Table zTest = ui.getTestZ();

    Table zSorted = zTest.getColsByName(zTrain.getColIds());
    if (zSorted == null) {
      //todo
    }

    Table res = zTrain.appendRows(zSorted);
    QBench project = QBenchProject.getInstance();
//    TableView testView = new TableView(zSorted, project.getTableDisplayOpt());
//    ui.setTestTableView(testView);
    ui.setTestTableView(null);
    TableView trainView = new TableView(res, project.getTableDisplayOpt());
    ui.setZTableView(trainView);
    System.gc();
    JOptionPane.showMessageDialog(ui, "Done.");
    return true;
  }
}

