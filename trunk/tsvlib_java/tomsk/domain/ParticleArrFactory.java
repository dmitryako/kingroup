package tomsk.domain;
import javax.utilx.RandomSeed;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/04/2007, 12:17:26
 */
public class ParticleArrFactory
{
  public static Point3dArr makeRandom(int size) {
    Point3dArr res = new Point3dArr(size);
    RandomSeed rand = RandomSeed.getInstance();
    for (int i = 0; i < res.size(); i++) {
      res.setX(i, rand.nextDouble(-1., 1));
      res.setY(i, rand.nextDouble(-1., 1));
      res.setZ(i, rand.nextDouble(-1., 1));
    }
    return res;
  }
}
