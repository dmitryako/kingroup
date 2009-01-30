package numeric.functor;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/03/2005, Time: 18:24:48
 */
public class FuncPow implements Func {
  private final double a;
  private final double b;
  public FuncPow(double a, double b) {
    this.a = a;
    this.b = b;
  }
  public double calc(double x) {
    return a * Math.pow(x, b);
  }
}