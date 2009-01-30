package tomsk.view.j3dx.geometryx;
import javax.media.j3d.PointArray;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 23, 2004, Time: 9:03:32 AM
 */
public class ColorPointArrayGeometry extends PointArray {
  public ColorPointArrayGeometry(Java3DAtomsModel model) {
    super(model.size(), COLOR_3 | COORDINATES);
    setCapability(ALLOW_COLOR_WRITE);
    setCapability(ALLOW_COORDINATE_WRITE);
    setCoordinates(0, model.getCoordinates());
    setColors(0, model.getColors());
  }
}
