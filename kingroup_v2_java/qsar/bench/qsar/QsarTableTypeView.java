package qsar.bench.qsar;
import pattern.ucm.AdapterUCCToALThread;
import pattern.ucm.UCShowHelpMssg;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 15:18:51
 */
public class QsarTableTypeView extends GridBagView
//  implements QBenchViewI
{
  private JRadioButton train;
  private JRadioButton test;
  private JRadioButton predict;

  public QsarTableTypeView(QsarTypeI model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Table");
    setBorder(titled);

    train = new JRadioButton("train", false);
//    train.setEnabled();
    test = new JRadioButton("test", false);
    predict = new JRadioButton("predict", false);
    ButtonGroup delimGroup = new ButtonGroup();
    delimGroup.add(train);
    delimGroup.add(test);
    delimGroup.add(predict);
    train.setSelected(true); //default

  }
  public void setEnabledTrain(boolean b) {
    train.setEnabled(b);
  }
  public void setEnabledTest(boolean b) {
    test.setEnabled(b);
  }
  public void setEnabledPredict(boolean b) {
    predict.setEnabled(b);
  }
  public void setEnabled(boolean b) {
    train.setEnabled(b);
    test.setEnabled(b);
    predict.setEnabled(b);
  }
  private void assemble() {
    String title = "Format of imported training table";
    JButton bttn = ProjectFrame.makeHelpButton();
    bttn.setToolTipText(title);
    AdapterUCCToALThread uc = new AdapterUCCToALThread(new UCShowHelpMssg(
      title + ":\n * Activity values are in data column #1."
        + "\n * Descriptor #1 is in data column #2, etc."
        + "\n * Compound #1 is in data row #1, etc."
      , QBenchFrame.getInstance()));
    bttn.addActionListener(uc);
//    train.setToolTipText(title);
    startRow(train);
    endRow(bttn);

    title = "Format of imported test table";
    bttn = ProjectFrame.makeHelpButton();
    bttn.setToolTipText(title);
    bttn.addActionListener(uc);
//    test.setToolTipText(title);
    startRow(test);
    endRow(bttn);

    title = "Format of imported prediction table";
    bttn = ProjectFrame.makeHelpButton();
    bttn.setToolTipText(title);
    uc = new AdapterUCCToALThread(new UCShowHelpMssg(
      title + ":\n * Descriptor #1 is in data column #1, etc."
        + "\n * Compound #1 is in data row #1, etc."
      , QBenchFrame.getInstance()));
    bttn.addActionListener(uc);
//    predict.setToolTipText(title);
    startRow(predict);
    endRow(bttn);
  }
  public void loadTo(QsarTypeI model)
  {
    model.setQsarType(QBench.QSAR_CALIB);
    if (test.isSelected())
      model.setQsarType(QBench.QSAR_VALID);
    if (predict.isSelected())
      model.setQsarType(QBench.QSAR_PREDICT);
  }

  public void loadFrom(QsarTypeI model)
  {
    train.setSelected(model.getQsarType() == QBench.QSAR_CALIB);
    test.setSelected(model.getQsarType() == QBench.QSAR_VALID);
    predict.setSelected(model.getQsarType() == QBench.QSAR_PREDICT);
  }
}
