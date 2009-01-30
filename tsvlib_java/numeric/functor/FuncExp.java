package numeric.functor;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 9:11:28 AM
 */
public class FuncExp implements Func {
  private final double c_;
  public FuncExp(final double c) {
    c_ = c;
  }
  public double calc(double x) {
    return Math.exp(c_ * x);
  }
}
