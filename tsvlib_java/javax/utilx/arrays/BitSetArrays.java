package javax.utilx.arrays;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2005, Time: 12:42:20
 */
public class BitSetArrays
{
  public static BitSet[] make(int size)
  {
    BitSet[] res = new BitSet[size];
    for (int i = 0; i < res.length; i++) {
      res[i] = new BitSet();
    }
    return res;
  }
}
