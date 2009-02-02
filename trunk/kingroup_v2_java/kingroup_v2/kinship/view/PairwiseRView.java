package kingroup_v2.kinship.view;

import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/01/2006, Time: 16:41:44
 */
public class PairwiseRView extends TableWithApplyUI {
  private final PairwiseROptView optView;

  public PairwiseRView(PairwiseROptView optView
    , UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
    this.setButtonText("Calculate");
  }
  public PairwiseROptView getOptView() {
    return optView;
  }

  public String getReference()
  {
    if (optView != null)
      return optView.getReference();
    return "";
  }
}
