package qsar.bench.ucm;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import javax.iox.TextFile;
import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/03/2007, 12:27:59
 */
public class QBenchResults //implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(QBenchResults.class.getName());
  private File file;

  public QBenchResults(File file) {
    this.file = file;
  }

  public boolean run() {
    QBenchMainUI ui = QBenchMainUI.getInstance();
    JTable table = ui.getSelectedResultsTable();
    if (table == null)   {
      JOptionPane.showMessageDialog(QBenchFrame.getInstance()
        , "First Calculate/Select/View results you want to save.");
      return false;
    }

    QBench model = QBenchProject.getInstance();
    String name = model.getResultsFileName();
    File file = model.makeFile(name);

    TextFile to = new TextFile();
    to.setFileName(file.getName());

    String header = writeFileHeader(to, model);
    write(to, table, model.getColumnDelim(), false);

    JFrame frame = QBenchFrame.getInstance();
    to.write(file, frame);
    ui.setStatus(header);
    return true;
  }

  private static String writeFileHeader(TextFile to, QBench project)
  {
    int SHOW_LEVELS = 3;
    String file = FileX.getShortFileName(project.getResultsFileName(), SHOW_LEVELS);
    SimpleDateFormat date = new SimpleDateFormat();
    try {
//      date.applyPattern("HH:mm  EEE MMM d yyyy");
      date.applyPattern("HH:mm d.MMM.yyyy");
    }
    catch (IllegalArgumentException e) {
    }
    String res = file + " saved from " + project.getAppName() + "_" + project.getAppVersion()
    + " at " + date.format(new Date());
    to.addLine(res);
    return res;
  }

  public static void write(TextFile to, JTable table, char delim, boolean columnHeaders) {
    if (table == null)
      return;
    StringBuffer buff = new StringBuffer();
    buff.setLength(0);
    if (columnHeaders) {
      for (int c = 0; c < table.getColumnCount(); c++) {
        buff.append(table.getColumnName(c));
        if (c != table.getColumnCount() - 1)
          buff.append(delim);
      }
      to.addLine(buff.toString());
    }
    for (int r = 0; r < table.getRowCount(); r++) {
      buff.setLength(0);
      for (int c = 0; c < table.getColumnCount(); c++) {
        String cell = (String) table.getValueAt(r, c);
        buff.append(cell);
        if (c != table.getColumnCount() - 1)
          buff.append(delim);
      }
      to.addLine(buff.toString());
    }
  }
}
