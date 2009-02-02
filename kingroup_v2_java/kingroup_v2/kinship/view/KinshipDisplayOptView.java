package kingroup_v2.kinship.view;

import kingroup_v2.kinship.Kinship;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.textx.FractionDigitsView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/01/2006, Time: 17:07:19
 */
public class KinshipDisplayOptView extends GridBagView {
  protected FractionDigitsView digitsView;
  protected JCheckBox halfMatrix;
  protected JCheckBox byGroup;
  public KinshipDisplayOptView() {
    init();
  }
  public KinshipDisplayOptView(Kinship model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Display");
    setBorder(titled);
    halfMatrix = new JCheckBox("half matrix");
    byGroup = new JCheckBox("by group");
    byGroup.setToolTipText("display results by group");
  }
  public void loadTo(Kinship model) {
    digitsView.loadTo(model.getDigitsModel());
    model.setDisplayHalfMatrix(halfMatrix.isSelected());
    model.setDisplayByGroup(byGroup.isSelected());
  }
  protected void loadFrom(Kinship model) {
    digitsView = new FractionDigitsView(model.getDigitsModel());
    halfMatrix.setSelected(model.getDisplayHalfMatrix());
    byGroup.setSelected(model.getDisplayByGroup());
    if (!model.getHasGroupId()) {
      byGroup.setSelected(false);
      byGroup.setEnabled(false);
      byGroup.setToolTipText("sample has zero or one group");
    }
  }
  private void assemble() {
    endRow(byGroup);
    endRow(halfMatrix);
    endRow(digitsView);
  }
  public void onByGroupChange(UCController uc) {
    byGroup.addActionListener(new AdapterUCCToALThread(uc));
  }
}
