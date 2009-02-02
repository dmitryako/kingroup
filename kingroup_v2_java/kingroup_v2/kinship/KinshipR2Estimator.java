package kingroup_v2.kinship;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import static javax.mathx.MathX.delta;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/05/2006, Time: 11:43:23
 */
public class KinshipR2Estimator
  implements CalculatorI
{
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipR2Estimator.class.getName());
  private final SysPop pop;
  private static final int TYPE = 0;
  private static final int TYPE2 = 1;

  public KinshipR2Estimator(SysPop pop) {
    this.pop = pop;
  }

  public double calc(int ix, int iy) {
    double r = calcAsym(ix, iy);
    if (r == Double.MAX_VALUE)
      return r;
    double r2 = calcAsym(iy, ix);
    if (r2 == Double.MAX_VALUE)
      return r2;
    return 0.5 * (r + r2);
  }

  public double calcAsym(int ix, int iy) {
    SysAlleleFreq freq = pop.getFreq();
    double sum = 0;
    for (int L = 0; L < pop.getNumLoci(); L++) {
//      log.info("ix=" + ix + ", iy=" + iy + ", L=" + L);
      int a = pop.getAllele(ix, L, TYPE);
      int b = pop.getAllele(ix, L, TYPE2);
      int c = pop.getAllele(iy, L, TYPE);
      int d = pop.getAllele(iy, L, TYPE2);
      if (a == -1 || b == -1 || c == -1 || d == -1)
        continue; //ignore

      // PRECOND: (x != -1 || x2 != -1) && (y != -1 || y2 != -1)
      double pa = freq.getFreq(L, a);
      double pb = freq.getFreq(L, b);

      double top = 0.5 * (delta(a, c) + delta(a, d) + delta(b, c) + delta(b, d)) - pa - pb;
      double bot = 1 + delta(a, b) - pa - pb;
      if ((float)bot == 0f)
        return KinshipREstimator.ERROR_VALUE;
      sum += top / bot;

//      if (sum > 1)  {
//        log.info("pop=\n" + pop);
//        log.info("freq=\n" + freq);
//        log.info("ix=" + ix + ", iy=" + iy);
//        int debug = 1;
//      }
    }
    return sum / pop.getNumLoci();
  }
}
