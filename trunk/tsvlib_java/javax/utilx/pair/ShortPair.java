package javax.utilx.pair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/05/2005, Time: 10:17:30
 */
public class ShortPair {
  final public short a;
  final public short b;
  public ShortPair(short a, short b) {
    this.a = a;
    this.b = b;
  }
  final public int pair(short res) {
    assert(res == a || res == b);
    return (res == b) ? a : b;
  }
  final public String toString() {
    return "(" + a + ", " + b + ")";
  }
}
