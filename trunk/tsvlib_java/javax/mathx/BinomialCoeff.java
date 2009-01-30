package javax.mathx;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/05/2005, Time: 13:15:22
 */
public class BinomialCoeff extends FactorialLog {
  public BinomialCoeff(int n) {
    super(n);
  }
  public double nCk(int n, int k) {
    return Math.exp(calc(n) - calc(k) - calc(n - k));
  }
}
