package qsar.bench.ucm.table;

import pattern.ucm.UCController;
import pattern.mvc.MVCTableView;
import tsvlib.project.ProjectLogger;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBench;
import qsar.bench.QBenchProject;
import qsar.bench.QBenchFrame;
import qsar.bench.qsar.table.CorrTableView;
import qsar.bench.qsar.table.TableSelectColsView;
import qsar.bench.qsar.table.TableFindColView;

import javax.iox.table.Table;
import javax.iox.table.TableFactory;
import javax.iox.table.TableDisplayOpt;
import javax.iox.table.TableView;
import javax.iox.TableFormat;
import javax.swingx.ApplyDialogUI;
import javax.swing.*;
import java.util.BitSet;

/**
 * Created by Dmitry.A.Konovalov@gmail.com using IntelliJ, 19/08/2009, 4:12:15 PM
 */
public class UCFindCol  implements UCController {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSortZColsByAbsCorrToY.class);
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

    if (!select("Find column")) {
      return false;
    }
    QBench project = QBenchProject.getInstance();
    TableFormat format = project.getSelectTable();
    String colName = format.getColName();
    Table res = zTrain.moveColByName(colName, IDX_Y+1);
    if (res == null) {
      JOptionPane.showMessageDialog(ui, "Column name not found.");
      return false;
    }

    TableDisplayOpt opt = project.getTableDisplayOpt();
    TableView tableView = new TableView(res, opt);
    ui.setZTableView(tableView);
    ui.setStatusWithTime("" + colName + " was moved to " + (IDX_Y +2) + " column");
    return true;
  }
  public static boolean select(String mssg) {
    log.trace("select(", mssg);
    QBench project = QBenchProject.getInstance();
    TableFormat format = project.getSelectTable();
    TableFindColView panel = new TableFindColView(format, mssg);
    ApplyDialogUI dlg = new ApplyDialogUI(panel, QBenchFrame.getInstance(), true);
    dlg.setTitle("Select");
    dlg.center();
    dlg.setFocusOnApply();
    dlg.setVisible(true);
    if (!dlg.apply()) {
      return false;
    }
    panel.loadTo(format);
    project.saveProjectToDefaultLocation();
    return true;
  }

}