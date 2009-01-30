package tomsk.view.j3dx.geometryx;
import javax.media.j3d.PointArray;
import javax.utilx.RandomSeed;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import java.util.Random;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 17, 2004, Time: 11:32:08 AM
 */
public class Java3DParticlesGeometry extends PointArray {
  protected Random rand_ = RandomSeed.getInstance();
  protected Point3d[] pa_ = new Point3d[0]; // position/point array
  private Color3f[] ca_ = new Color3f[0]; // color array
  final public int size() {
    return pa_.length;
  }
  // todo: remove
  public Java3DParticlesGeometry(int numPoints) {
    super(numPoints, COLOR_3 | COORDINATES);
    setCapability(ALLOW_COLOR_WRITE);
    setCapability(ALLOW_COORDINATE_WRITE);
    pa_ = new Point3d[numPoints];
    ca_ = new Color3f[numPoints];
    for (int i = 0; i < numPoints; i++) {
      pa_[i] = new Point3d();
      ca_[i] = new Color3f();
    }
    initRandCoord();
    updateColor();
  }
  public Java3DParticlesGeometry(Point3d[] pa) {
    super(pa.length, COLOR_3 | COORDINATES);
    setCapability(ALLOW_COLOR_WRITE);
    setCapability(ALLOW_COORDINATE_WRITE);
    pa_ = pa;
    ca_ = new Color3f[size()];
    for (int i = 0; i < size(); i++) {
      ca_[i] = new Color3f();
    }
    initRandCoord();
    updateColor();
  }
  final private void initRandCoord() {
    for (int i = size(); --i >= 0;) {
      Point3d p = pa_[i];
      p.x = 2 * rand_.nextDouble() - 1;
      p.y = 2 * rand_.nextDouble() - 1;
      p.z = 2 * rand_.nextDouble() - 1;
    }
    setCoordinates(0, pa_);
  }
  final protected void forceIntoBox() {
    for (int i = size(); --i >= 0;) {
      Point3d p = pa_[i];
      if (p.x < -1)
        p.x += 1;
      if (p.y < -1)
        p.y += 1;
      if (p.z < -1)
        p.z += 1;
      if (p.x > 1)
        p.x -= 1;
      if (p.y > 1)
        p.y -= 1;
      if (p.y > 1)
        p.y -= 1;
    }
  }
  final protected void updateColor() {
    for (int i = size(); --i >= 0;) {
      Point3d p = pa_[i];
      Color3f c = ca_[i];
      c.x = (float) (0.5 * (1 + p.z));
      c.y = 0; //rand_.nextFloat();
      c.z = (float) (0.5 * (1 - p.z));
    }
    setColors(0, ca_);
  }
}