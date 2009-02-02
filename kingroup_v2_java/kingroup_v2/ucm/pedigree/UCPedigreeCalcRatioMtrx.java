package kingroup_v2.ucm.pedigree;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.like.KinshipRatioMtrx;
import kingroup_v2.kinship.view.KinshipIBDArrView;
import kingroup_v2.kinship.view.KinshipIBDView;
import kingroup_v2.pedigree.ratio.PedigreeRatioMtrx;
import kingroup_v2.pedigree.ratio.view.PedigreeRatioOptView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.ucm.kinship.UCKinshipCalcRatioMtrx;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 14:21:51
 */
public class UCPedigreeCalcRatioMtrx  extends UCKinshipCalcRatioMtrx {
  private static ProjectLogger log = ProjectLogger.getLogger(UCPedigreeCalcRatioMtrx.class.getName());
  public UCPedigreeCalcRatioMtrx(PedigreeRatioOptView optView) {
    super();
    this.optView = optView;
    sigTest = new UCPedigreeCalcSigLevels(optView);
  }
  public KinshipRatioMtrx makeRatioMtrx(SysPop sysPop) {
    PedigreeRatioMtrx mtrx = new PedigreeRatioMtrx(sysPop);
    return mtrx;
  }
  public boolean isValid()
  {
    Kingroup project = KinGroupV2Project.getInstance();
    Kinship kinship = project.getKinship();

    KinshipIBD primIBD = kinship.getPrimIBD();
    KinshipIBDView primView = new KinshipIBDView(primIBD);
    KinshipIBDArrView nullView = new KinshipIBDArrView(kinship);

    if (nullView.find(primIBD)) {
      String mssg = "The ("
        + primView.toString()
        + ") primary hypothesis exists as null.";
      JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), mssg);
      return false;
    }
    return true;
  }
}

