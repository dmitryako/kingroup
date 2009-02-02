package kingroup_v2.like.milligan;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 15:34:06
 */
public class MilliganIBD
{
  public final static int K_0 = 8;
  public final static int K_1 = 7;
  public final static int K_2 = 6;
  private static ProjectLogger log = ProjectLogger.getLogger(MilliganIBD.class.getName());
  public static final int SIZE = 9;
  private double[] s = Vec.makeArray(0.0, SIZE);
  private double[] cum; // cumulative

  public double[] getArr() { return s;}

  public double[] getCum() {
    if (cum == null) {
      cum = Vec.makeCumulative(s);

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
    s[K_0] = 0.25; // full-sibs
    s[K_1] = 0.5;
    s[K_2] = 0.25;
  }
  final public void loadNullDefault() {
    s[K_0] = 1;
    s[K_1] = 0;
    s[K_2] = 0;
  }

  public double get(int i)
  {
    return s[i];
  }
  public void set(int i, double v)
  {
    s[i] = v;
  }
  public int size() {return SIZE;}
  public boolean equals(MilliganIBD ibd) {
    if (this == ibd)
      return true;
    return Vec.floatEquals(s, ibd.s);
  }
}
