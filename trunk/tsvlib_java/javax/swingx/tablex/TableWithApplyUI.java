package javax.swingx.tablex;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.ApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/03/2006, Time: 07:04:33
 */
public class TableWithApplyUI  extends TableViewWithOpt {
  private final ApplyUI withApply;
  public TableWithApplyUI(JComponent optView, UCController runOnApply) {
    withApply = new ApplyUI(optView, runOnApply);
    assemble(withApply);
  }
  public void assemble(JPanel v) {
    setOptView(v);
    assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
  }
  public void setFocusOnApply() {
    withApply.setFocusOnApply();
  }
  public void setButtonText(String txt) {
    withApply.setButtonText(txt);
  }
}

