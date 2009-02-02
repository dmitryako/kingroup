package kingroup.genetics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.genotype.Locus;
import kingroup.population.SmithPopBuilderModel;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 26, 2004, Time: 10:30:50 AM
 */
/**
 * Testing <code>kingroup.genetics.AlleleFreqEqual</code>.
 */
public class KAlleleFreqEqualTest extends TestCase {
   static protected int na_ = 2; // number of alleles
   static protected int nl_ = 2; // number of loci
   static protected double delta_ = 1e-7f;

   public static void main(String[] args) {
       junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
       return new TestSuite(KAlleleFreqEqualTest.class);
   }
   public void testKAlleleFreqEqual() {
      SmithPopBuilderModel model = new kingroup.population.SmithPopBuilderModel();
      nl_ = 2;
      na_ = 3;
      loadModelAttributes(model);
      AlleleFreqEqual freq = new AlleleFreqEqual(model);
      assertEqualFreqs(1.0, freq, 0, nl_ - 1);

      freq.normalize(1, false);
      double prob = 1.0 / na_;
      assertEqualFreqs(prob, freq, 0, nl_ - 1);
   }

   public void testLoadEqualProbs() {
      kingroup.population.SmithPopBuilderModel model = new kingroup.population.SmithPopBuilderModel();
      nl_ = 4;
      loadModelAttributes(model);
      AlleleFreqEqual freq = new AlleleFreqEqual(model);
      freq.initProb();

      int start = nl_ - 2;
      int end = nl_ - 1;
      freq.loadEqualProbs(start, end);
      assertEqualFreqs(1.0, freq, start, end);
      assertEqualFreqs(0.0, freq, 0, start - 1);
   }
   protected void loadModelAttributes(kingroup.population.SmithPopBuilderModel model) {
      model.setNumAlleles(na_);
      model.setNumLoci(nl_);
   }
   protected void assertEqualFreqs(double prob, OldAlleleFreq freq
                                 , int idxFirstLocus, int idxLastLocus) {
      for (int L = idxFirstLocus; L <= idxLastLocus; L++)  {
         Locus locus = freq.getLocus(L);
         for (int allele = 0; allele < locus.size(); allele++)
            assertEquals(prob, locus.get(allele).getProb(), delta_);
      }
   }
}
