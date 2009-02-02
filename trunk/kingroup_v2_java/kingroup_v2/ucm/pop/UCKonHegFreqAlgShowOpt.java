package kingroup_v2.ucm.pop;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.pop.allele.freq.KonHegFreqAlgOptView;
import kingroup_v2.pop.allele.freq.KonHegFreqView;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import pattern.ucm.UCController;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: ESHFreeUser
 * Date: 7/06/2006
 * Time: 12:57:16
 * To change this template use File | Settings | File Templates.
 */
public class UCKonHegFreqAlgShowOpt
  implements UCController {
  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW pop = ui.getUsrPop();
    if (pop == null   ||  pop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }

    Kinship kinship = project.getKinship();
    kinship.setHasGroupId(pop.getHasGroupId());
    KonHegFreqAlgOptView optView = new KonHegFreqAlgOptView(project);

    // THIS IS TRICKY:
    // UCKinshipCalcLikeMtrx is run on APPLY
    // when it finishes, it must update table view
    UCKonHegFreqAlgCalcFreq calc = new UCKonHegFreqAlgCalcFreq(optView);
//    UCPopFreqCalcFreq_dev calc = new UCPopFreqCalcFreq_dev(optView);
    KonHegFreqView view = new KonHegFreqView(optView, calc);
    ui.setKonHegFreqView(view);
    calc.setViewForUpdate(view);

    view.setTableView(new JTable());
    view.assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
    view.setFocusOnApply();

    return true;
  }
}