package javax.swingx;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplyDialogUI extends JDialog {
  private boolean apply = false;
  private JButton applyBttn = new JButton("Apply");
  private JFrame parentFrame;
  private JComponent view;
//  public static void main(String[] args) {
//    JFrame frame = new JFrame();
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    JPanel panel = new JPanel();
//    panel.add(new JLabel("TEST LABEL"));
//    ApplyDialogUI dlg = new ApplyDialogUI(panel, frame, true);
//    dlg.setVisible(true);
//  }

  public ApplyDialogUI(JComponent view, JFrame frame, boolean mod) {
    super(frame, mod);
    init();
    parentFrame = frame;
    this.view = view;
    assemble();
  }
  private void init()
  {
    setLayout(new BorderLayout());
  }
  public void setApplyBttnText(String txt) {
    applyBttn.setText(txt);
  }
  public boolean apply() {
    return apply;
  }
  public void setFocusOnApply() {
    applyBttn.setFocusable(true);
    applyBttn.setSelected(true);
    applyBttn.grabFocus();
  }
  public void center() {
    if (parentFrame == null)
      return;
    Point loc = parentFrame.getLocation();
    Rectangle recD = getBounds();
    Rectangle recF = parentFrame.getBounds();
    if (recD.width > recF.width
      || recD.height > recF.height) {
      setBounds(recF);
      return;
    }
    double x = loc.getX() + (recF.getWidth() - recD.getWidth()) / 2;
    double y = loc.getY() + (recF.getHeight() - recD.getHeight()) / 2;
    setLocation(new Point((int) x, (int) y));
  }
  private void assemble() {
    if (parentFrame != null)
      parentFrame.getRootPane().setDefaultButton(applyBttn);
    JPanel okPanel = new JPanel();
    JButton cancel = new JButton("Cancel");
//    getContentPane().setLayout(new BorderLayout());
    okPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    okPanel.add(applyBttn);
    okPanel.add(cancel);
//    getContentPane().add(view);
//    getContentPane().add(okPanel, BorderLayout.SOUTH);
    add(view, BorderLayout.CENTER);
    add(okPanel, BorderLayout.SOUTH);
    pack();
    setResizable(false);                            //Make it so the frame size is not changeable
    applyBttn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        apply = true;
        dispose();
      }
    });
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        apply = false;
        dispose();
      }
    });
  }

  public void removeActionListeners()
  {
    ActionListener a[] = applyBttn.getActionListeners();
    for (int i = 0; i < a.length; i++) {
      ActionListener actionListener = a[i];
      applyBttn.removeActionListener(actionListener);
    }
  }
  public void runOnApply(UCController uc)
  {
    applyBttn.addActionListener(new AdapterUCCToALThread(uc));
  }
}

