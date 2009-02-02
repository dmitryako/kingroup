package kingroup.genetics;

import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genotype.AlleleJUnit;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 26, 2004, Time: 10:31:16 AM
 */
/**
 * Testing all classes in <code>kingroup.genetics.*</code>.
 */
public class all_genetics_junit  {
   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      TestSuite suite = new TestSuite("All JUnit for kingroup.genetics.*");
      suite.addTest(AlleleJUnit.suite());
      suite.addTest(KAlleleFreqEqualTest.suite());
      suite.addTest(KAlleleFreqRandomTest.suite());
      suite.addTest(KAlleleFreqSmithEquifrequentTest.suite());
      suite.addTest(KAlleleFreqSmithNonequifrequentTest.suite());
      suite.addTest(AlleleFreqSmithMixedTest.suite());

      return suite;
   }
}



