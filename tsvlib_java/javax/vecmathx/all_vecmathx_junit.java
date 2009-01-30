package javax.vecmathx;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.vecmathx.derivative.AllDerivativeJUnit;
import javax.vecmathx.function.AllFunctionJUnit;
import javax.vecmathx.grid.AllGridJUnit;
import javax.vecmathx.integration.AllIntegrationJUnit;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 3:20:48 PM
 */
public class all_vecmathx_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for this package");
    suite.addTest(AllIntegrationJUnit.suite());
    suite.addTest(AllDerivativeJUnit.suite());
    suite.addTest(AllFunctionJUnit.suite());
    suite.addTest(AllGridJUnit.suite());
    return suite;
  }
}
