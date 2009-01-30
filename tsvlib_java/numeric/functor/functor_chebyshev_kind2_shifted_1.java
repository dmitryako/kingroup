package numeric.functor;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 1:50:13 PM
 */
public class functor_chebyshev_kind2_shifted_1 extends functor_chebyshev_kind2_1 {
  // shifted: U*(x) = U(2x-1)
  public double calc(double x) {
    return super.calc(2. * x - 1);
  }
}
