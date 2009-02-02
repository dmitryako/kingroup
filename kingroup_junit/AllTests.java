
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.*;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 26, 2004, Time: 11:18:15 AM
 */


/**
 * Testing all classes in <code>org.*</code>.
 */
public class AllTests  {
   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      TestSuite suite = new TestSuite("All JUnit geometryx for org.*");
      suite.addTest(AllJKinshipTests.suite());
      suite.addTest(all_kingroup_junit.suite());
      return suite;
   }
}




