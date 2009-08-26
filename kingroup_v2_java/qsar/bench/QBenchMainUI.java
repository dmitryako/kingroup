package qsar.bench;
import pattern.mvc.MVCTableView;
import qsar.bench.descriptors.LFER.LferAlgUI;
import qsar.bench.qsar.MLR.MlrAlgUI;

import javax.iox.StrTable;
import javax.iox.StrTableView;
import javax.iox.TextFileView;
import javax.iox.table.Table;
import javax.iox.table.TableDisplayOpt;
import javax.iox.table.TableView;
import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import javax.swingx.textx.TextView;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2007, Time: 11:06:21
 */
public class QBenchMainUI  extends JPanel {
  private static QBenchMainUI instance;
  private JTabbedPaneX tabbedPane;
  private JLabel status;

  private TextView consoleView;
  private int consoleIdx = -1;
  private JRadioButton consoleFocus;

  private TextFileView importFileView;
  private int importFileIdx = -1;
  private JRadioButton importFileFocus;

  private MlrAlgUI mlrView;
  private int mlrIdx = -1;
  private JRadioButton mlrFocus;

  private LferAlgUI lferView;
  private int lferIdx = -1;
  private JRadioButton lferFocus;

  private TableView zTableView;
  private int zIdx = -1;
  private JRadioButton zFocus;

  private MVCTableView resultView;
  private int resultIdx = -1;
  private JRadioButton resultFocus;

  private TableView testTableView;
  private int testIdx = -1;
  private JRadioButton testFocus;

  private TableView xTableView;
  private int xIdx = -1;
  private JRadioButton xFocus;

  private StrTableView smilesView;
  private int smilesIdx = -1;
  private JRadioButton smilesFocus;

  private QBenchMainUI() {
    init();
  }
  public static QBenchMainUI getInstance() {
    if (instance == null) {
      instance = new QBenchMainUI();
    }
    return instance;
  }

  synchronized public void repaint()
  {
    super.repaint();
  }

  synchronized public void validate()
  {
    super.validate();
  }

  public void removeQsarResults() {
    mlrView = null; // removing MLR
    rebuildAll();
  }
  public void rebuildAll() {
    tabbedPane.removeAll();
    importFileIdx = -1;
    zIdx = -1;
    testIdx = -1;
    xIdx = -1;
    mlrIdx = -1;
    smilesIdx = -1;
    lferIdx = -1;
    rebuild();
  }
  public void removeStructResults() {
    lferView = null; // removing LFER
    rebuildAll();
  }
  private void init()
  {
    consoleFocus = new JRadioButton();
    importFileFocus = new JRadioButton();
    zFocus = new JRadioButton();
    resultFocus = new JRadioButton();
    testFocus = new JRadioButton();
    xFocus = new JRadioButton();
    mlrFocus = new JRadioButton();
    lferFocus = new JRadioButton();
    smilesFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(consoleFocus);
    group.add(importFileFocus);
    group.add(zFocus);
    group.add(resultFocus);
    group.add(testFocus);
    group.add(xFocus);
    group.add(mlrFocus);
    group.add(lferFocus);
    group.add(smilesFocus);
    importFileFocus.setSelected(true);
    tabbedPane = new JTabbedPaneX(JTabbedPane.TOP);
    setLayout(new BorderLayout());
    add(tabbedPane, BorderLayout.CENTER);

    status = new JLabel(" ", SwingConstants.LEFT);
    add(status, BorderLayout.SOUTH);
  }
  public void setStatus(String msg) {
    status.setText(msg);
  }
  public void setStatusWithTime(String msg) {
    SimpleDateFormat date = new SimpleDateFormat();
    try {
      date.applyPattern("HH:mm:ss");
    }
    catch (IllegalArgumentException e) {
    }
    status.setText(date.format(new Date()) + " : " + msg);
  }
  private void rebuild() {
    QBench project = QBenchProject.getInstance();
    int maxCols = project.getTableDisplayOpt().getMaxNumCols();
    String maxNColsMssg = "";
    if (maxCols > 0)
      maxNColsMssg = " [first " + maxCols + " columns]";

    consoleIdx = tabbedPane.processView(consoleView, consoleIdx, consoleFocus.isSelected()
      , "Console", "Console output for system-wide messages.");
    if (consoleIdx != -1) {
      tabbedPane.setBackgroundAt(consoleIdx, project.getColorConsole());
    }
    importFileIdx = tabbedPane.processView(importFileView, importFileIdx, importFileFocus.isSelected()
      , "Imported File", "Last Imported File");
    zIdx = tabbedPane.processView(zTableView, zIdx, zFocus.isSelected()
      , "Z", "Z=(Y,X), e.g. CALIBRATION/TRAINING Table" + maxNColsMssg);
    testIdx = tabbedPane.processView(testTableView, testIdx, testFocus.isSelected()
      , "Z_v", "VALIDATION/TEST Table, Z=(Y,X)" + maxNColsMssg);
    xIdx = tabbedPane.processView(xTableView, xIdx, xFocus.isSelected()
      , "X", "e.g. PREDICTION Table, i.e. Y is missing." + maxNColsMssg);
    mlrIdx = tabbedPane.processView(mlrView, mlrIdx, mlrFocus.isSelected()
      , "MLR", "Multiple Linear Regression");
//    lferIdx = tabbedPane.processView(lferView, lferIdx, lferFocus.isSelected()
//      , "LFER", "Linear Free Energy Relation Descriptors of " + Abraham_1993_LFER.REFERENCE);
    resultIdx = tabbedPane.processView(resultView, resultIdx, resultFocus.isSelected()
      , "Result", "Result");

    JFrame frame = QBenchFrame.getInstance();
    if (frame != null) {
      frame.validate();
      frame.repaint();
    }
    validate();
    repaint();
  }
  public void showLoadTrainTableFirst() {
    JOptionPane.showMessageDialog(this
      , "First you need to import a TRAINING Z data set via MENU | FILE | IMPORT Z ...");
  }

  public void showNotImplemented() {
    JOptionPane.showMessageDialog(this
      , "This option has not been implemented yet.");
  }
  public void showLoadSmilesTableFirst() {
    JOptionPane.showMessageDialog(this
      , "First you need to import SMILES via MENU | FILE | IMPORT SMILES ...");
  }
  public void showLoadTestTableFirst() {
    JOptionPane.showMessageDialog(this
      , "First you need to import a VALIDATION/TEST Z data set via MENU | FILE | IMPORT Z_v for Validation...");
  }
  public void showLoadPredictTableFirst() {
    JOptionPane.showMessageDialog(this
      , "First you need to import a PREDICTION X data set via MENU | FILE | IMPORT X ...");
  }

  public void setImportFileView(TextFileView view) {
    this.importFileView = view;
    importFileFocus.setSelected(true);
    rebuild();
  }

  public TextFileView getImportFileView() {
    return importFileView;
  }

  public Table getZTable() {
    if (zTableView == null)
      return null;
    return zTableView.getTable();
  }

  public Table getTestZ() {
    if (testTableView == null)
      return null;
    return testTableView.getTable();
  }

  public Table getXTable() {
    if (xTableView == null)
      return null;
    return xTableView.getTable();
  }

  public void setZTableView(TableView tableView)
  {
    this.zTableView = tableView;
    zFocus.setSelected(true);
    rebuild();
  }

  public void setResultView(MVCTableView tableView)
  {
    this.resultView = tableView;
    resultFocus.setSelected(true);
    rebuild();
  }

  public void setSmilesView(StrTableView tableView)
  {
    this.smilesView = tableView;
    smilesFocus.setSelected(true);
    rebuild();
  }
  public StrTable getSmilesTable() {
    if (smilesView == null)
      return null;
    return smilesView.getTable();
  }

  public void setTestTableView(TableView tableView)
  {
    if (tableView == null) {
      this.testTableView = null;
      testIdx = -1;
      testFocus.setSelected(false);
    }
    else {
      this.testTableView = tableView;
      testFocus.setSelected(true);
    }
    rebuild();
  }
  public void setXTableView(TableView tableView)
  {
    this.xTableView = tableView;
    xFocus.setSelected(true);
    rebuild();
  }

  public TableView getZTableView()
  {
    return zTableView;
  }

  public TableView getTestTableView()
  {
    return testTableView;
  }

  public MlrAlgUI getMlrView()
  {
    if (mlrView == null) {
      mlrView = new MlrAlgUI();
    }
    setMlrView(mlrView); // NEED that to get focus
    return mlrView;
  }

  public LferAlgUI getLferView()
  {
    return lferView;
  }

  public void setMlrView(MlrAlgUI mlrView)
  {
    this.mlrView = mlrView;
    mlrFocus.setSelected(true);
    rebuild();
  }

  public void setLferView(LferAlgUI lferView)
  {
    this.lferView = lferView;
    lferFocus.setSelected(true);
    rebuild();
  }

  public JTable getSelectedResultsTable()
  {
    int idx = tabbedPane.getSelectedIndex();
    if (idx == -1)
      return null;

    if (zIdx == idx) { // NOTE!!!  Table's view may be truncated
      QBench project = QBenchProject.getInstance();
      TableDisplayOpt model = project.getTableDisplayOpt();
      TableDisplayOpt tmp = new TableDisplayOpt();
      tmp.setDigitsModel(model.getDigitsModel());
      tmp.setMaxNumCols(-1); //turn off
      tmp.setMaxNumRows(-1); //turn off
      tmp.setCountOn(false);
      return zTableView.makeTableView(tmp, this);
    }
    if (testIdx == idx) { // NOTE!!!  Table's view may be truncated
      QBench project = QBenchProject.getInstance();
      TableDisplayOpt model = project.getTableDisplayOpt();
      TableDisplayOpt tmp = new TableDisplayOpt();
      tmp.setDigitsModel(model.getDigitsModel());
      tmp.setMaxNumCols(-1); //turn off
      tmp.setMaxNumRows(-1); //turn off
      tmp.setCountOn(false);
      return testTableView.makeTableView(tmp, this);
    }
    if (mlrIdx == idx) {
      return mlrView.getSelectedResultsTable();
    }
    if (lferIdx == idx) {
      return lferView.getSelectedResultsTable();
    }
    if (resultIdx == idx) {
      return resultView.getTableView();
    }
    return null;
  }
  public void setConsoleView(TextView view) {
    this.consoleView = view;
    consoleFocus.setSelected(true);
    rebuild();
  }


  public void resetAll()
  {
    tabbedPane.removeAll();

    importFileView = null;     importFileIdx = -1;
    mlrView = null;            mlrIdx = -1;
    lferView = null;           lferIdx = -1;
    zTableView = null;         zIdx = -1;
    resultView = null;         resultIdx = -1;
    testTableView = null;      testIdx = -1;
    xTableView = null;         xIdx = -1;
    smilesView = null;         smilesIdx = -1;

    rebuild();
  }
}


