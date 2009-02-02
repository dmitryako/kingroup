package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/09/2005, Time: 11:14:16
 */
public class KinshipDisplayWithLogView extends KinshipDisplayOptView {
  private JCheckBox logView;
  public KinshipDisplayWithLogView(Kinship model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void init() {
//    Border border = BorderFactory.createEtchedBorder();
//    Border titled = BorderFactory.createTitledBorder(border, "Display");
//    setBorder(titled);
    logView = new JCheckBox("log10");
    logView.setToolTipText("display the log (base 10) of values");
//    sorted = new JCheckBox("sorted");
  }
  public void loadTo(Kinship model) {
    super.loadTo(model);
    model.setDisplayLogs(logView.isSelected());
  }
  protected void loadFrom(Kinship model) {
    super.loadFrom(model);
    logView.setSelected(model.getDisplayLogs());
  }
  private void assemble() {
    endRow(logView);
    endRow(byGroup);
    endRow(halfMatrix);
    endRow(digitsView);
  }
  public void changeDisplayLogs(UCController uc) {
    logView.addActionListener(new AdapterUCCToALThread(uc));
  }
  public boolean getDisplayLog() {
    return logView.isSelected();
  }
}
