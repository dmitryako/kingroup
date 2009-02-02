package kingroup_v2.fsr.accuracy;
import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/11/2005, Time: 10:36:01
 */
public class FsrAccuracyView extends TableWithApplyUI
{
  private final FsrAccuracyOptionsView optView;
  public FsrAccuracyView(FsrAccuracyOptionsView optView, UCController runOnApply) {
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
