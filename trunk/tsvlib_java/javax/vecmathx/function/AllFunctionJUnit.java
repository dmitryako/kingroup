package javax.vecmathx.function;
import jm.laguerre.LagrrOrthonTest;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.vecmathx.function.chebyshev.Shift2ndChebyshevJUnit;
import javax.vecmathx.function.legendra.ShiftedLegendraJUnit;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 9:20:22 AM
 */
public class AllFunctionJUnit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for this package");
    suite.addTest(LagrrOrthonTest.suite());
    suite.addTest(ShiftedLegendraJUnit.suite());
    suite.addTest(Shift2ndChebyshevJUnit.suite());
    return suite;
  }
}