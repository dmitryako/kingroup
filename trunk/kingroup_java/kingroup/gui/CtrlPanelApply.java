package kingroup.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 4, 2004, Time: 1:22:12 PM
 */

// Generic Controller for a MVC pattern
// Could be overwritten to perform different Apply action
public class CtrlPanelApply extends JPanel implements ActionListener {
  private JComponent view_;
  JButton apply_ = new JButton("Apply");
  public CtrlPanelApply(JComponent view) {
    view_ = view;
    assemble();
  }
  public void actionPerformed(ActionEvent event) {
  }
  private void assemble() {
    JPanel okPanel = new JPanel();
    setLayout(new BorderLayout());
    okPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    okPanel.add(apply_);
    add(view_);
    add(okPanel, BorderLayout.SOUTH);
    apply_.addActionListener(this);
  }
}
