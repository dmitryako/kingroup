package kingroup.genetics;

import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genotype.Locus;
import kingroup.population.SmithPopBuilderModel;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 26, 2004, Time: 11:48:59 AM
 */
/**
 * Testing <code>kingroup.genetics.AlleleFreqSmithNonequal</code>.
 */
public class KAlleleFreqSmithNonequifrequentTest extends KAlleleFreqEqualTest {

   public static void main(String[] args) {
       junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
       return new TestSuite(KAlleleFreqSmithNonequifrequentTest.class);
   }
   public void testKAlleleFreqSmithNonequifrequent() {
      SmithPopBuilderModel model = new kingroup.population.SmithPopBuilderModel();
      nl_ = 2;
      na_ = 3;
      loadModelAttributes(model);
      AlleleFreqSmithNonequal freq = new AlleleFreqSmithNonequal(model, 1);
      assertNonEqualFreqs(1.0, freq, 0, nl_ - 1);

      freq.normalize(1.0f, false);
      double scale = 2.0 / (na_ * (na_ + 1));
      assertNonEqualFreqs(scale, freq, 0, nl_ - 1);
   }

   public void testLoadNonequalProbs() {
      kingroup.population.SmithPopBuilderModel model = new kingroup.population.SmithPopBuilderModel();
      nl_ = 4;
      loadModelAttributes(model);
      AlleleFreqSmithNonequal freq = new AlleleFreqSmithNonequal(model, 1);
      freq.initProb();

      int start = nl_ - 2;
      int end = nl_ - 1;
      freq.loadNonequalProbs(start, end, 1);
      assertNonEqualFreqs(1.0, freq, start, end);
      assertNonEqualFreqs(0.0, freq, 0, start - 1);
   }
   protected void assertNonEqualFreqs(double scale, AlleleFreqEqual freq
                                 , int idxFirstLocus, int idxLastLocus) {
      for (int L = idxFirstLocus; L <= idxLastLocus; L++)  {
         Locus locus = freq.getLocus(L);
         for (int allele = 0; allele < locus.size(); allele++)
            assertEquals(scale * (allele + 1), locus.get(allele).getProb(), delta_);
      }
   }
}
