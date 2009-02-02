package kingroup_v2.kinship.view;
import kingroup_v2.KingroupFrame;
import kingroup_v2.heterozygosity.GuoThompson1992;
import kingroup_v2.kinship.Kinship;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;
import pattern.ucm.UCShowHelpMssg;
import tsvlib.project.ProjectFrame;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/03/2006, Time: 10:07:12
 */
public class KinshipRDisplayOptView   extends KinshipDisplayOptView {
  private JCheckBox pView;

  public KinshipRDisplayOptView(Kinship model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void init() {
    pView = new JCheckBox("P-values");
    pView.setToolTipText("calculate and display P-values");
  }
  public void loadTo(Kinship model) {
    super.loadTo(model);
    model.setDisplayPValues(pView.isSelected());
  }
  protected void loadFrom(Kinship model) {
    super.loadFrom(model);
//    negView.setSelected(model.getDisplayNegR());
    pView.setSelected(model.getDisplayPValues());
  }
  private void assemble() {
    startRow(pView);
    JButton bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      "Null distribution is created:\n(1) by randomly reshuffling sample alleles (at each locus),\n"
        + "    see " + GuoThompson1992.REFERENCE +";"
      + "\n(2) from 17000 null-hypothesis dyads."
      , KingroupFrame.getInstance())));
    endRow(bttn);

    endRow(byGroup);
    endRow(halfMatrix);
    endRow(digitsView);
  }
  public void onPValueChange(UCController uc) {
    pView.addActionListener(new AdapterUCCToALThread(uc));
  }
}
