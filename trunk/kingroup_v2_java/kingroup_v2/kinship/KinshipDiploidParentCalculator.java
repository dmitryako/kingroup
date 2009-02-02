package kingroup_v2.kinship;
import kingroup_v2.pop.sample.sys.SysPop;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 08:26:06
 */
public class KinshipDiploidParentCalculator extends KinshipDiploidLikeCalculator {
  public KinshipDiploidParentCalculator(double rm, double rp) {
    super(rm, rp);
  }
  public double calcLocusLogMatPat(int i, int k, int L, SysPop pop) {
    if (!pop.getHasMatId() && !pop.getHasPatId())
      return calcProb();
    int iMat = pop.getMatId(i);
    int iPat = pop.getPatId(i);
    int kMat = pop.getMatId(k);
    int kPat = pop.getPatId(k);
    initMatPat();
    if (pop.getHasMatId()) {
      if (iMat != -1) {
        setMatX(pop.getMatAllele(i, L, 0));
        setMatX2(pop.getMatAllele(i, L, 1));
      }
      if (kMat != -1) {
        setMatY(pop.getMatAllele(k, L, 0));
        setMatY2(pop.getMatAllele(k, L, 1));
      }
    }
    if (pop.getHasPatId()) {
      if (iPat != -1) {
        setPatX(pop.getPatAllele(i, L, 0));
        setPatX2(pop.getPatAllele(i, L, 1));
      }
      if (kPat != -1) {
        setPatY(pop.getPatAllele(k, L, 0));
        setPatY2(pop.getPatAllele(k, L, 1));
      }
    }
    return calcProbMatPat();
  }

}
