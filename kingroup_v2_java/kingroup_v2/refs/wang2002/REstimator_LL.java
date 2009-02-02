package kingroup_v2.refs.wang2002;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/09/2007, Time: 15:21:39
 */
public class REstimator_LL   extends REstimator_W
{
  private static ProjectLogger log = ProjectLogger.getLogger(REstimator_LL.class);
  private static final double S_1 = 1.;
  private static final double S_2 = 0.75;
  private static final double S_3 = 0.5;

  public REstimator_LL(SysPop pop, SysPop refPop)
  {
    super(pop, refPop);
  }

//  public double calc(int ix, int iy) {
//    double sum = 0;
//    int nL = pop.getNumLoci();
//    for (int L = 0; L < nL; L++) {
//      sum += calc(ix, iy, L) / (U * u[L]);
////      sum += calc(ix, iy, L);
//    }
//    return sum / nL;
//  }
//
  public double calc(int ix, int iy, int L) {
    int x = pop.getAllele(ix, L, TYPE);
    int x2 = pop.getAllele(ix, L, TYPE2);
    int y = pop.getAllele(iy, L, TYPE);
    int y2 = pop.getAllele(iy, L, TYPE2);
    if (x == -1 || x2 == -1 || y == -1 || y2 == -1)
      return 0; //ignore

    double s = 0;
    if (isP1(x, x2, y, y2))
      s = S_1;
    else if (isP2(x, x2, y, y2))
      s = S_2;
    else if (isP3(x, x2, y, y2))
      s = S_3;
    return (s - u[L]) / (1. - u[L]);
  }

}

