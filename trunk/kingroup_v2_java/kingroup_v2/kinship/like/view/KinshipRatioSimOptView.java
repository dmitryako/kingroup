package kingroup_v2.kinship.like.view;
import kingroup_v2.kinship.Kinship;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/10/2005, Time: 14:25:50
 */
public class KinshipRatioSimOptView extends GridBagView {
  private final static int FIELD_SIZE = 5;
  private final static int MIN_SIM_PAIRS = 1;
  private final static int DEFAULT_SIM_PAIRS = 1000;
  private final static int MAX_SIM_PAIRS = 100000;
  private JRadioButton flag;
  private JRadioButton pvalue;
  private JCheckBox sim;
  private IntField nPairs;
  public KinshipRatioSimOptView(Kinship model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void loadFrom(Kinship model) {
    sim.setSelected(model.getPerformSigTest());
    nPairs.setValue(model.getNumSimPairs());
    flag.setSelected(model.getDisplaySigFlag());
    pvalue.setSelected(!model.getDisplaySigFlag());
  }
  public void loadTo(Kinship model) {
    model.setPerformSigTest(sim.isSelected());
    model.setNumSimPairs(nPairs.getInput());
    model.setDisplaySigFlag(flag.isSelected());
    model.setDisplaySigFlag(!pvalue.isSelected());
  }
  public void setPValueEnabled(boolean b) {pvalue.setEnabled(b);}
  private void init() {
    sim = new JCheckBox("test");
    sim.setToolTipText("perform significance test");
    flag = new JRadioButton("flag");
    flag.setToolTipText("display significance flag [*, **, ***]");
    pvalue = new JRadioButton("p");
    pvalue.setToolTipText("display p-value [%]");
    nPairs = new IntField(FIELD_SIZE
      , DEFAULT_SIM_PAIRS, MIN_SIM_PAIRS, MAX_SIM_PAIRS);
    nPairs.setToolTipText("number of simulated pairs [0; 100 000]");

    ButtonGroup group = new ButtonGroup();
    group.add(flag);
    group.add(pvalue);
  }
  private void assemble() {
    startRow(sim);
    startRow(flag);
    startRow(pvalue);
    endRow(nPairs);
  }
  public void onSigFlagChange(UCController uc) {
    flag.addActionListener(new AdapterUCCToALThread(uc));
  }
  public void onPValueChange(UCController uc) {
    pvalue.addActionListener(new AdapterUCCToALThread(uc));
  }
  public int getNumPairs() {
    return nPairs.getInput();
  }
  public boolean getDisplayPValue() {
    return pvalue.isSelected();
  }
}
