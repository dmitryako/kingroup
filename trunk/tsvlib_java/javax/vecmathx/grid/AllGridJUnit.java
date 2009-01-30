package javax.vecmathx.grid;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 8:33:30 AM
 */
public class AllGridJUnit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for this package");
    suite.addTest(StepGridJUnit.suite());
    return suite;
  }
}
