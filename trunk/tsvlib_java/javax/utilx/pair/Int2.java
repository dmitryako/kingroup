package javax.utilx.pair;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 21, 2004, Time: 4:46:30 PM
 */
public class Int2 {
  public int a;
  public int b;
  public Int2(int a, int b) {
    this.a = a;
    this.b = b;
  }
  public Int2(Int2 from) {
    this.a = from.a;
    this.b = from.b;
  }
//  public boolean hasOneCommon(Int2 tmp)
//  {
//    return (tmp.a == a
//      || tmp.a == b
//      || tmp.b == a
//      || tmp.b == b);
//  }
  final public int pair(int res) {
    return (res == b) ? a : b;
  }
  public String toString() {
    return "(" + a + ", " + b + ")";
  }

//  public static Int2 makeComplement(Int2 p, Int2 p2)
//  {
//    // must be a triangle: a, b, c
//    if (p.a == p2.a)
//     return new Int2(p.b,  p2.b);
//    if (p.a == p2.b)
//     return new Int2(p.b,  p2.a);
//    if (p.b == p2.a)
//     return new Int2(p.a,  p2.b);
//    if (p.b == p2.b)
//     return new Int2(p.a,  p2.a);
//    return null;
//  }
}
