package tsvlib.project;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 26, 2004, Time: 11:45:59 AM
 */
import pattern.ucm.UCController;

import javax.iox.LOG;
import javax.swing.*;
public class UCSelectLookFeel implements UCController {
  private final JFrame frame;
  private final Project model;
  private final String plafName;
  /**
   * Makes a button to change the pluggable look and feel.
   *
   * @param plafName the name of the look and feel class
   */
  public UCSelectLookFeel(JFrame frame, Project model, String plafName) {
    this.frame = frame;
    this.model = model;
    this.plafName = plafName;
  }
  /**
   * button action: switch to the new look and feel
   */
  public boolean run() {
    if (frame == null)
      return true;
    try {
      model.setLookFeel(plafName);
      model.saveProjectToDefaultLocation();
      UIManager.setLookAndFeel(plafName);
//      SwingUtilities.updateComponentTreeUI(java.awt.Component);
      SwingUtilities.updateComponentTreeUI(frame);
//      frame.validate();
    }
    catch (Exception e) {
      LOG.trace(this, "actionPerformed: UIManager.setLookAndFeel", e);
    }
    return true;
  }
}