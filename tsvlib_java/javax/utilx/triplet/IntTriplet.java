package javax.utilx.triplet;
import javax.utilx.pair.IntPair;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 15:31:42
 */
public class IntTriplet extends IntPair
{
  public int c;
  public IntTriplet(int a, int b, int c) {
    super(a, b);
    this.c = c;
  }
  public IntTriplet(IntTriplet from) {
    super(from);
    this.c = from.c;
  }

  public String toString() {
    return "(" + a + ", " + b + ", " + c + ")";
  }
}
