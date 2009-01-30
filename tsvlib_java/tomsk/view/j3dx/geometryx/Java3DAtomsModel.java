package tomsk.view.j3dx.geometryx;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 23, 2004, Time: 8:30:44 AM
 */
public class Java3DAtomsModel {
  protected Point3d[] pa_ = new Point3d[0]; // position/point array
  private Color3f[] ca_ = new Color3f[0]; // color array
  final public int size() {
    return pa_.length;
  }
  public Java3DAtomsModel(Point3d[] pa) {
    pa_ = pa;
    ca_ = new Color3f[size()];
    for (int i = 0; i < size(); i++) {
      ca_[i] = new Color3f();
    }
  }
  public Point3d[] getCoordinates() {
    return pa_;
  }
  public Color3f[] getColors() {
    return ca_;
  }
}
