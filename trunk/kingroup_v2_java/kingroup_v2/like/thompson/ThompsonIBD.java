package kingroup_v2.like.thompson;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 10:34:40
 */
public class ThompsonIBD
{
  private static ProjectLogger log = ProjectLogger.getLogger(ThompsonIBD.class.getName());
  public final static int K_0 = 0;
  public final static int K_1 = 1;
  public final static int K_2 = 2;
  public static final int SIZE = 3;
  private double[] k = Vec.makeArray(0.0, SIZE);
  private double[] cum; // cumulative
  public double[] getArr() { return k;}

  public double[] getCum() {
    if (cum == null) {
      cum = Vec.makeCumulative(k);

      int n = cum.length;
      if ((float)cum[n-1] != 1f)
      {
        String error = "Types/Modes of identity-by-descent must be normalized to 1. Current norm="
          + (float)cum[n-1];
        log.severe(error);
        throw new RuntimeException(error);
      }
    }
    return cum;
  }

  final public void loadPrimDefault() {
    k[K_0] = 0.25; // full-sibs
    k[K_1] = 0.5;
    k[K_2] = 0.25;
  }
  final public void loadNullDefault() {
    k[K_0] = 1;
    k[K_1] = 0;
    k[K_2] = 0;
  }

  public double get(int i)
  {
    return k[i];
  }
  public void set(int i, double v)
  {
    k[i] = v;
  }
  public int size() {return SIZE;}
  public boolean equals(ThompsonIBD ibd) {
//    public boolean equals(Object ibd) {
    if (this == ibd)
      return true;
//    return DoubleArr.equals(k, ((ThompsonIBD)ibd).k);
    return Vec.floatEquals(k, ibd.k);
  }
}
