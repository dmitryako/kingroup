package kingroup_v2.kinship.view;
import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2005, Time: 13:48:08
 */
public class KinshipLikeView extends TableWithApplyUI {
  private final KinshipLikeOptView optView;
  public KinshipLikeView(KinshipLikeOptView optView
    , UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
  }
  public KinshipLikeOptView getOptionsView() {
    return optView;
  }
}
