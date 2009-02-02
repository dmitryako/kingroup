package qsar.bench.qsar.MLR;
import qsar.bench.QBench;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 13:14:53
 */
public class MlrSelectAlgView   extends GridBagView implements QBenchViewI {
  private final static ProjectLogger log = ProjectLogger.getLogger(MlrSelectAlgView.class);
  protected JRadioButton algMlr;
  protected JRadioButton alg_kNN;

  protected JRadioButton cvNone;
  protected JRadioButton cvLoo;
  protected JRadioButton cvMc;
  protected JRadioButton vsSA;
  protected JRadioButton vsGA;

  public MlrSelectAlgView(QBench bean, String mssg) {
    log.trace("MlrSelectAlgView(QBench, ", mssg);
    init();
    loadFrom(bean);
    assemble(mssg);
  }
  public MlrSelectAlgView() {
    init();
  }
  public void loadFrom(QBench from) {
    int cv = from.getCrossValidId();
    cvNone.setSelected(true); // default
    cvLoo.setSelected(cv == from.CV_LOO);
    cvMc.setSelected(cv == from.CV_MCCV);
    vsSA.setSelected(cv == from.CV_MCVS_SA);
    vsGA.setSelected(cv == from.CV_MCVS_GA);

    int alg = from.getQsarAlgId();
    algMlr.setSelected(true); // default
    alg_kNN.setSelected(alg == from.ALG_kNN);
  }
  public void loadTo(QBench model) {
    model.setQsarAlgId(model.ALG_MLR);
    if (alg_kNN.isSelected())
      model.setQsarAlgId(model.ALG_kNN);
    log.debug("getQsarAlgId=", model.getQsarAlgId());

    model.setCrossValidId(model.CV_NONE);
    if (cvLoo.isSelected()) {
      model.setCrossValidId(model.CV_LOO);
    }
    else if (cvMc.isSelected()) {
      model.setCrossValidId(model.CV_MCCV);
    }
    else if (vsSA.isSelected()) {
      model.setCrossValidId(model.CV_MCVS_SA);
    }
    else if (vsGA.isSelected()) {
      model.setCrossValidId(model.CV_MCVS_GA);
    }
    log.debug("getCrossValidId=", model.getCrossValidId());
  }
  private void init() {
    log.trace("init()");
    algMlr = new JRadioButton("MLR", true);
    algMlr.setToolTipText("Multiple Linear Regression");
//    mlr.setToolTipText(MCSAlg.REFERENCE);
    alg_kNN = new JRadioButton("kNN-MLR", false);
    alg_kNN.setToolTipText("k-Nearest Neighbors MLR");
//    ms.setToolTipText(MSAlgV2.REFERENCE);
    ButtonGroup group = new ButtonGroup();
    group.add(algMlr);
    group.add(alg_kNN);

    cvNone = new JRadioButton("None", true);
    cvLoo = new JRadioButton("LOO-CV", false);
    cvLoo.setToolTipText("Leave-One-Out cross validation");

    cvMc = new JRadioButton("MCCV", false);
    cvMc.setToolTipText("Monte Carlo cross validation");

    vsSA = new JRadioButton("MCVS via Simulated Annealing (SA)", false);
    vsSA.setToolTipText("Monte Carlo Variable Selection vi SA");

    vsGA = new JRadioButton("MCVS via Genetic Algorithm (GA)", false);
    vsGA.setToolTipText("Monte Carlo Variable Selection vi GA");

    group = new ButtonGroup();
    group.add(cvNone);
    group.add(cvLoo);
    group.add(cvMc);
    group.add(vsSA);
    group.add(vsGA);
  }
  protected void assemble(String mssg) {
    if (mssg != null  && mssg.length() != 0)
      endRow(new JLabel(mssg));
    endRow(assembleAlg());
    endRow(assembleCV());
  }
  protected JPanel assembleAlg() {
    GridBagView panel = new GridBagView("Algorithm");
    panel.endRow(algMlr);
    panel.endRow(cvLoo);
    panel.endRow(alg_kNN);
    return panel;
  }
  protected JPanel assembleCV() {
    GridBagView panel = new GridBagView("Cross Validation");
    panel.endRow(cvNone);
    panel.endRow(cvLoo);
    panel.endRow(cvMc);

    panel.endRow(new JLabel());
    panel.endRow(new JLabel("Monte Carlo Variable Selection (MCVS)"));
    panel.endRow(vsSA);
    panel.endRow(vsGA);
    return panel;
  }
}
