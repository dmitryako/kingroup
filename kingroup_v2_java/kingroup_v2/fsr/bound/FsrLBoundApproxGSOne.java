package kingroup_v2.fsr.bound;
import tsvlib.project.ProjectLogger;

import javax.mathx.FactorialLog;
import javax.mathx.MathX;
import javax.utilx.arrays.vec.Vec;
import static java.lang.Math.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/11/2005, Time: 15:18:03
 */
public class FsrLBoundApproxGSOne extends FsrLBound// approximation for lower bound for group size = 1
{
  private static ProjectLogger log = ProjectLogger.getLogger(FsrLBoundApproxGSOne.class.getName());
  private static final double EPS = 1.e-10;
  private static final double R_OVER_S_APPROX = 0.01;
  private final FactorialLog fact;
  public double[] Pk;

  public FsrLBoundApproxGSOne(int r, int nAlleles, int nLoci)
  {
    super(r, nAlleles, nLoci);
    fact = new FactorialLog(r);
  }

  public double calcAccuracyError2()  {
    double errors = calcNumErrors2();
    return errors / r;
  }
  public double calcAccuracyError(boolean stable)  {
    double errors = calcNumErrors(stable);
    return errors / r;
  }
  public double calcNumErrors(boolean byRecusion)  {
    double p[][] = null;
    if (byRecusion) {
      p = calc_pk2();
    }

    double res = 0;
    double delta2 = 0;
    int kmax = min(r-1, s);
    Pk = Vec.makeArray(0., kmax+1);
    for (int k = kmax; k >= 1; k--)
    {
      double pk;

      if (byRecusion)
        pk = p[k][r];
      else
        pk = calc_pk(k);
      double cLn = MathX.factLn3(s, k);
      double kkLn = -MathX.factLn3(k, k);
      double rj = Math.pow((double)k/s, r-k);
      double delta = pk * rj * exp(cLn + kkLn);
      if (abs(delta) < abs(delta2) && abs(delta) < EPS)
        break;
      delta2 = delta;
      Pk[k] = delta/r;
      res += delta * (r-k);
//      log.info("\npk["+k+"]["+r+"] = " + (float)pk
//        +"\ncLn=" + cLn
//        +"\nkkLn=" + kkLn
//        +"\nrj=" + rj
//        +"\ndelta = " + (float)delta
//        +"\nres = " + (float)res
//      );
    }
    return res;
  }

  public double calcNumErrors2()  {
    double res = 0;
    double delta2 = 0;
    double bigP[][] = calcBigP();
    int kmax = min(r-1, s);
//    Pk = DoubleArr.make(0., kmax+1);
    for (int k = kmax; k >= 1; k--)
    {
      double delta = bigP[k][r] * (r-k);
      if (abs(delta) < abs(delta2) && abs(delta) < EPS)
        break;
//      Pk[k] = bigP[k][r]/r;
      delta2 = delta;
      res += delta;
    }
    return res;
  }

//  public double calcFilledBoxesApprox(int k)
//  {
//    double gamma = k * exp(-(double)r/k);
//    return exp(-gamma);
//  }
  public double calc_pk(int k)
  {
    if (k > r)
      return 0;
    double res = 0;
    int sj = -1;
    double delta2 = 0;
    for (int j = 0; j <= k; j++)
    {
      sj *= (-1);  // sj = 1, j=0
      double c = MathX.binomialCoeff(k, j);
      double rj = MathX.pow((double)(k-j)/k, r);
      double delta = c * rj * sj;
      if (abs(delta) < abs(delta2)  && abs(delta) < EPS)
      {
        break;
      }
      res += delta;
      delta2 = delta;
    }
    return res;
  }

  public double[][] calc_pk2()
  {
    double[][] p = new double[r+1][r+1];
    p[0][0] = 1.;
    for (int k2 = 1; k2 <= r; k2++) {
      for (int r2 = 1; r2 <= r; r2++) {
        for (int m = 0; m < r2; m++)
        {
          double c = MathX.binomialCoeff(r2, m);
          p[k2][r2] += c * p[k2-1][m];
        }
      }
    }
    for (int k2 = 1; k2 <= r; k2++) {
      for (int r2 = 1; r2 <= r; r2++) {
        p[k2][r2] /= pow(k2, r2);
      }
    }
    return p;
  }
  public double[][] calcBigP()
  {
    double[][] p = new double[r][r+1];
    for (int k2 = 0; k2 < r; k2++) {
      for (int r2 = 0; r2 <= r; r2++) {
        p[k2][r2] = 0.;
      }
    }
    p[0][0] = 1.;
    for (int k2 = 1; k2 < r; k2++) {
      for (int r2 = 1; r2 <= r; r2++) {
        for (int m = 0; m < r2; m++)
        {
          double c = MathX.binomialCoeff(r2, m);
          double d = pow(s, m-r2) * (s-k2+1) / k2;
//          log.info(
//            "\np[k2][r2]=p["+k2+"]["+r2+"] = " + (float)p[k2][r2]
//              +"\np[k2-1][m]=p["+(k2-1)+"]["+m+"] = " + (float)p[k2-1][m]
//          );
          p[k2][r2] += c * d * p[k2-1][m];
        }
      }
    }
    return p;
  }
  public double calcBigP(int k) // for testing
  {
    return calcBigP()[k][r];
  }
  public double calc_pk2(int k) // for testing
  {
    return calc_pk2()[k][r];
  }
}
