package kingroup_v2.fsr.bound;

import kingroup_v2.fsr.FsrLowerBoundBean;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.FloatTextField;
import javax.swingx.text_fieldx.IntField;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/11/2005, Time: 08:32:16
 */
public class FsrLowerBoundView extends GridBagView {
  private IntField nGroups;
  private JLabel nGroupsLbl;
  private IntField groupSize;
  private JLabel groupSizeLbl;
  protected final static int FIELD_SIZE = 2;
  private IntField nLoci;
  private JLabel nLociLbl;
  private IntField nAlleles;
  private JLabel nAllelesLbl;
  private IntField nTrials;

  protected final static int FLOAT_SIZE = 5;
  private FloatTextField gsOne; // approximation for group size = 1
  private FloatTextField gsLarge; // approximation for group size >> 1
  private FloatTextField mc; // Monte Carlo

  private FloatTextField q1; // phenotype probability
  private FloatTextField q2;
  private FloatTextField q3;
  private FloatTextField q4;

  private FloatTextField mcQ1; // phenotype probability
  private FloatTextField mcQ2;
  private FloatTextField mcQ3;
  private FloatTextField mcQ4;

  private JRadioButton freqEqui;
  public FsrLowerBoundView(FsrLowerBoundBean bean) {
    init();
    loadFrom(bean);
    assemble();
  }
  private void init()
  {
    // POPULATION SAMPLE
    nGroups = new IntField(FIELD_SIZE, 1, 50);
    nGroupsLbl = new JLabel("groups");
    groupSize = new IntField(FIELD_SIZE, 1, 50);
    groupSizeLbl = new JLabel("group size");
    groupSizeLbl.setToolTipText("individuals in each group");

    // GENOTYPE
    nLoci = new IntField(FIELD_SIZE, 1, 1, 3);
    nLociLbl = new JLabel("loci");
    nAlleles = new IntField(FIELD_SIZE, 10, 1, 50);
    nAllelesLbl = new JLabel("alleles");
    nAllelesLbl.setToolTipText("at each locus");

    // ALLELIC FREQUENCIES
    freqEqui = new JRadioButton("equifrequent", true);
    freqEqui.setToolTipText("equifrequent alleles");
    ButtonGroup freqGroup = new ButtonGroup();
    freqGroup.add(freqEqui);

    // MONTE CARLO
    nTrials = new IntField(FLOAT_SIZE, 1000, 1, 1000000);

    // ACCURACY RESULTS
    gsOne = new FloatTextField(FLOAT_SIZE, 0, 100);
    gsOne.setEditable(false);
    gsOne.setToolTipText("xi_2, approximation for group size = 1");

    gsLarge = new FloatTextField(FLOAT_SIZE, 0, 100);
    gsLarge.setEditable(false);
    gsLarge.setToolTipText("xi_4, (if L=1, approx. for group size >> 1)");

    mc = new FloatTextField(FLOAT_SIZE, 0, 100);
    mc.setEditable(false);
    mc.setToolTipText("Percentage of incorrectly assigned individuals");

    q1 = new FloatTextField(FLOAT_SIZE, 0, 100);
    q2 = new FloatTextField(FLOAT_SIZE, 0, 100);
    q3 = new FloatTextField(FLOAT_SIZE, 0, 100);
    q4 = new FloatTextField(FLOAT_SIZE, 0, 100);
    q1.setEditable(false);
    q2.setEditable(false);
    q3.setEditable(false);
    q4.setEditable(false);
    q1.setToolTipText("combinatorial [%]");
    q2.setToolTipText("combinatorial [%]");
    q3.setToolTipText("combinatorial [%]");
    q4.setToolTipText("combinatorial [%]");

    mcQ1 = new FloatTextField(FLOAT_SIZE, 0, 100);
    mcQ2 = new FloatTextField(FLOAT_SIZE, 0, 100);
    mcQ3 = new FloatTextField(FLOAT_SIZE, 0, 100);
    mcQ4 = new FloatTextField(FLOAT_SIZE, 0, 100);
    mcQ1.setEditable(false);
    mcQ2.setEditable(false);
    mcQ3.setEditable(false);
    mcQ4.setEditable(false);
    mcQ1.setToolTipText("Monte Carlo [%]");
    mcQ2.setToolTipText("Monte Carlo [%]");
    mcQ3.setToolTipText("Monte Carlo [%]");
    mcQ4.setToolTipText("Monte Carlo [%]");
  }
  private void assemble() {
    startRow(assemblePopSample());
    startRow(assembleGenotype());
    endRow(assembleFreq());
    endRow(assembleMonteCarlo());
  }
  private JPanel assembleGenotype() {
    GridBagView panel = new GridBagView("Genotype");
    panel.startRow(nLoci);
    panel.endRow(nLociLbl);
    panel.startRow(nAlleles);
    panel.endRow(nAllelesLbl);
    return panel;
  }
  private JPanel assemblePopSample() {
    GridBagView panel = new GridBagView("Population");
    panel.startRow(nGroups);
    panel.endRow(nGroupsLbl);
    panel.startRow(groupSize);
    panel.endRow(groupSizeLbl);
    return panel;
  }
  private JPanel assembleFreq() {
    GridBagView panel = new GridBagView("Frequencies");
    panel.endRow(freqEqui);
    return panel;
  }
  private JPanel assembleMonteCarlo() {


    GridBagView panel = new GridBagView("Lower bound");

    JLabel label = new JLabel("trials");
    label.setToolTipText("number of Monte Carlo simulations");
    panel.startRow(label);
    panel.startRow(nTrials);
    panel.startRow(new JLabel(""));
//    panel.endRow(new JLabel("g >> 1"));
    panel.endRow(new JLabel("g = 1"));

    label = new JLabel("error");
    label.setToolTipText("accuracy-error [%]");
    panel.startRow(label);
    panel.startRow(mc);
    panel.startRow(gsLarge);
    panel.endRow(gsOne);

    label = new JLabel("q1");
    label.setToolTipText("probability of 1 offspring phenotype");
    panel.startRow(label);
    panel.startRow(mcQ1);
    panel.startRow(q1);
    panel.endRow(new JLabel(""));

    label = new JLabel("q2");
    label.setToolTipText("prob. of 2 phenotypes");
    panel.startRow(label);
    panel.startRow(mcQ2);
    panel.startRow(q2);
    panel.endRow(new JLabel(""));

    label = new JLabel("q3");
    label.setToolTipText("prob. of 3 phenotypes");
    panel.startRow(label);
    panel.startRow(mcQ3);
    panel.startRow(q3);
    panel.endRow(new JLabel(""));

    label = new JLabel("q4");
    label.setToolTipText("prob. of 4 phenotypes");
    panel.startRow(label);
    panel.startRow(mcQ4);
    panel.startRow(q4);
    panel.endRow(new JLabel(""));
    return panel;
  }
  public void setResultGroupSizeOne(float d) {
    gsOne.setValue(d);
  }
  public void setResultGroupSizeLarge(float d) {
    gsLarge.setValue(d);
  }
  public void setResultMonteCarlo(float d) {
    mc.setValue(d);
  }
  public void loadFrom(FsrLowerBoundBean bean) {
    nGroups.setValue(bean.getNumGroups());
    groupSize.setValue(bean.getGroupSize());
    nLoci.setValue(bean.getNumLoci());
    nAlleles.setValue(bean.getNumAlleles());
    freqEqui.setSelected(true);
    nTrials.setValue(bean.getNumTrials());
  }
  public void loadTo(FsrLowerBoundBean bean) {
    bean.setNumGroups(nGroups.getInput());
    bean.setGroupSize(groupSize.getInput());
    bean.setNumLoci(nLoci.getInput());
    bean.setNumAlleles(nAlleles.getInput());
    bean.setNumTrials(nTrials.getInput());
  }

  public void setPhenotypeProbMC(double[] qm)
  {
    mcQ1.setValue((float)(100. * qm[0]));
    mcQ2.setValue((float)(100. * qm[1]));
    mcQ3.setValue((float)(100. * qm[2]));
    mcQ4.setValue((float)(100. * qm[3]));
  }

  public void setPhenotypeProb(double[] qm)
  {
    if (qm == null) {
      q1.setValue(0);
      q2.setValue(0);
      q3.setValue(0);
      q4.setValue(0);
      return;
    }
    q1.setValue((float)(100. * qm[0]));
    q2.setValue((float)(100. * qm[1]));
    q3.setValue((float)(100. * qm[2]));
    q4.setValue((float)(100. * qm[3]));
  }
}

