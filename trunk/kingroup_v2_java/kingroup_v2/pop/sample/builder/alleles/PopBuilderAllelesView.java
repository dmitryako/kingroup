package kingroup_v2.pop.sample.builder.alleles;
import kingroup_v2.pop.allele.freq.KonHegFreqAlg;
import kingroup_v2.pop.sample.PopBuilderModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/11/2005, Time: 17:00:28
 */
public class PopBuilderAllelesView extends JPanel
{
  protected final static int FIELD_SIZE = 3;
  private JRadioButton freqEqui;
  private JRadioButton freqRandom;
  private JRadioButton freqTriangle;
  private JRadioButton freqPop;
  private JRadioButton freqKonHeg;
//  private FloatTextField error;

  public PopBuilderAllelesView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }
  private void init() {
    setLayout(new BorderLayout());
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Alleles");
    setBorder(titled);

    freqEqui = new JRadioButton("equifrequent", true);

    freqRandom = new JRadioButton("random [0;1]", true);
    freqRandom.setToolTipText("Set each frequency to random value from [0, 1) then normalise to 1");

    freqTriangle = new JRadioButton("triangular", false);
    freqTriangle.setToolTipText("Set frequencies to {1,2,3,...,number of alleles} then normalise to 1");

    freqPop = new JRadioButton("sample");
    freqPop.setToolTipText("Calculate from loaded sample");

    freqKonHeg = new JRadioButton("Konovalov&Heg(2008)");
    freqKonHeg.setToolTipText(KonHegFreqAlg.REFERENCE);

    ButtonGroup freqGroup = new ButtonGroup();
    freqGroup.add(freqEqui);
    freqGroup.add(freqRandom);
    freqGroup.add(freqTriangle);
    freqGroup.add(freqPop);
    freqGroup.add(freqKonHeg);

//    error = new FloatTextField(FIELD_SIZE, 0f, 0f, 100f);
//    error.setMinimumSize(error.getPreferredSize());
//    error.setToolTipText("Genotyping error rate per allele");
  }
  private void assemble() {
    add(assembleAlleles(), BorderLayout.NORTH);
  }
  private JPanel assembleAlleles() {
    GridBagView panel = new GridBagView();
    panel.endRow(freqEqui);
    panel.endRow(freqRandom);
    panel.endRow(freqTriangle);
    panel.endRow(freqPop);
    panel.endRow(freqKonHeg);

    // HELP button
//    JButton bttn = ProjectFrame.makeHelpButton();
//    panel.startRow(bttn);
//    panel.endRow(new JLabel(""));

//    JLabel label = new JLabel("error rate [%]");
//    panel.startRow(label);
//    panel.endRow(error);
//    panel.endRow(new JLabel("%"));

    return panel;
  }

  public void loadFrom(PopBuilderModel model) {
    freqEqui.setSelected(true);// default
    freqRandom.setSelected(model.getParentAlleleFreqType() == model.FREQ_RANDOM);
    freqTriangle.setSelected(model.getParentAlleleFreqType() == model.FREQ_TRIANGULAR);
    freqPop.setSelected(model.getParentAlleleFreqType() == PopBuilderModel.FREQ_CALC_SAMPLE_FREQ);
    freqKonHeg.setSelected(model.getParentAlleleFreqType() == PopBuilderModel.FREQ_CALC_KON_HEG_2008);
//    freqPop.setSelected(model.getAlleleFreqSource() == PopBuilderModelOLD.ALLELE_FREQ_CALC);
//    error.setValue(model.getAlleleErrorRate());
  }
  public void loadTo(PopBuilderModel model)
  {
//    model.setAlleleFreqSource(PopBuilderModelOLD.ALLELE_FREQ_GIVEN);
//    if (freqPop.isSelected())
//    model.setAlleleFreqSource(PopBuilderModelOLD.ALLELE_FREQ_CALC);
    model.setParentAlleleFreqType(model.FREQ_EQUAL);
    if (freqRandom.isSelected())
      model.setParentAlleleFreqType(model.FREQ_RANDOM);
    if (freqTriangle.isSelected())
      model.setParentAlleleFreqType(model.FREQ_TRIANGULAR);
    if (freqPop.isSelected())
      model.setParentAlleleFreqType(model.FREQ_CALC_SAMPLE_FREQ);
    if (freqKonHeg.isSelected())
      model.setParentAlleleFreqType(model.FREQ_CALC_KON_HEG_2008);
//    model.setAlleleErrorRate(error.getInput());
  }
}
