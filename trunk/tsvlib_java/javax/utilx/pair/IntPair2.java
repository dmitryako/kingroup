package javax.utilx.pair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/05/2006, Time: 09:54:21
 */
public class IntPair2
{
  public int a;
  public int b;
  public IntPair2(int a, int b) {
    this.a = a;
    this.b = b;
  }
  final public int pair(int v) {
    if (v == b)
      return a;
    if (v == a)
      return b;
    throw new RuntimeException("invalid input = " + v);
  }
  public String toString() {
    return "(" + a + ", " + b + ")";
  }
}
