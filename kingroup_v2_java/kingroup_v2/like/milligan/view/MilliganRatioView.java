package kingroup_v2.like.milligan.view;
import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/03/2006, Time: 08:18:31
 */
public class MilliganRatioView  extends TableWithApplyUI {
  private final MilliganRatioOptView optView;
  public MilliganRatioView(MilliganRatioOptView optView
    , UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
  }
  public MilliganRatioOptView getOptView() {
    return optView;
  }
}

