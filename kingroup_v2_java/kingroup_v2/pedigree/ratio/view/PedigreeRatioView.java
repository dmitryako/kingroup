package kingroup_v2.pedigree.ratio.view;
import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 14:26:49
 */
public class PedigreeRatioView extends TableWithApplyUI {
  private final PedigreeRatioOptView optView;
  public PedigreeRatioView(PedigreeRatioOptView optView
    , UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
  }
  public PedigreeRatioOptView getOptView() {
    return optView;
  }
}

