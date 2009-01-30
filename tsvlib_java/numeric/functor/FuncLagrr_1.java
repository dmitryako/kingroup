package numeric.functor;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 2:50:17 PM
 */
public class FuncLagrr_1 implements Func {
  final private double alpha;
  final private double lambda;
  public FuncLagrr_1(final double alpha, final double lambda) {
    this.alpha = alpha;
    this.lambda = lambda;
  }
  public double calc(double x) {
    return alpha + 1 - lambda * x;
  }
}