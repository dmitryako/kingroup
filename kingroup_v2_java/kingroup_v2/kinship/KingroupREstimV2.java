package kingroup_v2.kinship;

import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.mathx.MathX;
import javax.utilx.arrays.vec.Vec;

/**
 * Created by: jc1386591
 * Date: 12/07/2006. Time: 11:39:04
 */
public class KingroupREstimV2 implements CalculatorI
{
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipREstimator.class.getName());
  private final SysPop pop;
  private static final int TYPE = 0;
  private static final int TYPE2 = 1;

  public KingroupREstimV2(SysPop pop) {
    this.pop = pop;
  }

  public double calc(int ix, int iy) {
    double sum = 0;
    int NL = pop.getNumLoci();
    SysAlleleFreq freq = pop.getFreq();
    for (int L = 0; L < NL; L++) {
//      log.info("ix=" + ix + ", iy=" + iy + ", L=" + L);
      int a = pop.getAllele(ix, L, TYPE);
      int b = pop.getAllele(ix, L, TYPE2);
      int c = pop.getAllele(iy, L, TYPE);
      int d = pop.getAllele(iy, L, TYPE2);
      if (a == -1 || b == -1 || c == -1 || d == -1)
        continue; //ignore

      sum += (MathX.delta(a, c) + MathX.delta(a, d)
        + MathX.delta(b, c) + MathX.delta(b, d));
      sum -= freq.getFreq(L, a);
      sum -= freq.getFreq(L, b);
      sum -= freq.getFreq(L, c);
      sum -= freq.getFreq(L, d);
    }
    double r = sum / NL;
    return r;
  }

  public double calc3(int ix, int iy) {
    if (ix == iy)
      return 1.;
    double top = 0;
    int NL = pop.getNumLoci();
    for (int L = 0; L < NL; L++) {
      double[] pm = pop.getFreq().getLocFreq(L);
      double[] xm = new double[pm.length];
      double[] ym = new double[pm.length];

//      log.info("ix=" + ix + ", iy=" + iy + ", L=" + L);
      int a = pop.getAllele(ix, L, TYPE);
      int b = pop.getAllele(ix, L, TYPE2);
      int c = pop.getAllele(iy, L, TYPE);
      int d = pop.getAllele(iy, L, TYPE2);
      if (a == -1 || b == -1 || c == -1 || d == -1)
        continue; //ignore

      xm[a]++;
      xm[b]++;
      ym[c]++;
      ym[d]++;
      Vec.add(xm, -1, pm);
      Vec.add(ym, -1, pm);
      top += Vec.dot(xm, ym);
    }
    return 2. * top / NL;
  }

  public double calc2(int ix, int iy) {
    double top = 0;
    double bot = 0;
    int NL = pop.getNumLoci();
    for (int L = 0; L < NL; L++) {
      double[] pm = pop.getFreq().getLocFreq(L);
      double[] xm = new double[pm.length];
      double[] ym = new double[pm.length];

//      log.info("ix=" + ix + ", iy=" + iy + ", L=" + L);
      int a = pop.getAllele(ix, L, TYPE);
      int b = pop.getAllele(ix, L, TYPE2);
      int c = pop.getAllele(iy, L, TYPE);
      int d = pop.getAllele(iy, L, TYPE2);
      if (a == -1 || b == -1 || c == -1 || d == -1)
        continue; //ignore

      xm[a]++;
      xm[b]++;
      ym[c]++;
      ym[d]++;
      Vec.add(xm, -1, pm);
      Vec.add(ym, -1, pm);
      top += Vec.dot(xm, ym);
      bot += Vec.dot(xm, xm);
      bot += Vec.dot(ym, ym);
    }
    return 2. * top / bot;
  }
}
