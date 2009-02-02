package kingroup.model;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.population.SmithPopBuilderModel;

/**
 * Testing <code>kingroup.population.SmithPopBuilderModel</code>.
 */
public class PopulationBuilderBeanTest extends TestCase {
   private int count_ = 0;
   private KinshipIBDModelV1 prim_ = new KinshipIBDModelV1();
   private KinshipIBDModelV1 null_ = new KinshipIBDModelV1();
   private int numRmsPrim_ = count_++;
   private int numRpsNull_ = count_++;
   private int nl_ = count_++;  // number of loci
   private int na_ = count_++; // number of alleles for each get
   private int ng_ = count_++; // number of kin groups
   private int gs_ = count_++;
   private int fd_ = count_++;
   private boolean fts_ = true;
   private boolean ipg_ = true;

   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(PopulationBuilderBeanTest.class);
   }
   protected void setUp() {    }

   final public void testCopyTo() {
      SmithPopBuilderModel data = new kingroup.population.SmithPopBuilderModel();
      setNewAttributes(data);

      kingroup.population.SmithPopBuilderModel data2 = new kingroup.population.SmithPopBuilderModel();
      setNewAttributes(data2); // data != data2

      data2.copyTo(data); // data == data2
      assertAttributes(data);
      assertAttributes(data2);
   }

   public void testGetSet() {
      kingroup.population.SmithPopBuilderModel data = new kingroup.population.SmithPopBuilderModel();

      setNewAttributes(data);
      assertAttributes(data);
   }

   private void setNewAttributes(kingroup.population.SmithPopBuilderModel data) {
      generateNewAttributes();

      data.setPrimIdentity(prim_);
      data.setNullIdentity(null_);
      data.setNumLoci(nl_);
      data.setNumAlleles(na_);
      data.setNumFullSibGroups(ng_);
      data.setGroupSize(gs_);
      data.setFreqDistribution(fd_);
      data.setFreqTargetSample(fts_);
      data.setIncParents(ipg_);
   }

   private void assertAttributes(kingroup.population.SmithPopBuilderModel data) {
      assertEquals(numRmsPrim_, data.getPrimIdentity().getNumRms());
      assertEquals(numRpsNull_, data.getNullIdentity().getNumRps());
      assertEquals(nl_, data.getNumLoci());
      assertEquals(na_, data.getNumAlleles());
      assertEquals(ng_, data.getNumFullSibGroups());
      assertEquals(gs_, data.getGroupSize());
      assertEquals(fd_, data.getFreqDistribution());
      assertEquals(fts_, data.getFreqTargetSample());
      assertEquals(ipg_, data.getIncParents());
   }

   private void generateNewAttributes() {
      numRmsPrim_ = count_++;
      numRpsNull_ = count_++;
      nl_ = count_++;
      na_ = count_++;
      ng_ = count_++;
      gs_ = count_++;
      fd_ = count_++;
      fts_ = !fts_;
      ipg_ = !ipg_;
      prim_ = new KinshipIBDModelV1();
      null_ = new KinshipIBDModelV1();
      prim_.setNumRms(numRmsPrim_);
      null_.setNumRps(numRpsNull_);
   }
}

