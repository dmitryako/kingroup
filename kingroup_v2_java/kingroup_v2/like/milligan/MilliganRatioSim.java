package kingroup_v2.like.milligan;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipRatioSim;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.vecmathx.matrix.MtrxReadI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 16:42:18
 */
public class MilliganRatioSim  extends KinshipRatioSim
{
//  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRatioSim.class.getName());
  private final MilliganIBD mIBD;
  private final Milligan milligan;

  public MilliganRatioSim(MilliganIBD ibd, Milligan milligan, Kinship kinship)
  {
    super(null, kinship);
    this.mIBD = ibd;
    this.milligan = milligan;
  }
  public void makeDiploidYFromX(int yIdx, int xIdx, SysPop pop) {
    MilliganSysPopFactory.makeDiploidYFromX(mIBD, yIdx, xIdx, pop);
  }
  public MtrxReadI makeRatioMtrx(SysPop pop) {
    MilliganRatioMtrx mtrx = new MilliganRatioMtrx(pop);
    mtrx.calcRatio(milligan);
    return mtrx;
  }
}
