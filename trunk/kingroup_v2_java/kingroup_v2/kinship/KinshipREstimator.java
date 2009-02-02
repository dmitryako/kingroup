package kingroup_v2.kinship;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/01/2006, Time: 12:34:16
 */
public class KinshipREstimator
  implements CalculatorI
{
  public static double ERROR_VALUE = 1. / Float.MAX_VALUE;
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipREstimator.class.getName());
  private final SysPop pop;
  private static final int TYPE = 0;
  private static final int TYPE2 = 1;

  public KinshipREstimator(SysPop pop) {
    this.pop = pop;
  }

  public double calc(int ix, int iy) {
    double top = 0;
    double bot = 0; // bottom
    SysAlleleFreq freq = pop.getFreq();
    for (int L = 0; L < pop.getNumLoci(); L++) {
//      log.info("ix=" + ix + ", iy=" + iy + ", L=" + L);
      int a = pop.getAllele(ix, L, TYPE);
      int b = pop.getAllele(ix, L, TYPE2);
      int c = pop.getAllele(iy, L, TYPE);
      int d = pop.getAllele(iy, L, TYPE2);
      if (a == -1 || b == -1 || c == -1 || d == -1)
        continue; //ignore

      // PRECOND: (a != -1 || b != -1) && (c != -1 || d != -1)
      double pa = freq.getFreq(L, a);
      double pb = freq.getFreq(L, b);
      double pc = freq.getFreq(L, c);
      double pd = freq.getFreq(L, d);

      double tx = calcTop2(a, c, d); // top: P_y
      double tx2 = calcTop2(b, c, d);
      double ty = calcTop2(c, a, b);
      double ty2 = calcTop2(d, a, b);
      double bx = calcBottom2(a, b); // bottom: P_x
      double bx2 = calcBottom2(b, a);
      double by = calcBottom2(c, d);
      double by2 = calcBottom2(d, c);

      top += (tx - pa + tx2 - pb + ty - pc + ty2 - pd);
      bot += (bx - pa + bx2 - pb + by - pc + by2 - pd);
    }
    if (bot == 0)
      return ERROR_VALUE;
    return top / bot;
  }

  private double calcTop2(int x, int y, int y2) {
    if (x == -1)
      return 0;
    double delta = 0.5;
    if (y == -1  || y2 == -1)
      delta = 1;
    double res = 0;
    if (y != -1)
      res = (x == y ? delta : 0.0);
    if (y2 != -1)
      res += (x == y2 ? delta : 0.0);
    return res;
  }
  private double calcBottom2(int x, int x2) {
    if (x == -1)
      return 0;
    if (x2 == -1)
      return 1;
    return (x == x2 ? 1 : 0.5);
  }
}
