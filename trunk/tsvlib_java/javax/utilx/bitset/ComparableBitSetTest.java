package javax.utilx.bitset;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 14, 2004, Time: 5:17:24 PM
 */
public class ComparableBitSetTest extends TestCase {
  protected CompBitSet bs000 = new CompBitSet();
  protected CompBitSet bs100 = new CompBitSet();
  protected CompBitSet bs010 = new CompBitSet();
  protected CompBitSet bs001 = new CompBitSet();
  protected CompBitSet bs011 = new CompBitSet();
  protected CompBitSet bs110 = new CompBitSet();
  protected CompBitSet bs101 = new CompBitSet();
  protected CompBitSet bs111 = new CompBitSet();
  protected CompBitSet bs0001 = new CompBitSet();
  protected CompBitSet bs0011 = new CompBitSet();
  protected CompBitSet bs0101 = new CompBitSet();
  protected CompBitSet bs1001 = new CompBitSet();
  protected CompBitSet bs0111 = new CompBitSet();
  protected CompBitSet bs1011 = new CompBitSet();
  protected CompBitSet bs1101 = new CompBitSet();
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(ComparableBitSetTest.class);
  }
  protected void setUp() {
    setUp3();
    setUp4();
  }
  private void setUp3() {
    bs100.set(0);
    bs010.set(1);
    bs001.set(2);
    bs110.set(0, 2); // {110}
    bs011.set(1, 3);
    bs101.set(0);
    bs101.set(2);
    bs111.set(0, 3);
  }
  private void setUp4() {
    bs0001.set(3);
    bs0011.set(2, 4);
    bs0101.set(1);
    bs0101.set(3);
    bs1001.set(0);
    bs1001.set(3);
    bs0111.set(1, 4);
    bs1011.set(0, 4);
    bs1011.set(1, false);
    bs1101.set(0, 4);
    bs1101.set(2, false);
  }
  public void testCompareTo() {
    assertEquals(true, bs001.compareTo(bs110) > 0);
    assertEquals(true, bs110.compareTo(bs001) < 0);
    assertEquals(true, bs100.compareTo(bs100) == 0);
    assertEquals(true, bs100.compareTo(bs010) < 0);
    assertEquals(true, bs100.compareTo(bs001) < 0);
    assertEquals(true, bs101.compareTo(bs001) > 0);
    assertEquals(true, bs111.compareTo(bs001) > 0);
    assertEquals(true, bs111.compareTo(bs011) > 0);
    assertEquals(true, bs100.compareTo(bs0101) < 0);
    assertEquals(true, bs0111.compareTo(bs0101) > 0);
    assertEquals(true, bs1101.compareTo(bs1011) < 0);
  }
  public void testSwapFirstDifferentSetBit() {
    CompBitSet a = (CompBitSet) bs100.clone();
    CompBitSet b = (CompBitSet) bs001.clone();
    a.swapFirstDifferentSetBit(b);
    assertEquals(bs001, a);
    assertEquals(bs100, b);
    a = (CompBitSet) bs101.clone();
    b = (CompBitSet) bs001.clone();
    a.swapFirstDifferentSetBit(b);
    assertEquals(bs001, a);
    assertEquals(bs100, b);
    a = (CompBitSet) bs110.clone();
    b = (CompBitSet) bs001.clone();
    a.swapFirstDifferentSetBit(b);
    assertEquals(bs011, a);
    assertEquals(bs100, b);
    a = (CompBitSet) bs000.clone();
    b = (CompBitSet) bs001.clone();
    a.swapFirstDifferentSetBit(b);
    assertEquals(bs000, a);
    assertEquals(bs001, b);
    a = (CompBitSet) bs011.clone();
    b = (CompBitSet) bs011.clone();
    a.swapFirstDifferentSetBit(b);
    assertEquals(bs011, a);
    assertEquals(bs011, b);
  }
}
