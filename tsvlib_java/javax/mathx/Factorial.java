package javax.mathx;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/05/2005, Time: 13:24:21
 */
public class Factorial extends FactorialLog {
  public Factorial(int last) {
    super(last);
  }
  public double calc(int n) {
    return Math.exp(super.calc(n));
  }
}
