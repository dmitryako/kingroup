package qsar.bench.ucm.smiles;
import kingroup_v2.io.FileIO;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.ucm.table.import_table.UCImportTable;
import tsvlib.project.ProjectLogger;

import javax.iox.*;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import javax.swingx.UCShowImportFileUI;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/03/2007, 15:25:19
 */
public class UCImportSmilesTable   implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCImportTable.class.getName());
  private File file;

  public UCImportSmilesTable(File file) {
    this.file = file;
  }
  public TableFormatView makeFormatView(QBench model) {
    return new TableFormatView(model.getSmilesTableFormat());
  }
  public void setTableView(StrTableView tableView, QBenchMainUI ui) {
    ui.setSmilesView(tableView);
  }

  public boolean run() {
    read(file);
    return true;
  }
  public void read(File file) {
    QBench project = QBenchProject.getInstance();
    JFrame frame = QBenchFrame.getInstance();
    if (frame != null && file != null) {
      frame.setTitle(project.getAppName() + " "
        + project.getAppVersion()
        + "  [imported file: " + file.getName() + "]");
    }
    TableFormat format = project.getSmilesTableFormat();
    TextFile fileModel = new TextFile();
    fileModel.setFileName(file.getName());
    fileModel.read(file, frame);

    TextFileView fileView = new TextFileView(fileModel);
    TableFormatView formatView = makeFormatView(project);
    formatView.setLastColEnabled(false);
    ApplyDialogUI dlg = new UCShowImportFileUI(FileIO.combine(formatView, fileView)
      , frame, "Import " + file.getName());
    dlg.setApplyBttnText("Import");
    dlg.setVisible(true);
    if (!dlg.apply())
      return;

    formatView.loadTo(format);
    format.setLastCol(format.getFirstCol());  // import one
    project.saveProjectToDefaultLocation(); // remember user input

    QBenchMainUI ui = QBenchMainUI.getInstance();
    ui.setImportFileView(fileView);

    StrTable table = StrTableReader.makeFromFile(fileModel, format, true);
    if (table == null) {
      String error = "Unable to import table from file \n"
        + fileModel.getFileName();
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
      return;
    }

    StrTableView tableView = new StrTableView(table);
    setTableView(tableView, ui);
//    commonFinish(ui, usrPop, format);
//    setupGroups(ui, format);
  }
//  public TableFormatView makeFormatView(QBench project) {
//    return new TableFormatView(project.getTrainTableFormat());
//  }
//  public void setTableView(TableView tableView, QBenchMainUI ui) {
//  }
//  public TableFormat getTableFormat(QBench project) {
//    return project.getTrainTableFormat();
//  }
}


