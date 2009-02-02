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
import kingroup.project.KinGroupProjectModel;

/**
 * Testing <code>kingroup.model.KinGroupProjectModel</code>.
 */
public class KinGroupProjectModelTest extends TestCase {
   private SmithPopBuilderModel pop_ = new kingroup.population.SmithPopBuilderModel();
   private int count_ = 0;
   private String fn_ = "fileName";
   private int nl_ = count_++;  // number of loci

   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(KinGroupProjectModelTest.class);
   }

   public void testGetSet() {
      KinGroupProjectModel data = new KinGroupProjectModel();

      setNewAttributes(data);
      assertAttributes(data);

      setNewAttributes(data);
      assertAttributes(data);
   }

   final public void testCopyTo() {
      KinGroupProjectModel data = new KinGroupProjectModel();
      setNewAttributes(data);

      KinGroupProjectModel data2 = new KinGroupProjectModel();
      setNewAttributes(data2); // data != data2

      data2.copyTo(data); // data == data2
      assertAttributes(data);
      assertAttributes(data2);
   }


   private void setNewAttributes(KinGroupProjectModel data) {
      generateNewAttributes();

      data.setPopulationBuilder(pop_);
      data.setFileName(fn_);
   }

   private void assertAttributes(KinGroupProjectModel data) {
      assertEquals(fn_, data.getFileName());
      assertEquals(nl_, data.getPopulationBuilder().getNumLoci());
   }

   private void generateNewAttributes() {
      fn_ += Integer.toString(count_++);
      nl_ = count_++;
      pop_ = new kingroup.population.SmithPopBuilderModel();

      pop_.setNumLoci(nl_);
   }
}

