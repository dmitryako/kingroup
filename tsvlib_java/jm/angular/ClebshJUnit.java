package jm.angular;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/03/2005, Time: 11:13:10
 */
public class ClebshJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(ClebshJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testDCLEBG() {
////  Orthonormality
//   ,  Data(CClebshH(new CClebshTestAllSum(1, 1, 1,  0, 0)),  1, EPS8, "Sum_mm' C(1m,1m'|10) * C(1m,1m'|10)")
    double res = Clebsh.calcAllSum(1, 1, 1, 0, 0);
    assertEquals(0, Math.abs(1 - res), 8e-16);
//   ,  Data(CClebshH(new CClebshTestAllSum(1, 1, 1,  0, 1)),  0, EPS8, "Sum_mm' C(1m,1m'|10) * C(1m,1m'|11)")
    res = Clebsh.calcAllSum(1, 1, 1, 0, 1);
    assertEquals(0, Math.abs(0 - res), 8e-86);
//   ,  Data(CClebshH(new CClebshTestAllSum(1, 1, 1,  1, 1)),  1, EPS8, "Sum_mm' C(1m,1m'|11) * C(1m,1m'|11)")
    res = Clebsh.calcAllSum(1, 1, 1, 1, 1);
    assertEquals(0, Math.abs(1 - res), 8e-16);
//   ,  Data(CClebshH(new CClebshTestSum(20,20, 9, 8)),  1, EPS8, "Sum_mm' C(20m,20m'|98)^2")
    res = Clebsh.calcSum(20, 20, 9, 8);
    assertEquals(0, Math.abs(1 - res), 4e-15);
  }
}