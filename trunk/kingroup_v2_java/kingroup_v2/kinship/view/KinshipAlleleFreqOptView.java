package kingroup_v2.kinship.view;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipAlleleFreqOpt;
import kingroup_v2.relatedness.qg.QGRelatedness;
import pattern.ucm.AdapterUCCToALThread;
import pattern.ucm.UCShowHelpMssg;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/03/2006, Time: 10:00:36
 */
public class KinshipAlleleFreqOptView extends GridBagView
{
  private JRadioButton freqCurr;
  private JRadioButton calcFreq;
  private JCheckBox calcBias;
  private JRadioButton exclPair;
  private JRadioButton exclGroup;
  private boolean exclGroupOn;
  private JCheckBox zeroFreq;

  public KinshipAlleleFreqOptView(Kinship model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void assemble() {
    endRow(freqCurr);
    endRow(calcFreq);
    endRow(calcBias);

    GridBagView gridbag = new GridBagView();
    gridbag.setBorder(BorderFactory.createLoweredBevelBorder());
    gridbag.startRow(exclGroup);
    gridbag.endRow(exclPair);
    gridbag.startRow(zeroFreq);
    JButton bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg("Some allele(s)"
      + " may only be present in the current pair or group(s).\n\n"
      + "If 'zero' is selected,\n"
      + "the bias correction sets the frequencies of such alleles to ZERO\n"
      + "(as in KINSHIP program).\n"
      + "If 'zero' is OFF, the frequencies of such alleles are left unchanged."
      , KingroupFrame.getInstance())));
    gridbag.endRow(bttn);

    endRow(gridbag);
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Allele frequencies");
    setBorder(titled);

    // CALC/CURRENT
    freqCurr = new JRadioButton("Current", false);
    freqCurr.setToolTipText("Use currently loaded");

    calcFreq = new JRadioButton("Calculate", false);
    calcFreq.setToolTipText("Calculate from loaded population sample.");

    ButtonGroup group = new ButtonGroup();
    group.add(freqCurr);
    group.add(calcFreq);
    freqCurr.setSelected(true); // default

    // EXCLUDE PAIR/GROUP
    calcBias = new JCheckBox("bias correction");
    calcBias.setToolTipText("as per " + QGRelatedness.REFERENCE);
    exclPair = new JRadioButton("pair", false);
    exclPair.setToolTipText("Exclude current pair from background frequency calculations");

    exclGroup = new JRadioButton("group", false);
    exclGroup.setToolTipText("Exclude current group(s) from background frequency calculations.");

    group = new ButtonGroup();
    group.add(exclPair);
    group.add(exclGroup);
    exclPair.setSelected(true); // default

    zeroFreq = new JCheckBox("zero");
    zeroFreq.setToolTipText("Allow zero frequencies");

    freqCurr.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)      {
        calcBias.setEnabled(!freqCurr.isSelected());
        exclPair.setEnabled(!freqCurr.isSelected());
        zeroFreq.setEnabled(!freqCurr.isSelected());
        if (exclGroupOn)
          exclGroup.setEnabled(!freqCurr.isSelected());
      }});
    calcFreq.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)      {
        calcBias.setEnabled(calcFreq.isSelected());
        exclPair.setEnabled(calcBias.isSelected());
        zeroFreq.setEnabled(calcBias.isSelected());
        if (exclGroupOn)
          exclGroup.setEnabled(calcBias.isSelected());
      }});

    calcBias.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)      {
        exclPair.setEnabled(calcBias.isSelected());
        zeroFreq.setEnabled(calcBias.isSelected());
        if (exclGroupOn)
          exclGroup.setEnabled(calcBias.isSelected());
      }});
  }
  private void loadFrom(Kinship model)
  {
    KinshipAlleleFreqOpt opt = model.getAlleleFreqOpt();
    freqCurr.setSelected(!opt.getCalcFreq());
    calcFreq.setSelected(opt.getCalcFreq());
    calcBias.setSelected(opt.getExclBias());
    exclGroup.setSelected(opt.getExclGroup());
    exclPair.setSelected(!opt.getExclGroup());
    zeroFreq.setSelected(opt.getAllowZeroFreq());

    exclGroupOn = true;
    if (!model.getHasGroupId()) {
      exclPair.setSelected(true);
      exclGroupOn = false;
    }
    enableOptions();
  }

  private void enableOptions()
  {
    calcBias.setEnabled(calcFreq.isSelected());
    exclPair.setEnabled(calcBias.isSelected());
    zeroFreq.setEnabled(calcBias.isSelected());
    if (exclGroupOn)
      exclGroup.setEnabled(calcBias.isSelected());
    else
      exclGroup.setEnabled(false);
  }

  public void loadTo(Kinship model) {
    KinshipAlleleFreqOpt opt = model.getAlleleFreqOpt();
    opt.setCalcFreq(calcFreq.isSelected());
    opt.setExclBias(calcBias.isSelected());
    opt.setExclGroup(exclGroup.isSelected());
    opt.setAllowZeroFreq(zeroFreq.isSelected());
  }
}
