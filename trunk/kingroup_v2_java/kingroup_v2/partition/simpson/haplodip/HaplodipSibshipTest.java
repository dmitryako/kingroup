package kingroup_v2.partition.simpson.haplodip;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.utilx.pair.IntPair;
import javax.utilx.pair.IntPairSymmKey;
import java.util.LinkedList;
import java.util.TreeSet;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 16:52:15
 */
public class HaplodipSibshipTest extends TestCase {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(HaplodipSibshipTest.class);
  }
  public void testMakeParents_40() {
    LinkedList pA = new LinkedList();
    LinkedList pB = new LinkedList();
    pA.add(new Integer(0));
    pB.add(new IntPair(1, -1));
    IntPair newPair = new IntPair(4, 0);
    assertEquals(true, HaplodipSibship.makeParents(newPair, pA, pB));
  }
  public void testMakeParents_41() {
    LinkedList pA = new LinkedList();
    LinkedList pB = new LinkedList();
    pA.add(new Integer(0));
    pB.add(new IntPair(1, -1));
    IntPair newPair = new IntPair(4, 1);
    assertEquals(false, HaplodipSibship.makeParents(newPair, pA, pB));
  }
  public void testMakeParents_00() {
    LinkedList pA = new LinkedList();
    LinkedList pB = new LinkedList();
    pA.add(new Integer(0));
    pB.add(new IntPair(1, -1));
    IntPair newPair = new IntPair(0, 0);
    assertEquals(true, HaplodipSibship.makeParents(newPair, pA, pB));
  }
  public void testMakeParents2_f() {
    TreeSet locusSet = new TreeSet();
    locusSet.add(new IntPairSymmKey(6, 6));
    locusSet.add(new IntPairSymmKey(0, 6));
    locusSet.add(new IntPairSymmKey(6, 2));
    locusSet.add(new IntPairSymmKey(6, 6));
    assertEquals(false, HaplodipSibship.hasParentsSLOW(locusSet));
  }
  public void testMakeParents2_t() {
    TreeSet locusSet = new TreeSet();
    locusSet.add(new IntPairSymmKey(0, 6));
    locusSet.add(new IntPairSymmKey(6, 2));
    assertEquals(true, HaplodipSibship.hasParentsSLOW(locusSet));
  }
  public void testMakeParents3() {
    TreeSet locusSet = new TreeSet();
    locusSet.add(new IntPairSymmKey(5, 6));
    locusSet.add(new IntPairSymmKey(5, 3));
    locusSet.add(new IntPairSymmKey(5, 6));
    locusSet.add(new IntPairSymmKey(6, 3));
    locusSet.add(new IntPairSymmKey(6, 6));
    locusSet.add(new IntPairSymmKey(3, 6));
    assertEquals(false, HaplodipSibship.hasParentsSLOW(locusSet));
  }
  public void testMakeParents3_t() {
    TreeSet locusSet = new TreeSet();
    locusSet.add(new IntPairSymmKey(5, 6));
    locusSet.add(new IntPairSymmKey(5, 6));
    locusSet.add(new IntPairSymmKey(6, 3));
    locusSet.add(new IntPairSymmKey(3, 6));
    assertEquals(true, HaplodipSibship.hasParentsSLOW(locusSet));
  }
}