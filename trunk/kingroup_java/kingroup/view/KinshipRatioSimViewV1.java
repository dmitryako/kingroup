package kingroup.view;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.KinPairSimModel;
import kingroup.model.HypothesisModel;
import kingroup.model.RatioModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.tablex.JTableFactory;
import javax.swingx.text_fieldx.IntTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
public class KinshipRatioSimViewV1 extends GridBagView {
  private final static int FIELD_SIZE = 5;
  private final static int MIN_SIM_PAIRS = 0;
  private final static int DEFAULT_SIM_PAIRS = 1000;
  private final static int MAX_SIM_PAIRS = 10000;
  protected final static int TYPE_I_ERROR_IDX = 0;
  protected final static int SIG_LEVEL_IDX = 1;
  protected final static int TYPE_II_ERROR_IDX = 2;
  protected final int TABLE_COLUMN_COUNT = 3;
  private JCheckBox simulate_ = new JCheckBox("Simulate ");
  private IntTextField numRuns_ = new IntTextField(FIELD_SIZE
    , DEFAULT_SIM_PAIRS, MIN_SIM_PAIRS, MAX_SIM_PAIRS);
  private JCheckBox sigFlag_ = new JCheckBox("Flag");
  private JTable confTable_ = new JTable();
  private JScrollPane confView_ = new JScrollPane();
  protected String[][] tableData_;
  private HypothesisModel primModel_;
  private HypothesisModel nullModel_;
  private String simTableCol1 = "p<";
  private String simTableCol2 = "N/A";
  private String simTableCol3 = "Type II error";
  public KinshipRatioSimViewV1(RatioModel ratioModel) {
    primModel_ = ratioModel.getHypoPrimModel();
    nullModel_ = ratioModel.getHypoNullModel();
    init();
    loadFrom(ratioModel.getSimulationModel());
    assemble();
  }
  public void rebuild(RatioModel model) {
    loadFrom(model.getSimulationModel());
    assemble();
  }
  public void enableSimulation(boolean b) {
    if (!b)
      simulate_.setSelected(b);
    simulate_.setEnabled(b);
  }
  public void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Significance Levels");
    setBorder(titled);
    simulate_.setToolTipText("Generate random pairs in order to findFirstIdxSLOW the significance levels");
    simulate_.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        numRuns_.setEnabled(simulate_.isSelected());
      }
    });
    sigFlag_.setToolTipText("Flag ratios against the simulatated significance levels");
  }
  public void loadFrom(KinPairSimModel model) {
    float[] typeOne = model.getPLevels();  // type I errors
    if (typeOne == null) {
      float[] ds = {0.05f, 0.01f, 0.001f};
      typeOne = ds;
      model.setPLevels(typeOne);
    }
    tableData_ = new String[typeOne.length][TABLE_COLUMN_COUNT];
    loadTypeOneErrorColumn(model);
    simulate_.setSelected(model.getPerformSigTest());
    numRuns_.setValue(model.getNumRuns());
    numRuns_.setEnabled(model.getPerformSigTest());
    sigFlag_.setSelected(model.getDisplaySigFlag());
  }
  public void loadTo(KinPairSimModel model) {
    model.setPerformSigTest(simulate_.isSelected());
    model.setNumRuns(numRuns_.getInput());
    model.setDisplaySigFlag(sigFlag_.isSelected());
  }
  public void loadTypeOneErrorColumn(KinPairSimModel model) {
    float[] levels = model.getPLevels();  // type I errors
    NumberFormat nf = NumberFormat.getNumberInstance();
    int i = 0;
    nf.setMaximumFractionDigits(0); // 0.05, 0.01, 0.001
    tableData_[i][TYPE_I_ERROR_IDX] = "*" + nf.format(100. * levels[i]) + "%";
    i++;
    tableData_[i][TYPE_I_ERROR_IDX] = "**" + nf.format(100. * levels[i]) + "%";
    i++;
    nf.setMaximumFractionDigits(1); // 0.05, 0.01, 0.001
    tableData_[i][TYPE_I_ERROR_IDX] = "***" + nf.format(100. * levels[i]) + "%";
  }
  //*************************************************
  // Need to addSimulation a rebuild simulation panel here
  public void assemble() {
    removeAll();
    buildTable(tableData_);
    startRow(sigFlag_);
    startRow(simulate_);
    startRow(numRuns_);
    endRow(new JLabel(""));
    endRow(confView_);
    invalidate();
    Dimension dim = getPreferredSize();
    setMinimumSize(dim);
  }
  public String getColumnName(int column) {
    if (confTable_ == null
      || confTable_.getColumnCount() <= column)
      return "N/A";
    return confTable_.getColumnName(column);
  }
  private void buildTable(String[][] t) {
    String[] columnNames = {simTableCol1, simTableCol2, simTableCol3}; // set up table colums
    confTable_ = new JTable(t, columnNames);
    JTableFactory.initColumnSizes(confTable_);
    confView_ = new JScrollPane(confTable_);
    confView_.setPreferredSize(new Dimension(200, 72));
    confTable_.setEnabled(false);
    confView_.setEnabled(false);
  }
  public String getPairs() {
    return numRuns_.getText();
  }
  public void setPairsStr(int i) {
    numRuns_.setValue(i);
  }
  public void loadSignificanceLevels(double[] levels) {
    for (int i = 0; i < levels.length; i++)
      tableData_[i][SIG_LEVEL_IDX] = primModel_.format(levels[i]);
    if (primModel_.getDisplayLogs())
      simTableCol2 = "log(Ratio)";
    else
      simTableCol2 = "Ratio";
  }
  public void loadTypeTwoErrorColumn(double[] levels) {
    NumberFormat nf2 = NumberFormat.getNumberInstance();
    nf2.setMaximumFractionDigits(0);
    NumberFormat nf3 = NumberFormat.getNumberInstance();
    nf3.setMaximumFractionDigits(1);
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < levels.length; i++) {
      buff.setLength(0);
      if (levels[i] >= 0.1)
        buff.append(nf2.format(100. * levels[i]));
      else
        buff.append(nf3.format(100. * levels[i]));
      tableData_[i][TYPE_II_ERROR_IDX] = buff.append('%').toString();
    }
    assemble();
    repaint();
    revalidate();
  }
  public void updateTable() {
    confTable_.revalidate();
    confTable_.repaint();
    repaint();
    revalidate();
  }
  // FOR PRODUCING A REPORT FOR OUTPUT
  public HypothesisModel getPrimarySettings() {
    return primModel_;
  }
  public HypothesisModel getNullSettings() {
    return nullModel_;
  }
  public Object getValueAt(int r, int c) {
    Object obj = confTable_.getValueAt(r, c);
    if (obj == null)
      return " ";
    else
      return obj;
  }
}


























