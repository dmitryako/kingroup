package numeric.functor;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 9, 2004, Time: 8:09:17 AM
 */
public class functor_const_over implements Func {
  private final double c_;
  public functor_const_over(final double c) {
    c_ = c;
  }
  public double calc(double x) {
    return c_ / x;
  }
}
