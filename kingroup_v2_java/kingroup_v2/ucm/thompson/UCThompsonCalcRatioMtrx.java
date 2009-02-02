package kingroup_v2.ucm.thompson;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.like.KinshipLogMtrx;
import kingroup_v2.like.thompson.Thompson;
import kingroup_v2.like.thompson.ThompsonIBD;
import kingroup_v2.like.thompson.ThompsonRatioMtrx;
import kingroup_v2.like.thompson.view.ThompsonIBDArrView;
import kingroup_v2.like.thompson.view.ThompsonIBDView;
import kingroup_v2.like.thompson.view.ThompsonRatioOptView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.ucm.kinship.UCKinshipCalcRatioMtrx;
import kingroup_v2.ucm.pedigree.UCPedigreeCalcSigLevels;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 11:12:55
 */
public class UCThompsonCalcRatioMtrx  extends UCKinshipCalcRatioMtrx {
//  private static ProjectLogger log = ProjectLogger.getLogger(UCPedigreeCalcRatioMtrx.class.getName());
  public UCThompsonCalcRatioMtrx(ThompsonRatioOptView optView) {
    super();
    this.optView = optView;
    sigTest = new UCPedigreeCalcSigLevels(optView);
  }
  public KinshipLogMtrx makeRatioMtrx(SysPop sysPop) {
    return new ThompsonRatioMtrx(sysPop);
  }
  public boolean isValid()
  {
    Kingroup kingroup = KinGroupV2Project.getInstance();
    Thompson thompson = kingroup.getThompson();
    ThompsonIBD primIBD = thompson.getPrimIBD();

    ThompsonIBDView primView = new ThompsonIBDView(primIBD);
    ThompsonIBDArrView nullView = new ThompsonIBDArrView(thompson);

    if (nullView.find(primIBD)) {
      String mssg = "The k=("
        + primView.toString()
        + ") primary hypothesis exists as null.";
      JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), mssg);
      return false;
    }
    return true;
  }
}
