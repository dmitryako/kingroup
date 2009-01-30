package numeric.functor;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 12:52:41 PM
 */
public class FuncConst implements Func {
  final private double c0;
  public FuncConst(double v) {
    c0 = v;
  }
  public double calc(double x) {
    return c0;
  }
}
