package kingroup_v2.ucm.relatedness;
import kingroup_v2.Kingroup;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.refs.wang2002.RMtrx_LL;
import kingroup_v2.refs.wang2002.RMtrx_W;
import kingroup_v2.relatedness.PairwiseRMtrx;
import kingroup_v2.relatedness.RMtrxOutbredKonHeg;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/12/2006, Time: 10:48:33
 */
public class PairwiseRMtrxFactory
{
  private int type;
  public PairwiseRMtrxFactory(int type) {
    this.type = type;
  }

  public PairwiseRMtrx makeRMtrx(SysPop group, SysPop refPop) {
    return makeRMtrx(group, refPop, type);
  }
  public static PairwiseRMtrx makeRMtrx(SysPop group, SysPop refPop, int type) {
    PairwiseRMtrx mtrx;
    if (type == Kingroup.PAIRWISE_R_KH_OUTBRED) {
      RMtrxOutbredKonHeg mtrx2 = new RMtrxOutbredKonHeg(group);
      mtrx2.setReferencePop(refPop); // needed for heteroz
      mtrx = mtrx2;
    }
    else if (type == Kingroup.PAIRWISE_R_WANG) {
      mtrx = new RMtrx_W(group, refPop);
    }
    else if (type == Kingroup.PAIRWISE_R_LL) {
      mtrx = new RMtrx_LL(group, refPop);
    }
    else {
      mtrx = new PairwiseRMtrx(group);
      mtrx.setEstimator(PairwiseREstimatorFactory.makeREstimator(group, type));
    }
    mtrx.calc();
    return mtrx;
  }
}
