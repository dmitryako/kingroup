package javax.utilx.arrays;
import javax.utilx.pair.IntPair;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2005, Time: 12:47:55
 */
public class IntPairArrays
{
  public static IntPair[] make(int size)
  {
    IntPair[] res = new IntPair[size];
    for (int i = 0; i < res.length; i++) {
      res[i] = new IntPair(0, 0);
    }
    return res;
  }
}
