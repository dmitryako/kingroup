package javax.utilx.arrays;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/03/2005, Time: 16:10:16
 */
public class DoubleArraysJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(DoubleArraysJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testSortIdx() {
    LOG.setTrace(true);
    double[] v = {1, 0, 1.5};
    int[] test = {1, 0, 2};
    int[] res = Vec.sortIdx(v);
    IntArraysJUnit.assertEquals(test, res);
  }
}