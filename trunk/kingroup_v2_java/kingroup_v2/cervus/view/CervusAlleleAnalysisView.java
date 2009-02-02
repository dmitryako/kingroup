package kingroup_v2.cervus.view;
import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/03/2006, Time: 12:09:39
 */
public class CervusAlleleAnalysisView  extends TableWithApplyUI {
  private final AlleleAnalysisOptView optView;
  public CervusAlleleAnalysisView(AlleleAnalysisOptView optView
    , UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
    setButtonText("Run");
  }
  public AlleleAnalysisOptView getOptView() {
    return optView;
  }
}
