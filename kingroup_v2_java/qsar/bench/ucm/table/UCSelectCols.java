package qsar.bench.ucm.table;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.table.TableSelectColsView;
import tsvlib.project.ProjectLogger;

import javax.iox.TableFormat;
import javax.iox.table.Table;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 5/07/2007, 11:11:51
 */
public class UCSelectCols
{
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSelectCols.class);

  public static boolean select(String mssg) {
    log.trace("select(", mssg);
    QBench project = QBenchProject.getInstance();
    TableFormat format = project.getSelectTable();
    TableSelectColsView panel = new TableSelectColsView(format, mssg);
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

  public static BitSet getSelected()
  {
    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table zTrain = ui.getZTable();
    QBench project = QBenchProject.getInstance();
    TableFormat format = project.getSelectTable();
    int first = format.getFirstCol();
    int last = format.getLastCol();
    if (last == 0) {  // indicates the last available column
      last = zTrain.getNumCols();
    }
    if (first > last) {
      JOptionPane.showMessageDialog(ui, "Unable to proceed: the selected first column is AFTER the last column");
      return null;
    }

    BitSet res = new BitSet();
    res.set(first-1, last, true);
    return res;
  }
}
