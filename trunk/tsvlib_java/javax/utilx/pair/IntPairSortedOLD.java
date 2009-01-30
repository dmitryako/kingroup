package javax.utilx.pair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/06/2005, Time: 10:55:56
 */
public class IntPairSortedOLD {
  final public int a;    // ordered: a<=b
  final public int b;
  public IntPairSortedOLD(int a, int b) {
    if (a < b) {
      this.a = a;
      this.b = b;
    } else {
      this.a = b;
      this.b = a;
    }
  }

  public IntPairSortedOLD(IntPair2 pair)
  {
    this(pair.a, pair.b);
  }
}
