package kingroup_v2.relatedness.identix;
import kingroup_v2.kinship.CalculatorI;
import kingroup_v2.lynch.REstimator_LR;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import static javax.mathx.MathX.delta;
import static java.lang.Math.sqrt;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2006, Time: 12:36:33
 */
public class IdentixREstimator
  implements CalculatorI
{
  private static ProjectLogger log = ProjectLogger.getLogger(REstimator_LR.class.getName());
  private final SysPop pop;
  private static final int TYPE = 0;
  private static final int TYPE2 = 1;

  public IdentixREstimator(SysPop pop) {
    this.pop = pop;
  }

  public double calc(int ix, int iy) {
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

      double top = delta(a, c) + delta(a, d) + delta(b, c) + delta(b, d);
      double bot = 2. * sqrt(1. + delta(a, b)) * sqrt(1. + delta(c, d));
      double w = 1. / freq.getIdentixWeight(L);
//      double w = freq.getIdentixWeight(L);
//      w = sqrt(w);
      if (bot == 0)
        throw new RuntimeException("something is wrong in IdentixREstimator.calcAsym()");
      sum += w * top / bot;
      sumW += w;
    }
    return sum / sumW;
  }
}
