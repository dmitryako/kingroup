package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 15:34:00
 */
public class KinshipHaploidOptView extends GridBagView {
  private JRadioButton excl;
  private JRadioButton mat;
  private JRadioButton pat;
  private JRadioButton ignore;
  public KinshipHaploidOptView(Kinship model) {
    init();
    loadFrom(model);
    assemble();
  }
  public void loadTo(Kinship model) {
    model.setTreatHaploid(Kinship.HAPLOID_EXCLUDE); // default
    if (pat.isSelected())
      model.setTreatHaploid(Kinship.HAPLOID_PAT);
    if (mat.isSelected())
      model.setTreatHaploid(Kinship.HAPLOID_MAT);
    if (ignore.isSelected())
      model.setTreatHaploid(Kinship.HAPLOID_IGNORE);
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Haploids");
    setBorder(titled);

    excl = new JRadioButton("Exclude");
    excl.setToolTipText("Exclude individual");

    mat = new JRadioButton("Maternal");
    mat.setToolTipText("Assume maternal inheritance");

    pat = new JRadioButton("Paternal");
    pat.setToolTipText("Assume paternal inheritance");

    ignore = new JRadioButton("Ignore");
    ignore.setToolTipText("Ignore haploid locus as input error");

    ButtonGroup bGroup = new ButtonGroup();
    bGroup.add(ignore);
    bGroup.add(excl);
    bGroup.add(mat);
    bGroup.add(pat);
    setToolTipText("Treatment of haploid locus");
  }
  private void loadFrom(Kinship model) {
    excl.setSelected(true); // default
    pat.setSelected(model.getTreatHaploid() == Kinship.HAPLOID_PAT);
    mat.setSelected(model.getTreatHaploid() == Kinship.HAPLOID_MAT);
    ignore.setSelected(model.getTreatHaploid() == Kinship.HAPLOID_IGNORE);
  }
  private void assemble() {
    endRow(ignore);
    endRow(excl);
    endRow(mat);
    endRow(pat);
  }
  public void onTreatHaploidChange(UCController uc) {
    ignore.addActionListener(new AdapterUCCToALThread(uc));
    excl.addActionListener(new AdapterUCCToALThread(uc));
    mat.addActionListener(new AdapterUCCToALThread(uc));
    pat.addActionListener(new AdapterUCCToALThread(uc));
  }
}
