package javax.swingx.panelx;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.ApplyUI;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/05/2007, 16:19:46
 */
public class ViewWithApplyUI  extends ViewWithOpt {
  private ApplyUI withApply;
  public ViewWithApplyUI(JComponent optView, UCController runOnApply) {
    withApply = new ApplyUI(optView, runOnApply);
    assembleWithOpt(withApply);
  }

  public ViewWithApplyUI() {
  }

  public void setOptView(JComponent optView, UCController runOnApply) {
    withApply = new ApplyUI(optView, runOnApply);
    assembleWithOpt(withApply);
  }

  public void assembleWithOpt(JPanel v) {
    super.setOptView(v);
    assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
  }
  public void setFocusOnApply() {
    withApply.setFocusOnApply();
  }
  public void setButtonText(String txt) {
    withApply.setButtonText(txt);
  }
}

