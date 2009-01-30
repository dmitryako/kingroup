package jm.laguerre;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/03/2005, Time: 08:46:27
 */
public class all_laguerre_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for jm.laguerre.*");
    suite.addTest(LagrrArrTest.suite());
    suite.addTest(LagrrOrthonTest.suite());
    suite.addTest(LaguerreLogRJUnit.suite());
    suite.addTest(LaguerreLogCRJUnit.suite());
    return suite;
  }
}