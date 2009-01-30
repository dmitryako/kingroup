package javax.swingx;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import java.awt.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2005, Time: 13:13:39
 */
public class ApplyUI extends GridBagView {
  private JButton bttn = new JButton("Apply");
  private JComponent view;
  public ApplyUI(JComponent view, UCController runOnApply) {
    this.view = view;
    bttn.addActionListener(new AdapterUCCToALThread(runOnApply));
    assemble();
  }
  public void setFocusOnApply() {
    bttn.setFocusable(true);
    bttn.setSelected(true);
    bttn.grabFocus();
  }
  public void setButtonText(String txt)
  {
    bttn.setText(txt);
  }
  private void assemble() {
    endRow(view);
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(bttn, BorderLayout.EAST);
    endRow(panel);
//    setMinimumSize();
  }
}


