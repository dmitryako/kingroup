package javax.utilx.pair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/10/2005, Time: 12:00:14
 */
public class FloatPair // implements Comparable
{
  protected float a;
  protected float b;
  public FloatPair(float a, float b) {
    this.a = a;
    this.b = b;
  }
  public FloatPair() {
  }
  public final void setA(float a) {this.a = a;}
  public final void setB(float b) {this.b = b;}
  public final float getA() {return a;}
  public final float getB() {return b;}

//  public int compareTo(Object obj)
//  {
//    if (this == obj)
//      return 0;
//    FloatPair pair = (FloatPair)obj;
//    if (pair.a == a  &&  pair.b == b)
//      return 0;
//    if (pair.a == a)
//      return Float.compare(pair.b,  b);
//    return Float.compare(pair.a,  a);
//  }
}
