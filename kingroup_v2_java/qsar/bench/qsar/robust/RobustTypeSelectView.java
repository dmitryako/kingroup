package qsar.bench.qsar.robust;
import qsar.bench.QBench;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/11/2007, Time: 17:07:46
 */
public class RobustTypeSelectView extends GridBagView
{
  private final static ProjectLogger log = ProjectLogger.getLogger(RobustTypeSelectView.class);
  private JRadioButton lta;
  private JRadioButton clts;
  private JRadioButton lmeda;
  private JRadioButton mclts;

  public RobustTypeSelectView(QBench bean) {
    super("Robust Estimator");
    init();
    loadFrom(bean);
    assemble();
  }
  public void loadFrom(QBench from) {
    int type = from.getRobustType();
    lta.setSelected(true); // default
    clts.setSelected(type == QBench.ROBUST_CLTS);
    mclts.setSelected(type == QBench.ROBUST_MCLTS);
    lmeda.setSelected(type == QBench.ROBUST_LMEDA);
  }
  public void loadTo(QBench model) {
    model.setRobustType(QBench.ROBUST_LTA);
    if (clts.isSelected()) {
      model.setRobustType(QBench.ROBUST_CLTS);
    }
    if (mclts.isSelected()) {
      model.setRobustType(QBench.ROBUST_MCLTS);
    }
    if (lmeda.isSelected()) {
      model.setRobustType(QBench.ROBUST_LMEDA);
    }
  }
  private void init() {
    log.trace("init()");
    clts = new JRadioButton("CLTS", true);
    clts.setToolTipText("Concentration Least Trimmed Squares (Rousseew&VanZomeren2006)");

    mclts = new JRadioButton("MCLTS", true);
    mclts.setToolTipText("Modified CLTS (Olive 2008)");

    lmeda = new JRadioButton("LMedA", true);
    lmeda.setToolTipText("Least Median Absolute errors");

    lta = new JRadioButton("LTA", true);
    lta.setToolTipText("Least Trimmed Sum of Absolute errors (Hawkins & Olive 1999)");

//    clts.setToolTipText(Huber_1973.REFERENCE);
//    huberParam = new FloatTextField(FLOAT_SIZE, Huber_1973.HUBER_MIN, Huber_1973.HUBER_MAX);
//    huberParam.setToolTipText("default 1.345");

    ButtonGroup group = new ButtonGroup();
    group.add(clts);
    group.add(mclts);
    group.add(lmeda);
    group.add(lta);
  }
  private void assemble() {
    startRow(lta);
    endRow(clts);
    startRow(mclts);
    endRow(lmeda);
  }
}

