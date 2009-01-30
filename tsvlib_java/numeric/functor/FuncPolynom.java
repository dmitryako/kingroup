package numeric.functor;
import stlx.FastLoop;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 1:08:04 PM
 */
// f(x) = c[0] + c[1]*x + c[2]*x*x+...
public class FuncPolynom implements Func {
  final private double[] c;
  public FuncPolynom(double[] c) {
    this.c = c;
  }
  public double calc(double x) {
    return FastLoop.polynom(c, x);
  }
}