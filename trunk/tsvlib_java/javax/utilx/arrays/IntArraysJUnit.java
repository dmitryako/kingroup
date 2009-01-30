package javax.utilx.arrays;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/03/2005, Time: 16:16:09
 */
public class IntArraysJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(IntArraysJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static void assertEquals(int[] test, int[] res) {
    for (int i = 0; i < test.length; i++) {
      assertEquals(test[i], res[i]);
    }
  }
}