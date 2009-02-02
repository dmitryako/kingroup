package kingroup_v2.partition.simpson;
import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/05/2005, Time: 12:50:14
 */
public class all_simpson_junit {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    TestSuite suite = new TestSuite("All JUnit's for kingroup.partition.simpson");
    suite.addTest(SysPopSampleTest.suite());
    suite.addTest(SimpsPopFactoryTest.suite());
    suite.addTest(SIMPSAlg2Test.suite());
    return suite;
  }
}