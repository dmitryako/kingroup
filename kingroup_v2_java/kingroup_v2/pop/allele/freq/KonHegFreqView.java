package kingroup_v2.pop.allele.freq;

import kingroup_v2.KingroupFrame;
import pattern.ucm.UCController;

import javax.swingx.tablex.TableWithApplyUI;

/**
 * Created by IntelliJ IDEA.
 * User: ESHFreeUser
 * Date: 7/06/2006
 * Time: 13:24:43
 * To change this template use File | Settings | File Templates.
 */
public class KonHegFreqView extends TableWithApplyUI {
  private final KonHegFreqAlgOptView optView;
  public KonHegFreqView(KonHegFreqAlgOptView optView, UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(KingroupFrame.getInstance());
    this.optView = optView;
  }
  public KonHegFreqAlgOptView getOptView() {
    return optView;
  }
}