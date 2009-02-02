package kingroup.genetics;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/

import junit.framework.*;
import kingroup.genotype.AlleleJUnit;

/**
 * Testing all classes in <code>kingroup.genetics.*</code>.
 *
 */
public class KAllGeneticsTests  {
   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      TestSuite suite = new TestSuite("All JUnit geometryx for kingroup.genetics.*");
      suite.addTest(AlleleJUnit.suite());
      //suite.addTest(KGenotypeGroupDataTest.suite());
      suite.addTest(LikeDiploidTest.suite());
      suite.addTest(LikeHaploDipJUnit.suite());
      suite.addTest(KKinshipLhHypothesisTest.suite());
      return suite;
   }
}



