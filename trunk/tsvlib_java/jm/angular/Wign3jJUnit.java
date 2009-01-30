package jm.angular;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/03/2005, Time: 08:31:50
 */
public class Wign3jJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(Wign3jJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testCalcClebsh() {
    //
    //  <j1, m1, j2, m2| jm> = (-)**(j1-j2+m) sqrt(2j+1) (j1 j2  j)
    //                                                   (m1 m2 -m)
    //  (j1  j1 0) = (-)**(j1-m1)   1/sqrt(2 * j1 + 1)
    //  (m1 -m1 0)
//      Data(CClebshH(new CClebsh(1, 1, 0,  0, 0, 0)), -1./sqrt(3.), EPS8, "CLEBSH(1,0,1, 0|0,0)")
    double res = Wign3j.calcClebsh(1, 1, 0, 0, 0, 0);
    assertEquals(0, Math.abs(-1. / Math.sqrt(3.) - res), 6e-23);
//      assertEquals(-1./Math.sqrt(3.), res, 6e-23);
    res = Wign3j.calcClebsh2L(2, 2, 0, 0, 0, 0);
    assertEquals(0, Math.abs(-1. / Math.sqrt(3.) - res), 6e-23);
//   ,  Data(CClebshH(new CClebsh(1, 1, 0, -1, 1, 0)),  1./sqrt(3.), EPS8, "CLEBSH(1,1,1,-1|0,0)")
    res = Wign3j.calcClebsh(1, 1, 0, -1, 1, 0);
    assertEquals(0, Math.abs(1. / Math.sqrt(3.) - res), 2e-16);
//      assertEquals(1./Math.sqrt(3.), res, 6e-20);
//   ,  Data(CClebshH(new CClebsh(1, 1, 0,  1,-1, 0)),  1./sqrt(3.), EPS8, "CLEBSH(1,1,1,-1|0,0)")
    res = Wign3j.calcClebsh(1, 1, 0, 1, -1, 0);
    assertEquals(0, Math.abs(1. / Math.sqrt(3.) - res), 2e-16);
//      assertEquals(1./Math.sqrt(3.), res, 6e-20);
//   ,  Data(CClebshH(new CClebsh(1, 0, 1,  0, 0, 0)), 1./sqrt(3.), EPS8, "CLEBSH(1,0,1, 0|0,0)")
    res = Wign3j.calcClebsh(1, 0, 1, 0, 0, 0);
//      assertEquals(1./Math.sqrt(3.), res, 6e-20);
    assertEquals(0, Math.abs(1. - res), 6e-23);
//   ,  Data(CClebshH(new CClebsh(20,20,0,  0, 0, 0)),  1./sqrt(2.*20+1), EPS8
//           , "CLEBSH(20,0,20,0|0,0)")
    res = Wign3j.calcClebsh(20, 20, 0, 0, 0, 0);
    assertEquals(0, Math.abs(1. / Math.sqrt(2. * 20 + 1) - res), 5e-16);
//      assertEquals(1./Math.sqrt(2.*20+1), res, 6e-20);
//
  }
}