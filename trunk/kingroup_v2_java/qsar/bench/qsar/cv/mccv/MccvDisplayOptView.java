package qsar.bench.qsar.cv.mccv;
import qsar.bench.QBench;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/04/2008, Time: 13:47:11
 */
public class MccvDisplayOptView extends GridBagView implements QBenchViewI
{
  private final static ProjectLogger log = ProjectLogger.getLogger(MccvDisplayOptView.class);
  protected JCheckBox showGraph;
  protected JRadioButton mse;
  protected JRadioButton mae;
  protected JRadioButton medAE;
  protected JRadioButton tmse;
  protected JRadioButton tmae;
  protected JRadioButton robCorr;

  public MccvDisplayOptView(QBench bean) {
    super("MCCV Errors");
    init();
    loadFrom(bean);
    assemble();
  }

  public void loadTo(QBench model)
  {
    Mccv to = model.getMccv();
    to.setSaveCalibValidErrs(showGraph.isSelected());

    to.setErrorType(Mccv.MED_AE);
    if (mse.isSelected()) {
      to.setErrorType(Mccv.MSE);
    }
    else if (mae.isSelected()) {
      to.setErrorType(Mccv.MAE);
    }
    else if (tmae.isSelected()) {
      to.setErrorType(Mccv.TMAE);
    }
    else if (tmse.isSelected()) {
      to.setErrorType(Mccv.TMSE);
    }
    else if (robCorr.isSelected()) {
      to.setErrorType(Mccv.ROB_CORR);
    }
  }

  public void loadFrom(QBench from)
  {
    showGraph.setSelected(from.getMccv().getSaveCalibValidErrs());
    int type = from.getMccv().getErrorType();
    medAE.setSelected(true); // default

    mse.setSelected(type == Mccv.MSE);
    tmse.setSelected(type == Mccv.TMSE);

    mae.setSelected(type == Mccv.MAE);
    tmae.setSelected(type == Mccv.TMAE);
    robCorr.setSelected(type == Mccv.ROB_CORR);
  }
  private void init() {
    log.trace("init()");
    showGraph = new JCheckBox("calib&valid errors");
    showGraph.setToolTipText("show calibration and validation errors");

    medAE = new JRadioButton("MedAE", true);
    medAE.setToolTipText("median absolute error");

    mse = new JRadioButton("MSE", false);
    mse.setToolTipText("mean squared error");

    tmse = new JRadioButton("TMSE", false);
    tmse.setToolTipText("trimmed mean squared error");

    mae = new JRadioButton("MAE", false);
    mae.setToolTipText("mean absolute error");

    tmae = new JRadioButton("TMAE", false);
    tmae.setToolTipText("trimmed mean absolute error");

    robCorr = new JRadioButton("RobCorr", false);
    robCorr.setToolTipText("robust correlation");

    ButtonGroup group = new ButtonGroup();
    group.add(medAE);
    group.add(mse);
    group.add(tmse);
    group.add(mae);
    group.add(tmae);
    group.add(robCorr);
  }
  protected void assemble() {
    endRow(showGraph);
    startRow(medAE);
    startRow(mse);
    endRow(mae);
    startRow(robCorr);
    startRow(tmse);
    endRow(tmae);
  }

}
