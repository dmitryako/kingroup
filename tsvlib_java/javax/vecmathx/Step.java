package javax.vecmathx;
import javax.vecmath.Point2d;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 24, 2004, Time: 4:38:32 PM
 */
final public class Step extends Point2d {
  public Step(Range r, int size) {
    super(r.range() / (size - 1), (size - 1) * r.oneOverRange());
  }
  public Step(double step) {
    super(step, 1. / step);
  }
  final public double step() {
    return x;
  }
  final public double oneOverStep() {
    return y;
  }
}
