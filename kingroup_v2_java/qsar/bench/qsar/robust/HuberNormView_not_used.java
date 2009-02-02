package qsar.bench.qsar.robust;
import qsar.bench.QBench;
import qsar.refs.Huber_1973;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.FloatTextField;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/03/2008, Time: 14:32:47
 */
public class HuberNormView_not_used extends GridBagView
{
  private final static ProjectLogger log = ProjectLogger.getLogger(RobustTypeSelectView.class);
  private JRadioButton huber;
  protected FloatTextField huberParam;
  private static final int FLOAT_SIZE = 5;

  private JRadioButton lae;

  public HuberNormView_not_used(QBench bean) {
    super("Robust norms");
    init();
    loadFrom(bean);
    assemble();
  }
  public void loadFrom(QBench bean) {
    int norm = bean.getRobustType();
    huber.setSelected(true); // default
//    alg_kNN.setSelected(alg == bean.ALG_kNN);
    huberParam.setValue(bean.getCltsNumStarts());
  }
  public void loadTo(QBench model) {
//    model.setCltsNumStarts(huberParam.getInput());
    model.setRobustType(model.ROBUST_HUBER);
//    if (alg_kNN.isSelected())
//      model.setQsarAlgId(model.ALG_kNN);
    log.debug("getRobustNormId=", model.getRobustType());
  }
  private void init() {
    log.trace("init()");
    huber = new JRadioButton("Huber", true);
    huber.setToolTipText(Huber_1973.REFERENCE);
    huberParam = new FloatTextField(FLOAT_SIZE, Huber_1973.HUBER_MIN, Huber_1973.HUBER_MAX);
    huberParam.setToolTipText("default 1.345");

    ButtonGroup group = new ButtonGroup();
    group.add(huber);
  }
  private void assemble() {
    startRow(huber);
    endRow(huberParam);
  }
}
