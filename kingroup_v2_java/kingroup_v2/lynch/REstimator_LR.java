package kingroup_v2.lynch;
import kingroup_v2.kinship.CalculatorI;
import kingroup_v2.kinship.KinshipREstimator;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import static javax.mathx.MathX.delta;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2006, Time: 12:36:46
 */
public class REstimator_LR
  implements CalculatorI
{
  private static ProjectLogger log = ProjectLogger.getLogger(REstimator_LR.class.getName());
  private final SysPop pop;
  private static final int TYPE = 0;
  private static final int TYPE2 = 1;

  public REstimator_LR(SysPop pop) {
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
    double sumW = 0;
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

      double top = pa * (delta(b, c) + delta(b, d)) + pb * (delta(a, c) + delta(a, d)) - 4. * pa * pb;
      double bot = 2. * pa * pb;
      double w = (1. + delta(a, b)) * (pa + pb) - 4. * pa * pb;
      if ((float)bot == 0f)
        return KinshipREstimator.ERROR_VALUE;
      sum += top / bot;
      sumW += w / bot;
    }
    if ((float)sumW == 0f)
      return KinshipREstimator.ERROR_VALUE;
    return sum / sumW;
  }
}
