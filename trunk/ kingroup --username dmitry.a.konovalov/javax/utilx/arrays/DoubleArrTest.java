package javax.utilx.arrays;
import junit.framework.TestCase;

import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/03/2006, Time: 11:03:26
 */
public class DoubleArrTest extends TestCase
{
  private static final double EPS = 1e-10;

  public void testMakeCumulative()
  {
    int idx = 0;
    double[] cum = Vec.makeCumulative(new double[]{1, 2, 3});
    assertEquals(1, cum[idx++], EPS);
    assertEquals(3, cum[idx++], EPS);
    assertEquals(6, cum[idx++], EPS);
  }

  public void testFindBinL()
  {
    double[] a;
    int idx;

    a = new double[]{0.5};
    idx = Vec.findBinL(a, 0.1);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.5);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.6);
    assertEquals(0, idx);

    a = new double[]{0.5, 2};
    idx = Vec.findBinL(a, 0.1);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.5);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.6);
    assertEquals(1, idx);
    idx = Vec.findBinL(a, 2);
    assertEquals(1, idx);
    idx = Vec.findBinL(a, 2.6);
    assertEquals(1, idx);

    a = new double[]{0.5, 2, 2.9};
    idx = Vec.findBinL(a, 0.1);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.5);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.6);
    assertEquals(1, idx);
    idx = Vec.findBinL(a, 2);
    assertEquals(1, idx);
    idx = Vec.findBinL(a, 2.6);
    assertEquals(2, idx);
    idx = Vec.findBinL(a, 2.9);
    assertEquals(2, idx);
    idx = Vec.findBinL(a, 3);
    assertEquals(2, idx);

    a = new double[]{0.5, 2, 2.9, 3};
    idx = Vec.findBinL(a, 0.1);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.5);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.6);
    assertEquals(1, idx);
    idx = Vec.findBinL(a, 2);
    assertEquals(1, idx);
    idx = Vec.findBinL(a, 2.6);
    assertEquals(2, idx);
    idx = Vec.findBinL(a, 2.9);
    assertEquals(2, idx);
    idx = Vec.findBinL(a, 2.95);
    assertEquals(3, idx);
    idx = Vec.findBinL(a, 3);
    assertEquals(3, idx);
    idx = Vec.findBinL(a, 4);
    assertEquals(3, idx);

    a = new double[]{0.5, 2, 2.9, 3, 4};
    idx = Vec.findBinL(a, 0.1);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.5);
    assertEquals(0, idx);
    idx = Vec.findBinL(a, 0.6);
    assertEquals(1, idx);
    idx = Vec.findBinL(a, 2);
    assertEquals(1, idx);
    idx = Vec.findBinL(a, 2.6);
    assertEquals(2, idx);
    idx = Vec.findBinL(a, 2.9);
    assertEquals(2, idx);
    idx = Vec.findBinL(a, 2.95);
    assertEquals(3, idx);
    idx = Vec.findBinL(a, 3);
    assertEquals(3, idx);
    idx = Vec.findBinL(a, 3.5);
    assertEquals(4, idx);
    idx = Vec.findBinL(a, 4);
    assertEquals(4, idx);
    idx = Vec.findBinL(a, 4.4);
    assertEquals(4, idx);

  }

}
