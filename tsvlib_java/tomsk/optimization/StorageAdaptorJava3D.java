package tomsk.optimization;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 17, 2004, Time: 3:16:37 PM
 */
final public class StorageAdaptorJava3D {
  private Point3d[] pa_; // coordinate/position array
  private Vector3d[] va_; // velocity array
  public int size() {
    return pa_.length;
  }
  public Point3d getPositionSlow(int i) {
    if (i < 0 || i >= size())
      return null;
    return pa_[i];
  }
  final public Point3d[] getPositionArray() {
    return pa_;
  }
  final public Point3d getPositionFast(int i) {
    return pa_[i];
  }
  final public Vector3d getVelocitySlow(int i) {
    if (i < 0 || i >= size())
      return null;
    return va_[i];
  }
  public Vector3d getVelFast(int i) {
    return va_[i];
  }
  public StorageAdaptorJava3D(int size) {
    if (size < 0)
      size = 0;
    pa_ = new Point3d[size];
    va_ = new Vector3d[size];
    init();
  }
  public void init() {
    for (int i = 0; i < pa_.length; i++)
      pa_[i] = new Point3d();
    for (int i = 0; i < va_.length; i++)
      va_[i] = new Vector3d();
  }
}
