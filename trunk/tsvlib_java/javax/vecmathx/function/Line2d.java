package javax.vecmathx.function;
import javax.vecmath.Vector2d;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 24, 2004, Time: 5:26:18 PM
 */
public class Line2d extends Vector2d {
  public Line2d(Vector2d start, Vector2d end) {
    // java 3d should have this function
    // make a line that goes through 2 points
  }
  final public double a() {
    return x;
  }
  final public double b() {
    return y;
  }
  final public double calcY(double x) {
    return x * a() + b();
  }
}
