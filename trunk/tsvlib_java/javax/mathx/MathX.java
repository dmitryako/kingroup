package javax.mathx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/03/2005, Time: 11:21:39
 */
public class MathX {
  private static FactorialLog fact = new FactorialLog(10);
  public static int mod(int a, int b) { // fortran remainder
    return a % b; // remainder
  }
  public static double factLn(int n) {
    return fact.calc(n);
  }
  public static double factorial(int n) {
    return Math.exp(factLn(n));
  }
  /*
  n!/(n-k)!=n*(n-1)*...*(n-k+1)
  */
  public static double factLn2(double n, int k) {
    if (n < 2  ||  n < k)
      return 0;
    double res = 0;
    for (double i = n-k+1; i <= n; i++)
      res += Math.log(i);
    return res;
  }
  /*
  n!/[(n-k)! n^k]=n*(n-1)*...*(n-k+1)
  */
  public static double factLn3(double n, int k) {
    if (n < 2  ||  n < k)
      return 0;
    double res = 0;
    for (double i = n-k+1; i <= n; i++)
      res += Math.log((double)i/n);
    return res;
  }
  public static double fact2(double n, int k) {
    return Math.exp(factLn2(n, k));
  }
  public static double pow(double x, int k) {
    double res;
    switch (Math.abs(k)) {
      case 0:
        return 1.;
      case 1:
        res = x;
        break;
      case 2:
        res = x * x;
        break;
      case 3:
        res = x * x * x;
        break;
      case 4:
        res = x * x * x * x;
        break;
      case 5:
        res = x * x * x * x * x;
        break;
      case 6:
        double x3 = x * x * x;
        res = x3 * x3;
        break;
      case 7:
        x3 = x * x * x;
        res = x3 * x3 * x;
        break;
      case 8:
        double x4 = x * x * x * x;
        res = x4 * x4;
        break;
      case 9:
        x4 = x * x * x * x;
        res = x4 * x4 * x;
        break;
      case 10:
        double x5 = x * x * x * x * x;
        res = x5 * x5;
        break;
      default:
        return Math.pow(x, k);
    }
    if (res == 0)
      return res;
    return (k > 0) ? res : 1. / res;
  }
  public static int max(int a, int b, int c) {
    return Math.max(Math.max(a, b), c);
  }
  public static int max(int a, int b, int c, int d) {
    return Math.max(Math.max(a, b), Math.max(c, d));
  }
  public static int min(int a, int b, int c) {
    return Math.min(Math.min(a, b), c);
  }
  public static int min(int a, int b, int c, int d) {
    return Math.min(Math.min(a, b), Math.min(c, d));
  }
  public static byte min(byte a, byte b) {
    return a < b ? a : b;
  }
  public static byte max(byte a, byte b) {
    return a > b ? a : b;
  }
  public static int delta(Object a, Object b) {
    return (a.equals(b)) ? 1 : 0;
  }
  public static int delta(int i, int j) {
    return (i == j) ? 1 : 0;
  }

  public static double binomialCoeffLn(int n, int k)
  {
    return factLn(n) - factLn(n-k) - factLn(k);
  }
  public static double binomialCoeff(int n, int k)
  {
    return Math.exp(MathX.binomialCoeffLn(n, k));
  }

  public static int limit(int v, int minVal, int maxVal)
  {
    v = Math.min(v, maxVal);
    v = Math.max(minVal, v); 
    return v;
  }
}
