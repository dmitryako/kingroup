package javax.swingx;
import javax.swing.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/09/2005, Time: 13:14:51
 */
public class UCShowImportFileUI extends ApplyDialogUI {
  public UCShowImportFileUI(JComponent view, JFrame frame, String title) {
    super(view, frame, true);
    setTitle(title);
    center();
    setApplyBttnText("Apply");
    setFocusOnApply();
    setResizable(true);
  }
}
