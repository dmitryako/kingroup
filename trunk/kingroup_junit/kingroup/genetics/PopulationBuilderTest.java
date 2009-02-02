package kingroup.genetics;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.population.PopBuilderOLD;
import kingroup.population.OldPop;
import kingroup.population.SmithPopBuilderModel;
import kingroup.util.FastIdArray;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 6, 2004, Time: 9:20:02 AM
 */

public class PopulationBuilderTest  extends TestCase {
   static protected int na_ = 2; // number of alleles
   static protected int nl_ = 2; // number of loci
   static protected double delta_ = 1f-10;

   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(PopulationBuilderTest.class);
   }
   public void testKPopulationBuilder() {
      SmithPopBuilderModel model = new SmithPopBuilderModel();
      nl_ = 2;
      na_ = 5;
      model.setNumAlleles(na_);
      model.setNumLoci(nl_);
      model.setFreqDistribution(SmithPopBuilderModel.FREQ_EQUAL);

      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(model);
      freq.normalize(1.0f, false);

      PopBuilderOLD builder = new PopBuilderOLD(freq);
      OldPop pop = builder.build(model);

      // TEST setAlleleFreq(UsrPopFactory);
      freq = builder.getAlleleFreq();
      KAlleleFreqEqualTest freqTest = new KAlleleFreqEqualTest();
      freq.normalize(1, false);
      double prob = 1.0 / na_;
      freqTest.assertEqualFreqs(prob, freq, 0, nl_ - 1);

      // TEST setLocusIds(UsrPopFactory.getLocusIds());
      FastIdArray locusIds = pop.getLocusIds();
      for (int L = 0; L < locusIds.size(); L++) {
         assertEquals("L" + (L + 1), locusIds.get(L).getId());
      }
   }
   public void testBuild() {
      //TODO: How to test rebuild()
   }

   protected void loadModelAttributes(kingroup.population.SmithPopBuilderModel model) {
      model.setNumAlleles(na_);
      model.setNumLoci(nl_);
      model.setFreqDistribution(kingroup.population.SmithPopBuilderModel.FREQ_EQUAL);
   }
}
