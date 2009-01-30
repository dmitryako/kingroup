package jm.angular;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/03/2005, Time: 13:05:29
 */
public class Wign6jJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(Wign6jJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testCalc() {
    //      // from Landau p.522
    //      CWign6jH abc0(new CWign6j( a, b, c
    //                              , x0, c, b));
    //      double abc0_res = abc0->calc_abc0();
    double abc0 = Wign6j.calc(1, 1, 1
      , 0, 1, 1);
    double abc0_res = Wign6j.calc_abc0(1, 1, 1);
    //      CWign6jH abc0_2(new CWign6j( 0.5, 0.5,   1
    //                              ,    0,   1, 0.5));
    double abc0_2 = Wign6j.calc(0.5f, 0.5f, 1
      , 0, 1, 0.5f);
    //      double abc0_2_res = abc0_2->calc_abc0();
    double abc0_2_res = Wign6j.calc_abc0(0.5f, 0.5f, 1);
    //      job6j.addLine(abc0,   abc0_res,   "abc0",   3e-16);
    assertEquals(0, Math.abs(abc0 - abc0_res), 3e-16);
    //      job6j.addLine(abc0_2, abc0_2_res, "abc0_2", 3e-16);
    assertEquals(0, Math.abs(abc0_2 - abc0_2_res), 2e-16);
    //      job6j.addLine(CWign6jH(new CWign6j( 0.5, 0.5,   1
    //                                    ,   1,   1, 0.5)), -1./3, "wign6j #1", 2e-16);
    double res = Wign6j.calc(0.5f, 0.5f, 1
      , 1, 1, 0.5f);
    assertEquals(0, Math.abs(-1. / 3 - res), 6e-17);
    //      job6j.addLine(CWign6jH(new CWign6j( 0.5, 0.5,   1
    //                                    , 0.5,   1, 0.5)), 0, "wign6j #2", EPS18);
    res = Wign6j.calc(0.5f, 0.5f, 1
      , 0.5f, 1, 0.5f);
    assertEquals(0, Math.abs(0 - res), 2e-26);
    //      job6j.addLine(CWign6jH(new CWign6j(   1, 0.5,  1.5
    //                                    , 0.5,   1,    1)), -1./6, "wign6j #3", 9e-17);
    //      //                            , 0.5,   1,    1)), -1./6, "wign6j #3", 6e-17);
    res = Wign6j.calc(1, 0.5f, 1.5f
      , 0.5f, 1, 1);
    assertEquals(0, Math.abs(-1. / 6 - res), 6e-17);
  }
}