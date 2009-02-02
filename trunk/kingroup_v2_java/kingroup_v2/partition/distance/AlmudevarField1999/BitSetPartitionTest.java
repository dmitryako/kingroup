package kingroup_v2.partition.distance.AlmudevarField1999;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionPair;

import javax.utilx.bitset.ComparableBitSetTest;
import javax.utilx.pair.BitSetPair;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 14, 2004, Time: 2:53:51 PM
 */
public class BitSetPartitionTest extends ComparableBitSetTest {
  int UNIVERSE_SIZE = 3;
  Partition a_b_c = new Partition(UNIVERSE_SIZE);
  Partition a_b = new Partition(UNIVERSE_SIZE);
  Partition b_c = new Partition(UNIVERSE_SIZE);
  Partition ab_c = new Partition(UNIVERSE_SIZE);
  Partition a_bc = new Partition(UNIVERSE_SIZE);
  Partition ac_b = new Partition(UNIVERSE_SIZE);
  Partition a = new Partition(UNIVERSE_SIZE);
  Partition ab = new Partition(UNIVERSE_SIZE);
  Partition abc = new Partition(UNIVERSE_SIZE);
  Partition empty = new Partition(UNIVERSE_SIZE);
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(BitSetPartitionTest.class);
  }
  protected void setUp() {
    super.setUp();
    a_b_c.add(bs100);
    a_b_c.add(bs010);
    a_b_c.add(bs001);
    a_b.add(bs100);
    a_b.add(bs010);
    b_c.add(bs010);
    b_c.add(bs001);
    ab_c.add(bs110);
    ab_c.add(bs001);
    a_bc.add(bs100);
    a_bc.add(bs011);
    ac_b.add(bs101);
    ac_b.add(bs010);
    a.add(bs100);
    ab.add(bs110);
    abc.add(bs111);
  }
  public void testRemoveAllCommon() {
    PartitionPair res = a_b_c.removeAllCommon(ab_c);
    assertEquals(a_b, res.a);
    assertEquals(ab, res.b);
    res = ab_c.removeAllCommon(a_b_c);
    assertEquals(ab, res.a);
    assertEquals(a_b, res.b);
    res = abc.removeAllCommon(a_b_c);
    assertEquals(abc, res.a);
    assertEquals(a_b_c, res.b);
    res = abc.removeAllCommon(abc);
    assertEquals(empty, res.a);
    assertEquals(empty, res.b);
  }
  public void testFindAllDifferences() {
    String strA_B_C = a_b_c.toString();
    String strAB_C = ab_c.toString();
    BitSetPair res = a_b_c.findAllDifferences(ab_c);
    // {100, 010, 001}
    // {110, 001}
    assertEquals(bs100, res.a); // and
    assertEquals(bs010, res.b); // xor
    res = ab_c.findAllDifferences(a_b_c);
    // {110, 001}
    // {100, 010, 001}
    assertEquals(bs100, res.a); // and
    assertEquals(bs010, res.b); // xor
    res = ab_c.findAllDifferences(ab_c);
    // {110, 001}
    assertEquals(bs110, res.a); // and
    assertEquals(bs000, res.b); // xor
    res = abc.findAllDifferences(ab_c);
    // {111}
    // {110, 001}
    assertEquals(bs110, res.a); // and
    assertEquals(bs001, res.b); // xor
  }
  public void testSubtract() {
    Partition res = a_b_c.subtract(bs100);  // {100, 010, 001} - {100}
    assertEquals(b_c, res); //{000, 010, 001}
    res = a_b_c.subtract(bs001);   // {100, 010, 001} - {001}
    assertEquals(a_b, res);
    res = ab_c.subtract(bs001);   // {110, 001} - {001}
    assertEquals(ab, res);
    res = abc.subtract(bs001);   // {111} - {001}
    assertEquals(ab, res);
    res = a_b_c.subtract(bs011);    // {100, 010, 001} - {011}
    assertEquals(a, res);
  }
}
