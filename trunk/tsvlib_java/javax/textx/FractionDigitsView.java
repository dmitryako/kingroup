package javax.textx;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/09/2005, Time: 11:41:56
 */
public class FractionDigitsView extends JPanel {
  private JComboBox formatCombo;
  public FractionDigitsView(FractionDigitsModel model) {
    init(model);
    loadFrom(model);
    assemble();
  }
  public void loadTo(FractionDigitsModel model) {
    model.setSelectedIdx(formatCombo.getSelectedIndex());
  }
  public void loadFrom(FractionDigitsModel model) {
    formatCombo.setSelectedIndex(model.getSelectedIdx());
  }
  private void init(FractionDigitsModel model) {
    setLayout(new BorderLayout());
    formatCombo = new JComboBox(model.getFormats());
    formatCombo.setToolTipText("Select how to format results.");
  }
  private void assemble() {
    add(formatCombo, BorderLayout.CENTER);
  }
  public void setRunOnChange(UCController uc) {
    formatCombo.addActionListener(new AdapterUCCToALThread(uc));
  }
}
