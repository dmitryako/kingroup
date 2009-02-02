package kingroup_v2.fsr;
import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 08:56:51
 */
public class FsrAlgView extends TableWithApplyUI {
  private final FsrAlgOptView optView;
  public FsrAlgView(FsrAlgOptView optView, UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
  }
  public void assemble(JPanel v) {
    setOptView(v);
    assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
  }
//  public FsrAlgOptionsSIMPSView getOptionsView() {
//    return optionsView;
//  }
}
