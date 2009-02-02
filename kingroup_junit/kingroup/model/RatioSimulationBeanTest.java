package kingroup.model;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 6, 2004, Time: 9:34:08 AM
 */

public class RatioSimulationBeanTest   extends TestCase {
   private int count_ = 100;
   private KinshipIBDModelV1 prim_ = new KinshipIBDModelV1();
   private KinshipIBDModelV1 null_ = new KinshipIBDModelV1();
   private int numRmsPrim_ = count_++;
   private int numRpsNull_ = count_++;
   private int simSize_ = count_++;

   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(PopulationBuilderBeanTest.class);
   }
   protected void setUp() {    }

   final public void testCopyTo() {
      RatioSimulationModel data = new RatioSimulationModel();
      setNewAttributes(data);

      RatioSimulationModel data2 = new RatioSimulationModel();
      setNewAttributes(data2); // data != data2

      data2.copyTo(data); // data == data2
      assertAttributes(data);
      assertAttributes(data2);
   }

   public void testGetSet() {
      RatioSimulationModel data = new RatioSimulationModel();

      setNewAttributes(data);
      assertAttributes(data);
   }

   private void setNewAttributes(RatioSimulationModel data) {
      generateNewAttributes();

      data.setPrimIdentity(prim_);
      data.setNullIdentity(null_);
      data.setSimulationSize(simSize_);
   }

   private void assertAttributes(RatioSimulationModel data) {
      assertEquals(numRmsPrim_, data.getPrimIdentity().getNumRms());
      assertEquals(numRpsNull_, data.getNullIdentity().getNumRps());
      assertEquals(simSize_, data.getSimulationSize());
   }

   private void generateNewAttributes() {
      numRmsPrim_ = count_++;
      numRpsNull_ = count_++;
      simSize_ = count_++;
      prim_ = new KinshipIBDModelV1();
      null_ = new KinshipIBDModelV1();
      prim_.setNumRms(numRmsPrim_);
      null_.setNumRps(numRpsNull_);
   }
}
