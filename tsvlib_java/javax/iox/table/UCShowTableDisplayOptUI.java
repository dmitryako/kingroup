package javax.iox.table;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/11/2007, Time: 15:40:16
 */
public class UCShowTableDisplayOptUI   extends ApplyDialogUI
{
  public UCShowTableDisplayOptUI(JComponent view, JFrame frame, String title) {
    super(view, frame, true);
    setTitle(title);
    center();
    setApplyBttnText("Apply");
    setFocusOnApply();
    setResizable(true);
  }
}

