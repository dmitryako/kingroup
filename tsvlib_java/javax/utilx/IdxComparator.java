package javax.utilx;
import java.util.Comparator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/03/2005, Time: 15:49:29
 */
public class IdxComparator implements Comparator {
  private final double[] src;
  private int retLess = -1;
  private int retGreater = 1;
  public IdxComparator(double[] from, boolean ascending) {
    src = from;
    if (!ascending) {
      retLess *= -1;
      retGreater *= -1;
    }
  }
  public int compare(Object obj, Object obj2) {
    Integer intObj = (Integer) obj;
    Integer intObj2 = (Integer) obj2;
    double d = src[intObj.intValue()];
    double d2 = src[intObj2.intValue()];
    if (d == d2)
      return 0;
    if (d < d2)
      return retLess;
    return retGreater;
  }
}
