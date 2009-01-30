package numeric.functor;
import javax.mathx.MathX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/03/2005, Time: 15:49:06
 */
public class FuncPowInt implements Func {
  private final double a;
  private final int k;
  public FuncPowInt(double a, int k) {
    this.a = a;
    this.k = k;
  }
  public double calc(double x) {
    return a * MathX.pow(x, k);
  }
}