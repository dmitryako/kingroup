package kingroup_v2.ucm.kinship;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBDComplex;
import kingroup_v2.kinship.like.KinshipRatioSimTable;
import kingroup_v2.kinship.like.view.KinshipRatioSimTableView;
import kingroup_v2.kinship.view.KinshipRatioOptViewI;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/10/2005, Time: 09:34:54
 */
public class UCKinshipCalcSigLevels implements UCKinshipCalcSigLevelsI
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCKinshipCalcSigLevels.class.getName());
  protected final KinshipRatioOptViewI optView;
  protected KinshipRatioSimTable table;
  public UCKinshipCalcSigLevels(KinshipRatioOptViewI optView) {
    this.optView = optView;
  }
  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    Kinship kinship = project.getKinship();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();
    if (!kinship.getPerformSigTest())
      return true; // this is not an error

    KinshipIBDComplex primIBD = kinship.getComplexPrimIBD();
    KinshipIBDComplex nullIBD = kinship.getComplexNullIBD();
    KinGroupV2MainUI mainGui = KinGroupV2MainUI.getInstance();
    if (primIBD.getComplex() || nullIBD.getComplex()) {
      String error = "This interface has been designed to replicate the original KINSHIP program,\n"
        +"hence significance test cannot be performed if either hypothesis is complex.\n\n"
        + "NOTE! Starting from KINGROUP v2 the test can be performed for complex null hypothesis,\n"
        + "see menu | Pedigree | Likelihood.";
      log.severe(error);
      JOptionPane.showMessageDialog(mainGui, error);
      return false;
    }
    if (nullIBD.equals(primIBD)) {
      String error = "Unable to proceed with the significance test:"
      +"\nNull hypotheses is the same as the primary hypothesis.";
      log.severe(error);
      JOptionPane.showMessageDialog(mainGui, error);
      return false;
    }
    SysPop pop = mainGui.getSysPop();
    table = new KinshipRatioSimTable();
    if (!table.calc(kinship, pop))
      return false;
    KinshipRatioSimTableView view = new KinshipRatioSimTableView(kinship, table);
    optView.setRatioSimTableView(view);
    return true;
  }
  public KinshipRatioSimTable getRatioSimTable() {
    return table;
  }
}
