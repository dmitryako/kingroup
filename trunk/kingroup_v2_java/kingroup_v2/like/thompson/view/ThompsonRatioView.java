package kingroup_v2.like.thompson.view;
import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 11:21:11
 */
public class ThompsonRatioView  extends TableWithApplyUI {
  private final ThompsonRatioOptView optView;
  public ThompsonRatioView(ThompsonRatioOptView optView
    , UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
  }
  public ThompsonRatioOptView getOptView() {
    return optView;
  }
}

