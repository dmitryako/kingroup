package kingroup_v2.refs.wang2002;
import kingroup_v2.kinship.CalculatorI;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.mathx.MathX;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/09/2006, Time: 09:08:50
 */
public class REstimator_W implements CalculatorI
{
  private static ProjectLogger log = ProjectLogger.getLogger(REstimator_W.class.getName());
  protected final SysPop pop;
  private final SysAlleleFreq sampleFreq;
  protected static final int TYPE = 0;
  protected static final int TYPE2 = 1;
  private double A2;
  private double A22;
  private double A3;
  private double A4;

  private double b;
  private double b1;
  private double c;
  private double d;
  private double e;
  private double e2f;
  private double ef2;
  private double f;
  private double g;
  private double V;
  protected double U;
  protected double[] u;

  public REstimator_W(SysPop pop, SysPop refPop) {
    // Wang (2002) Genetics 160 p1203

    this.pop = pop;
    sampleFreq = SysAlleleFreqFactory.makeFrom(refPop, true);
//    log.info("sampleFreq=\n" + sampleFreq);

    int n = pop.size();
    int nLoci = pop.getNumLoci();
    U = 0;
    u = new double[nLoci];
    for (int L = 0; L < nLoci; L++)
    {
      double[] loc = sampleFreq.getLocFreq(L);
      double m2 = Vec.dot(loc, loc);
      double m3 = Vec.dot(loc, loc, loc);
      double m4 = Vec.dot(loc, loc, loc, loc);
      double N = 2. * n;
      double N1 = N - 1.;
      double N2 = N - 2.;
      double N3 = N - 3.;
      double a2 = (N * m2 - 1.) / N1;     //Eq 12
      double a22 = a2 * a2;
      double a3 = (N * N * m3 - 3. * N1 * a2 - 1.) / (N1 * N2); // Eq 13
      double a4 = (N * N * N * m4 - 6. * N1 * N2 * a3 - 7 * N1 * a2 - 1.) / (N1 * N2 * N3); // Eq 14
      u[L] = 2. * a2 - a3;
      A2  += a2  / u[L];
      A22 += a22 / u[L];
      A3  += a3  / u[L];
      A4  += a4  / u[L];
      U   +=  1. / u[L];  // p1206
    }
    A2  /= U;
    A22 /= U;
    A3  /= U;
    A4  /= U;
//    Vec.mult(U, u);

    b = 2. * A22 - A4;
    b1 = 1. - b;
    c = A2 - 2. * A22 + A4;
    d = 4. * (A3 - A4);
    e = 2. * (A2 - 3 * A3 + 2 * A4);
    f = 4 * (A2 - A22 - 2. * A3 + 2. * A4);
    g = 1. - 7. * A2 + 4 * A22 + 10 * A3 - 8 * A4;

    e2f = e * e * f + d * g * g;
    ef2 = MathX.pow(e * f - d * g, 2);

    V = b1 * b1 * e2f - b1 * ef2
      + 2. * c * d * f * b1 * (g + e)  +  c * c * d * f * (d + f); // Eq 11
//    if (V == 0) {
//      int debug = 1;
//    }
  }


  public double calc(int ix, int iy) {
    double sum = 0;
    for (int L = 0; L < pop.getNumLoci(); L++) {
      sum += calc(ix, iy, L) / (U * u[L]);
    }
    return sum;
  }
  public double calc(int ix, int iy, int L) {
    int x = pop.getAllele(ix, L, TYPE);
    int x2 = pop.getAllele(ix, L, TYPE2);
    int y = pop.getAllele(iy, L, TYPE);
    int y2 = pop.getAllele(iy, L, TYPE2);
    if (x == -1 || x2 == -1 || y == -1 || y2 == -1)
      return 0; //ignore

    double P1 = 0;
    double P2 = 0;
    double P3 = 0;
    if (isP1(x, x2, y, y2))
      P1 = 1.;
    else if (isP2(x, x2, y, y2))
      P2 = 1.;
    else if (isP3(x, x2, y, y2))
      P3 = 1.;

    double phi = (d * f * ((e + g) * b1 + c * (d + f)) * (P1 - 1.)
      + d * b1 * (g * (b1 - d) + f * (c + e)) * P3
      + f * b1 * (e * (b1 - f) + d * (c + g)) * P2
    ) / V;  // Eq 9

    double delta = (c * d * f * (e + g) * (P1 + 1 - 2. * b)
      + (b1 * e2f - ef2) * (P1 - b)
      + c * (d * g - e * f) * (d * P3 - f * P2)
      - c * c * d * f * (P3 + P2 - d - f)
      - c * b1 * (d * g * P3 + e * f * P2)
    ) / V; // Eq 10

    return 0.5 * phi + delta; //Eq 1
  }

  public static boolean isP1(int a, int b, int c, int d) {
    if (a == b  &&  b == c  &&  c == d)
      return true;
    if ((a == c  &&  b == d)
    ||  (a == d  &&  b == c))
      return true;
    return false;
  }
  public static boolean isP2(int a, int b, int c, int d) {
    if (a == b  &&  b == c  &&  c != d) // aaab
      return true;
    if (a == b  &&  b == d  &&  c != d) // aaba
      return true;
    if (a != b  &&  a == c  &&  c == d) // abaa
      return true;
    if (a != b  &&  b == c  &&  c == d) // baaa
      return true;
    return false;
  }
  public static boolean isP3(int a, int b, int c, int d) {
    if (a != b  &&  a == c  &&  c != d) // aaab
      return true;
    if (a != b  &&  a == d  &&  c != d) // aaba
      return true;
    if (a != b  &&  b == c  &&  c != d) // abaa
      return true;
    if (a != b  &&  b == d  &&  c != d) // baaa
      return true;
    return false;
  }
}

