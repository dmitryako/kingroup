package javax.vecmathx;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 12:32:11 PM
 */
public class DuffLoopJUnit extends TestCase {
  private double eps_ = 1e-13;
  public static Test suite() {
    return new TestSuite(DuffLoopJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testDot() {
    double[] dv = {1., 0.5};
    double[] dv2 = {2., 0.5, 1.};
    valarray v = new valarray(dv);
    valarray v2 = new valarray(dv2);
    assertEquals(2.25, FastLoop.dot(v, v2), eps_);
    double[] dv3 = {1., 2., 3., 4., 5., 6., 7., 8.};
    double[] dv4 = {1.01, 2.01, 3.01, 4.01, 5.01, 6.01, 7.01, 8.01};
    v = new valarray(dv3);
    v2 = new valarray(dv4);
    assertEquals(204.36, FastLoop.dot(v, v2), eps_);
    double[] dv5 = {1., 2., 3., 4., 5., 6., 7., 8., 9.};
    double[] dv6 = {1.01, 2.01, 3.01, 4.01, 5.01, 6.01, 7.01, 8.01, 9.01};
    v = new valarray(dv5);
    v2 = new valarray(dv6);
    assertEquals(285.45, FastLoop.dot(v, v2), eps_);
  }
}
