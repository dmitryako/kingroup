package kingroup;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/

import kingroup.model.*;
import kingroup.genetics.*;
import kingroup.util.KAllUtilTests;

import junit.framework.*;

/**
 * Testing all classes in <code>kingroup.kinship.*</code>.
 *
 */
public class AllJKinshipTests  {
   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      TestSuite suite = new TestSuite("All JUnit geometryx for kingroup.kinship.*");
      suite.addTest(KAllGeneticsTests.suite());
      suite.addTest(KAllModelTests.suite());
      suite.addTest(KAllUtilTests.suite());
      return suite;
   }
}



