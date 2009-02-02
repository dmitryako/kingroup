package kingroup_v2.ucm.pedigree;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDComplex;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.kinship.like.KinshipRatioSimArr;
import kingroup_v2.kinship.like.KinshipRatioSimTable;
import kingroup_v2.kinship.like.view.KinshipRatioSimTableView;
import kingroup_v2.kinship.view.KinshipRatioOptViewI;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.ucm.kinship.UCKinshipCalcSigLevels;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2006, Time: 11:39:50
 */
public class UCPedigreeCalcSigLevels extends UCKinshipCalcSigLevels
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCPedigreeCalcSigLevels.class.getName());
  public UCPedigreeCalcSigLevels(KinshipRatioOptViewI optView) {
    super(optView);
  }
  public boolean run() {
    KinGroupV2MainUI mainGui = KinGroupV2MainUI.getInstance();
    Kingroup project = KinGroupV2Project.getInstance();
    Kinship kinship = project.getKinship();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();
    if (!kinship.getPerformSigTest())
      return true; // this is not an error

    KinshipIBDComplex primIBD = kinship.getComplexPrimIBD();
    KinshipIBD[] nullArr = kinship.getNullArr();
    if (primIBD.getComplex()) {
      String error = "Program error: Primary hypothesis can not be complex!!!\n"
        + "Please report this bug.";
      log.severe(error);
      JOptionPane.showMessageDialog(mainGui, error);
      return false;
    }
    if (nullArr.length > 1  && !kinship.getDisplaySigFlag()) {
      String error = "Significance levels with complex null hypothesis is working"
        +"\nBUT p-values are needed to be checked.\n"
        +"Please contact dmitry.konovalov@jcu.edu.au if you need this functionality.";
      log.severe(error);
      JOptionPane.showMessageDialog(mainGui, error);
      return false;
    }
    if (nullArr.length == 0) {
      String error = "Unable to proceed with the significance test:"
        +"\nMissing null hypothesis.";
      log.severe(error);
      JOptionPane.showMessageDialog(mainGui, error);
      return false;
    }
    if (KinshipIBDFactory.find(primIBD, nullArr)) {
      String error = "Unable to proceed with the significance test:"
        +"\nOne of null hypotheses is the same as the primary hypothesis.";
      log.severe(error);
      JOptionPane.showMessageDialog(mainGui, error);
      return false;
    }
    SysPop pop = mainGui.getSysPop();
    kinship.getComplexPrimIBD().setComplex(false);
    KinshipIBDComplex complexNullIBD = kinship.getComplexNullIBD();
    KinshipIBDComplex saved = new KinshipIBDComplex();
    complexNullIBD.copyTo(saved);      // SAVE
    complexNullIBD.setComplex(false);

//    PedigreeRatioHist hist = new PedigreeRatioHist();
    table = null;
    for (KinshipIBD nullIBD : nullArr)     {
      nullIBD.copyTo(complexNullIBD);
//      hist.add(tmpTable.getLogs());
      if (table == null) {
        table = new KinshipRatioSimArr(kinship);
        table.calc(kinship, pop);
      }
      else    {
        KinshipRatioSimTable tmpTable = new KinshipRatioSimTable();
        tmpTable.calc(kinship, pop);
        table.merge(tmpTable);
      }
    }
    saved.copyTo(complexNullIBD);      // RESTORE

//    PedigreeRatioHistView histView = new PedigreeRatioHistView(hist);
//    optView.setHistView(histView);

    KinshipRatioSimTableView view = new KinshipRatioSimTableView(kinship, table);
    optView.setRatioSimTableView(view);
    return true;
  }
}
