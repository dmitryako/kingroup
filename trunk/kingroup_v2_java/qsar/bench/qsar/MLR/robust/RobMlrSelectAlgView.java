package qsar.bench.qsar.MLR.robust;
import qsar.bench.QBench;
import qsar.bench.qsar.MLR.MlrSelectAlgView;
import qsar.bench.qsar.robust.RobustTypeSelectView;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/11/2007, Time: 16:53:41
 */
public class RobMlrSelectAlgView  extends MlrSelectAlgView  {
  private final static ProjectLogger log = ProjectLogger.getLogger(RobMlrSelectAlgView.class);
  private RobustTypeSelectView typeView;

  public RobMlrSelectAlgView(QBench bean, String mssg) {
    log.trace("RobMlrSelectAlgView(QBench, ", mssg);
    init();
    loadFrom(bean);
    assemble(mssg);
  }
  public void loadFrom(QBench from) {
    super.loadFrom(from);
    typeView = new RobustTypeSelectView(from);
  }
  public void loadTo(QBench model) {
    super.loadTo(model);
    typeView.loadTo(model);
  }
  private void init() {
    log.trace("init()");
  }
  protected void assemble(String mssg) {
    if (mssg != null  && mssg.length() != 0)
      endRow(new JLabel(mssg));
    endRow(typeView);
    endRow(assembleAlg());
    endRow(assembleCV());
  }
  protected JPanel assembleAlg() {
    GridBagView panel = new GridBagView("Algorithm");
    panel.endRow(algMlr);
//      panel.endRow(cv_loo);
//    panel.endRow(alg_kNN);
    return panel;
  }
  protected JPanel assembleCV() {
    GridBagView panel = new GridBagView("Cross Validation");
    panel.endRow(cvNone);
//      panel.endRow(cv_loo);
    panel.endRow(cvMc);

//    panel.endRow(new JLabel());
//    panel.endRow(new JLabel("Monte Carlo Variable Selection (MCVS)"));
//    panel.endRow(vs_sa);
//    panel.endRow(vs_ga);
    return panel;
  }
}
