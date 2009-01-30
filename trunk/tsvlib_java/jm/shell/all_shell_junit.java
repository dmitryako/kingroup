package jm.shell;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/03/2005, Time: 09:39:23
 */
public class all_shell_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit for jm.getShell.*");
//      suite.addTest(LagrrArrTest.suite());
//      suite.addTest(LagrrBasisTest.suite());
    return suite;
  }
}