package qsar.bench.ucm.table.import_table;
import kingroup_v2.io.FileIO;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.*;
import javax.iox.table.*;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import javax.swingx.UCShowImportFileUI;
import java.io.File;

/**
 * Copyright www.DmitryKonovalov.org
 * User: jc138691, Date: 6/03/2007, Time: 13:34:03
 */
public class UCImportTable  implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCImportTable.class);
  private File file;
//  private ProgressWnd progress = null;

  public UCImportTable(File file) {
    this.file = file;
  }
  public boolean run() {
//    QBench bean = QBenchProject.getInstance();
//    String name = bean.getTrainingTableName();
//    File file = bean.makeFile(name);

    try {
      read(file);
    } catch(Exception e) {
      log.error("Exception", e);
    } catch(java.lang.OutOfMemoryError e) {
      log.error("Exception", e);
    }
    return true;
  }
  public void read(File file) {
    log.trace("read");
//    progress = new ProgressWnd(QBenchMainUI.getInstance(), "reading table ...");
//    if (progress != null
//      && progress.isCanceled(i, 0, nIter))

    QBench project = QBenchProject.getInstance();
    JFrame frame = QBenchFrame.getInstance();
    if (frame != null && file != null) {
      frame.setTitle(project.getAppName() + " " + project.getAppVersion()
        + "  [imported file: " + file.getName() + "]");
    }
    TableFormat format = getTableFormat(project);
    TextFile from = new TextFile();
    from.setFileName(file.getName());
    from.read(file, frame);
    from.trim();
    log.trace("from = \n", from);

    TextFileView fileView = new TextFileView(from);
//    TableFormatView formatView = new TableFormatView(format);
    TableFormatView formatView = makeFormatView(project);
    ApplyDialogUI dlg = new UCShowImportFileUI(FileIO.combine(formatView, fileView)
      , frame, "Import " + file.getName());
    dlg.setApplyBttnText("Import");
    dlg.setVisible(true);
    if (!dlg.apply())
      return;

    char oldDelim = format.getColumnDelim();
    formatView.loadTo(format);
    if (oldDelim != format.getColumnDelim())
      project.setColumnDelim(format.getColumnDelim());

    project.saveProjectToDefaultLocation(); // remember user input

    QBenchMainUI ui = QBenchMainUI.getInstance();
    ui.setImportFileView(fileView);

    Table table = TableReader.makeFromFile(from, format, true);
    if (table == null) {
      String error = "Unable to import table from file \n"
        + from.getFileName();
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
      return;
    }
    ui.setStatus("Imported " + table.getNumRows() + " rows & " + table.getNumCols() + " columns.");

    TableDisplayOpt opt = project.getTableDisplayOpt();
    TableDisplayOptView optView = new TableDisplayOptView(opt);

    dlg = new UCShowTableDisplayOptUI(optView, frame, "Table");
    dlg.setApplyBttnText("Display");
    dlg.setVisible(true);
    if (!dlg.apply())
      return;
    optView.loadTo(opt);
    project.saveProjectToDefaultLocation(); // remember user input

    TableView tableView = new TableView(table, opt);
//    tableView.setRightMouseButton(new UCShowTableMenu(tableView));
    setTableView(tableView, ui);
  }
  public TableFormatView makeFormatView(QBench project) {
    return new TableFormatView(project.getTrainTableFormat());
  }
  public void setTableView(TableView tableView, QBenchMainUI ui) {
  }
  public TableFormat getTableFormat(QBench project) {
    return project.getTrainTableFormat();
  }
}

