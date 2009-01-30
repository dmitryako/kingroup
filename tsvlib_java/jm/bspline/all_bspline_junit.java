package jm.bspline;
import jm.bspline.junit.BSPLVBJUnit;
import jm.bspline.junit.BSplineArrJUnit;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/04/2005, Time: 11:18:24
 */
public class all_bspline_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for jm.bspline.*");
    suite.addTest(BSplineArrJUnit.suite());
    suite.addTest(BSPLVBJUnit.suite());
    suite.addTest(BSplineArrJUnit.suite());
    return suite;
  }
}