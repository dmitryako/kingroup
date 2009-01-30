package tomsk.domain;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GeometryUpdater;
import javax.utilx.RandomSeed;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/04/2007, 13:26:30
 */
public class ParticleArrGeometryUpdater implements GeometryUpdater
{
  private double SCALE_PER_FRAME = 0.01;

  public ParticleArrGeometryUpdater()
  {
  }

  public void updateData(Geometry geometry)
  {
    GeometryArray arr = (GeometryArray)geometry;
    moveCoord(SCALE_PER_FRAME, arr);
    updateColor(arr);
  }

  private void moveCoord(double scale, GeometryArray geom) {
    Point3dArr arr = new Point3dArr(geom.getCoordRefDouble());
    RandomSeed rand = RandomSeed.getInstance();
    for (int i = 0; i < arr.size(); i++) {
      arr.addX(i, scale * rand.nextDouble(-1., 1.));
      arr.addY(i, scale * rand.nextDouble(-1., 1.));
      arr.addZ(i, scale * rand.nextDouble(-1., 1.));
    }
    forceIntoBox(geom);
  }

  final protected void updateColor(GeometryArray geom) {
    Point3dArr coord = new Point3dArr(geom.getCoordRefDouble());
    Point3fArr color = new Point3fArr(geom.getColorRefFloat());
    for (int i = 0; i < color.size(); i++) {
      double z = coord.getZ(i);
      color.setX(i, (float) (0.5 * (1 + z)));
      color.setY(i, 0); //rand_.nextFloat();
      color.setZ(i, (float) (0.5 * (1 - z)));
    }
  }
  final protected void forceIntoBox(GeometryArray geom) {
    Point3dArr arr = new Point3dArr(geom.getCoordRefDouble());
    for (int i = 0; i < arr.size(); i++) {
      double v = arr.getX(i);
      if (v < -1)
        v += 1;
      if (v > 1)
        v -= 1;
      arr.setX(i, v);

      v = arr.getY(i);
      if (v < -1)
        v += 1;
      if (v > 1)
        v -= 1;
      arr.setY(i, v);

      v = arr.getZ(i);
      if (v < -1)
        v += 1;
      if (v > 1)
        v -= 1;
      arr.setZ(i, v);
    }
  }
}
