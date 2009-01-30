package tomsk.view.j3dx.geometryx;
import javax.vecmath.Point3d;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 17, 2004, Time: 11:38:49 AM
 */
public class Java3DBrownianGeometry extends Java3DParticlesGeometry {
  private double scalePerFrame_ = 0.01;
  public Java3DBrownianGeometry(int num) {
    super(num);
  }
  public void calcNextFrame() {
    moveCoord(scalePerFrame_);
    updateColor();
  }
  private void moveCoord(double scale) {
    for (int i = size(); --i >= 0;) {
      Point3d p = pa_[i];
      p.x += scale * (2 * rand_.nextDouble() - 1);
      p.y += scale * (2 * rand_.nextDouble() - 1);
      p.z += scale * (2 * rand_.nextDouble() - 1);
    }
    forceIntoBox();
    setCoordinates(0, pa_);
  }
  public Point3d getPosition(int idx) {
    return pa_[idx];
  }
}
