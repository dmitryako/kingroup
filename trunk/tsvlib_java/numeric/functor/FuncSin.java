package numeric.functor;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 1:23:39 PM
 */
public class FuncSin implements Func {
  public double calc(double x) {
    return Math.sin(x);
  }
}
