package kingroup_v2.kinship.like.view;
import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/10/2005, Time: 10:15:37
 */
public class KinshipRatioView extends TableWithApplyUI {
  private final KinshipRatioOptView optView;
  public KinshipRatioView(KinshipRatioOptView optView
    , UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
  }
  public KinshipRatioOptView getOptView() {
    return optView;
  }
}