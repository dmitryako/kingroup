package kingroup_v2.kinship;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/12/2006, Time: 12:40:58
 */
public class KHDistSqrEstimator implements CalculatorI
//  extends TEstimator
{
  private static ProjectLogger log = ProjectLogger.getLogger(KHDistSqrEstimator.class.getName());

  public static double ERROR_VALUE = 1. / Float.MAX_VALUE;
  private static final int TYPE = 0;
  private static final int TYPE2 = 1;
  protected final SysPop pop;

  public KHDistSqrEstimator(SysPop pop)
  {
    this.pop = pop;
//    super(pop);
  }

  public double calc(int ix, int iy) {
    double sum = 0;
    int NL = pop.getNumLoci();
    for (int L = 0; L < NL; L++) {
//      log.info("ix=" + ix + ", iy=" + iy + ", L=" + L);
      int a = pop.getAllele(ix, L, TYPE);
      int b = pop.getAllele(ix, L, TYPE2);
      int c = pop.getAllele(iy, L, TYPE);
      int d = pop.getAllele(iy, L, TYPE2);
      if (a == -1 || b == -1 || c == -1 || d == -1)
        continue; //ignore

      double x2 = (a == b ? 4: 2); // sum_j x_j^2
      double y2 = (c == d ? 4: 2); // sum_j y_j^2
      double xy
        = (a == c ? 1: 0)
        + (a == d ? 1: 0)
        + (b == c ? 1: 0)
        + (b == d ? 1: 0);
      double dist = x2 - 2. * xy + y2;
      sum += dist;
    }
    double dij = sum / (4. * NL);
    return dij;
  }
}
