package kingroup_v2.fsr.bound;
import javax.mathx.MathX;
import static javax.mathx.MathX.factLn2;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/11/2005, Time: 13:30:41
 */
public class FsrLowerBoundGail
{
  private final double[][] b;
  protected final int na; // num of alleles
  protected final int s;  // number of boxes
  protected final int r;  // number of balls
  private final double p;
  private final double p2;

  public FsrLowerBoundGail(int r, int nAlleles) {
    na = nAlleles;
    this.r = r;
    s = (nAlleles * (nAlleles + 1)) / 2;
    p = 1. / (nAlleles * nAlleles);
    p2 = 2. * p;
    b = makeB();
  }
  private double p(int i) {
    if (i <= na)
      return p;
    return p2;
  }
  public double getB(int r, int s) {
    return b[r][s];
  }
  public double[][] makeB() {
    // J.Appl.Prob. 16, 242-251 (1979)
    // B(r, n) = B(r, n-1) + r * p_n * B(r-1, n-1)
    // r = nGroups
    // n = (nAlleles * (nAlleles + 1)) / 2;
    //
    // B(r, 0)=0,
    // B(0, n)=1;

    double[][] b = new double[r + 1][s + 1];

    for (int m = 0; m <= r; m++) {
      b[m][0] = 0;
    }
    for (int j = 0; j <= s; j++) {
      b[0][j] = 1;
    }

    for (int m = 1; m <= r; m++) {
      for (int j = 1; j <= s; j++) {
        b[m][j] = b[m][j-1] + b[m-1][j-1] * m * p(j);
      }
    }
    return b;
  }
  public double calcGailP()
  {
    return (1. - getB(r, s));
  }
  public double calcGailDist()
  {
    return calcGailP();
  }
  public double calcMcConnellP(int nLoci) {
    double n2 = MathX.pow((double)s, nLoci);
    double resLn = factLn2(n2, r) - r * Math.log(n2);
    return (1. - Math.exp(resLn));
  }
  public double calcMcConnellDist(int nLoci) {
    return calcMcConnellP(nLoci);
  }
}
