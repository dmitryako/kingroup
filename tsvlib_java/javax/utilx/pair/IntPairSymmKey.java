package javax.utilx.pair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/05/2005, Time: 08:36:03
 */
public class IntPairSymmKey extends IntPair implements Comparable {
  public IntPairSymmKey(int a, int b) {
    super(a, b);
  }
  public IntPairSymmKey(IntPairSymmKey from) {
    super(from);
  }
  public int getKeyPlus() {
    return a + b;
  }
  public int getKeyMinus() {
    return Math.abs(a - b);
  }
  // used as a symmentric key
  public int compareTo(Object obj) {
    IntPairSymmKey p = (IntPairSymmKey) obj;
    int keyP = getKeyPlus() - p.getKeyPlus();
    int keyM = getKeyMinus() - p.getKeyMinus();
    if (keyP == 0 && keyM == 0)
      return 0;
    if (keyP == 0)
      return keyM;
    return keyP;
  }
}
