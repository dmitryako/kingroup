package kingroup_v2.ucm.milligan;
import kingroup_v2.kinship.like.KinshipLogMtrx;
import kingroup_v2.like.milligan.MilliganRatioMtrx;
import kingroup_v2.like.milligan.view.MilliganRatioOptView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.ucm.kinship.UCKinshipCalcRatioMtrx;
import kingroup_v2.ucm.pedigree.UCPedigreeCalcSigLevels;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/03/2006, Time: 17:45:31
 */
public class UCMilliganCalcRatioMtrx extends UCKinshipCalcRatioMtrx {
//  private static ProjectLogger log = ProjectLogger.getLogger(UCPedigreeCalcRatioMtrx.class.getName());
  public UCMilliganCalcRatioMtrx(MilliganRatioOptView optView) {
    super();
    this.optView = optView;
    sigTest = new UCPedigreeCalcSigLevels(optView);
  }
  public KinshipLogMtrx makeRatioMtrx(SysPop sysPop) {return new MilliganRatioMtrx(sysPop);}
}