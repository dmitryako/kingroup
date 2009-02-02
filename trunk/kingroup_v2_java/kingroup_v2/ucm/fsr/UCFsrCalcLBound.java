package kingroup_v2.ucm.fsr;

import kingroup_v2.Kingroup;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.fsr.FsrLowerBoundBean;
import kingroup_v2.fsr.bound.*;
import pattern.ucm.UCController;

import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/11/2005, Time: 08:56:58
 */
public class UCFsrCalcLBound  implements UCController
 {
  private static ProjectLogger log = ProjectLogger.getLogger(UCFsrCalcLBound.class.getName());

  private final FsrLowerBoundView optionsView;

  public UCFsrCalcLBound(FsrLowerBoundView optionsView) {
    this.optionsView = optionsView;
  }
  public boolean run()
  {
    Kingroup project = KinGroupV2Project.getInstance();
    FsrLowerBoundBean model = project.getFsrLowerBound();
    optionsView.loadTo(model);
    project.saveProjectToDefaultLocation();

    int r = model.getNumGroups();
    int na = model.getNumAlleles();
    int L = model.getNumLoci();
    int g = model.getGroupSize();
    FsrLBoundApproxGSOne gsOne = new FsrLBoundApproxGSOne(r, na, L);
    double gsOneRes = gsOne.calcAccuracyError(true);
    optionsView.setResultGroupSizeOne((float)(100.*gsOneRes));

    FsrLBoundApproxGSLarge gsLarge = new FsrLBoundApproxGSLarge(r, na, L);
    FsrLBoundResults gsLargeRes = gsLarge.calc(g);
    optionsView.setPhenotypeProb(gsLargeRes.getPhenotypeProb());
    optionsView.setResultGroupSizeLarge((float)(100.*gsLargeRes.getAccuracyError()));

    FsrLBoundMonteCarlo mc = new FsrLBoundMonteCarlo(r, na, L);
    FsrLBoundResults mcRes = mc.runSim(g, model.getNumTrials());

    optionsView.setResultMonteCarlo((float)(100.*mcRes.getAccuracyError()));
    optionsView.setPhenotypeProbMC(mcRes.getPhenotypeProb());
    optionsView.repaint();
    return true;
  }
}