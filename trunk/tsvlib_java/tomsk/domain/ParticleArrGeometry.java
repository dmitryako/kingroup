package tomsk.domain;
import javax.media.j3d.PointArray;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/04/2007, 13:07:25
 */
public class ParticleArrGeometry  extends PointArray {
  private Point3dArr model;
  private float[] colors;

  public ParticleArrGeometry(Point3dArr model)
  {
    super(model.size(), COLOR_3 | COORDINATES | BY_REFERENCE);
    setCapability(ALLOW_REF_DATA_READ);
    setCapability(ALLOW_REF_DATA_WRITE);

//    setCapability(ALLOW_COLOR_WRITE);
//    setCapability(ALLOW_COORDINATE_WRITE);
    this.model = model;
    setCoordRefDouble(model.getArr());

    Point3fArr colors = new Point3fArr(model.size());
    setColorRefFloat(colors.getArr());
  }
}
