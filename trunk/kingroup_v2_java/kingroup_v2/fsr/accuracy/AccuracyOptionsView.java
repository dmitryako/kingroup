package kingroup_v2.fsr.accuracy;
import kingroup_v2.Kingroup;
import kingroup_v2.fsr.DisplayOptView;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.FloatTextField;
import javax.swingx.text_fieldx.IntTextField;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/11/2005, Time: 11:17:27
 */
public class AccuracyOptionsView extends GridBagView
{
  protected IntTextField nTrials;
  protected FloatTextField accError;
  protected FloatTextField stdDev;
  protected DisplayOptView displayView;
  private static final int FIELD_SIZE = 4;
  private static final int MIN_N_TRIALS = 1;
  private static final int MAX_N_TRIALS = 10000;
  private static final int DEFAULT_N_TRIALS = 10;
  private static final int FLOAT_SIZE = 6;

  public AccuracyOptionsView(Kingroup project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  private void init()
  {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Accuracy");
    setBorder(titled);

    getStartRow().anchor = GridBagConstraints.NORTHWEST;
    getEndRow().anchor = GridBagConstraints.NORTHWEST;
    nTrials = new IntTextField(FIELD_SIZE
      , DEFAULT_N_TRIALS, MIN_N_TRIALS, MAX_N_TRIALS);
//    nTrials.setToolTipText("number of Monte Carlo simulation trials [0; 10 000]");

    accError = new FloatTextField(FLOAT_SIZE, 0, 100);
    accError.setEditable(false);
    accError.setToolTipText("percentage of incorrectly assigned individuals");

    stdDev = new FloatTextField(FLOAT_SIZE, 0, 100);
    stdDev.setEditable(false);
    stdDev.setToolTipText("standard deviation");
  }
  public void loadTo(Kingroup model) {
    model.setNumAccuracyTrials(nTrials.getInput());
  }
  protected void loadFrom(Kingroup project) {
    nTrials.setValue(project.getNumAccuracyTrials());
  }
  protected void assemble() {
    JLabel label = new JLabel("trials");
    label.setToolTipText("number of Monte Carlo simulations");
    startRow(label);
    endRow(nTrials);

    label = new JLabel("error");
    label.setToolTipText("accuracy-error [%]");
    startRow(label);
    endRow(accError);

    label = new JLabel("std dev");
    label.setToolTipText("standard deviation [%]");
    startRow(label);
    endRow(stdDev);
  }
  public void onOptionChange(UCController uc) {
//    specieView.onSpecieChange(uc);
//    displayView.onDisplayViewChange(uc);
//    displayView.onSortByGroupChange(uc);
  }
//  public Dimension getMinimumSize() {
//    int EXTRA_SIZE = 20;
//    Dimension d = getPreferredSize();
//    return new Dimension(d.width + EXTRA_SIZE, d.height + EXTRA_SIZE);
//  }

  public void setAccurError(double accError)
  {
    this.accError.setValue((float)accError);
    repaint();
  }

  public void setAccurErrorStdDev(double stdDev)
  {
    this.stdDev.setValue((float)stdDev);
    repaint();
  }
}
