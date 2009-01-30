package javax.utilx.bitset;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.BitSet;
import java.util.HashMap;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Oct 14, 2004, Time: 11:52:20 AM
 */
public class BitSetTest extends TestCase {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(BitSetTest.class);
  }
  protected void setUp() {
  }
  public void testHashtable() {
    BitSet bs = new BitSet(2);
    bs.set(1);
    bs.set(2); // {11000}
    BitSet bs2 = new BitSet(200);
    bs2.set(1);
    bs2.set(2); // {11000}

    // The HashMap class is roughly equivalent to Hashtable,
    // except that it is unsynchronized and permits nulls.
    HashMap map = new HashMap();
    map.put(bs, new String("{110}"));
    Object v = map.get(bs2);
    assertEquals(true, v != null);
  }
}
