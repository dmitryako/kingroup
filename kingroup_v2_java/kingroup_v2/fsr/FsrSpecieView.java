package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 09:12:41
 */
public class FsrSpecieView extends GridBagView {
  private JRadioButton dip;
  private JRadioButton hap;
  public FsrSpecieView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  public void loadTo(Kingroup model) {
    model.setDiploidSpecie(dip.isSelected());
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Species");
    setBorder(titled);
    dip = new JRadioButton("Diploid");
    hap = new JRadioButton("Haplodiploid");
    ButtonGroup bGroup = new ButtonGroup();
    bGroup.add(dip);
    bGroup.add(hap);
  }
  private void loadFrom(Kingroup model) {
    dip.setSelected(model.getDiploidSpecie());
    hap.setSelected(!model.getDiploidSpecie());
  }
  private void assemble() {
    endRow(dip);
    endRow(hap);
  }
  public void onSpecieChange(UCController uc) {
    dip.addActionListener(new AdapterUCCToALThread(uc));
    hap.addActionListener(new AdapterUCCToALThread(uc));
  }
}
