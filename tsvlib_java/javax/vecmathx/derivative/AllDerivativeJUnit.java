package javax.vecmathx.derivative;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 3:22:16 PM
 */
public class AllDerivativeJUnit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for this package");
    suite.addTest(DerivPts3JUnit.suite());
    suite.addTest(DerivPts5JUnit.suite());
    suite.addTest(DerivPts9JUnit.suite());
    return suite;
  }
}
