package jm.angular;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/03/2005, Time: 09:38:26
 */
public class all_angular_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for jm.angular.*");
    suite.addTest(Wign3jJUnit.suite());
    return suite;
  }
}