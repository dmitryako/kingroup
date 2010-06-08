package javax.utilx.triplet;
import javax.utilx.pair.Int2;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 15:31:42
 */
public class Int3 extends Int2
{
  public int c;
  public Int3(int a, int b, int c) {
    super(a, b);
    this.c = c;
  }
  public Int3(Int3 from) {
    super(from);
    this.c = from.c;
  }

  public String toString() {
    return "(" + a + ", " + b + ", " + c + ")";
  }
}
