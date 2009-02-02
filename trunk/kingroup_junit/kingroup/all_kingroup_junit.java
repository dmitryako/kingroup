package kingroup;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/

import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genetics.all_genetics_junit;
import kingroup.genotype.all_genotype_junit;
import kingroup.model.all_model_junit;

/**
 * Testing all classes in <code>kingroup.*</code>.
 */
public class all_kingroup_junit  {
   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      TestSuite suite = new TestSuite("All JUnit for kingroup.*");
      suite.addTest(all_genotype_junit.suite());
      suite.addTest(all_genetics_junit.suite());
      suite.addTest(all_model_junit.suite());
      return suite;
   }
}



