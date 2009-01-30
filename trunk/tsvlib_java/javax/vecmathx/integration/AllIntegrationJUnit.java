package javax.vecmathx.integration;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 3:23:12 PM
 */
public class AllIntegrationJUnit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for this package");
    suite.addTest(BooleQuadrJUnit.suite());
    return suite;
  }
}
