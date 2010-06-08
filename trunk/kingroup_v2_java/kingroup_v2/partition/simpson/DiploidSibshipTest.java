package kingroup_v2.partition.simpson;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.utilx.pair.Int2;
import javax.utilx.pair.IntPairSymmKey;
import java.util.LinkedList;
import java.util.TreeSet;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/05/2005, Time: 10:33:36
 */
public class DiploidSibshipTest extends TestCase {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(DiploidSibshipTest.class);
  }
  public void testMakeParents() {
    LinkedList pA = new LinkedList();
    LinkedList pB = new LinkedList();
    pA.add(new Int2(0, -1));
    pB.add(new Int2(1, -1));
    Int2 newPair = new Int2(4, 1);
    assertEquals(true, DiploidSibship.makeParents(newPair, pA, pB));
  }
  public void testMakeParents2() {
    TreeSet locusSet = new TreeSet();
    locusSet.add(new IntPairSymmKey(6, 6));
    locusSet.add(new IntPairSymmKey(0, 6));
    locusSet.add(new IntPairSymmKey(6, 2));
    locusSet.add(new IntPairSymmKey(6, 6));
    assertEquals(true, DiploidSibship.hasParentsSLOW(locusSet));
  }
  public void testMakeParents3() {
    TreeSet locusSet = new TreeSet();
    locusSet.add(new IntPairSymmKey(5, 6));
    locusSet.add(new IntPairSymmKey(5, 3));
    locusSet.add(new IntPairSymmKey(5, 6));
    locusSet.add(new IntPairSymmKey(6, 3));
    locusSet.add(new IntPairSymmKey(6, 6));
    locusSet.add(new IntPairSymmKey(3, 6));
    assertEquals(true, DiploidSibship.hasParentsSLOW(locusSet));
  }
}