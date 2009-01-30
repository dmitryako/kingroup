package tsvlib.project;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 20, 2004, Time: 2:42:46 PM
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class UCDShowHelpAbout implements ActionListener {
  private static String mssg_ = null;
  private JFrame frame = null;
  public void setHelpMessage(String mssg) {
    mssg_ = mssg;
  }
  public UCDShowHelpAbout(JFrame frame) {
    this.frame = frame;
  }
  public void actionPerformed(ActionEvent event) {
    JDialog dlg = new JDialog(frame, "About", true);
    JPanel panel = new JPanel();
    panel.add(new JLabel(mssg_));
//    dlg.getContentPane().add(panel);
    dlg.setLayout(new BorderLayout());
    dlg.add(panel, BorderLayout.CENTER);
    dlg.pack();
    dlg.setResizable(false);
    Point loc = frame.getLocation();
    Rectangle recD = dlg.getBounds();
    Rectangle recF = frame.getBounds();
    double x = loc.getX() + (recF.getWidth() - recD.getWidth()) / 2;
    double y = loc.getY() + (recF.getHeight() - recD.getHeight()) / 2;
    dlg.setLocation(new Point((int) x, (int) y));
    dlg.setVisible(true);
  }
}
