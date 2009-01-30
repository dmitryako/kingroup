package javax.vecmathx;
import javax.vecmath.Point4d;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 24, 2004, Time: 4:30:43 PM
 */
final public class Range extends Point4d {
  public Range(double first, double last) {
    super(first, last, last - first, 1. / (last - first));
  }
  final public double left() {
    return x;
  }
  final public double right() {
    return y;
  }
  final public double range() {
    return z;
  }
  final public double oneOverRange() {
    return w;
  }
}
