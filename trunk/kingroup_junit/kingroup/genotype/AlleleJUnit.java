package kingroup.genotype;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genotype.Allele;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 16, 2004, Time: 3:53:25 PM
 */
public class AlleleJUnit extends TestCase {
   private double prob_ = 0;
   private double delta_ = 1e-7f;
   private String id_ = "";
   private int count_ = 0;

   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(AlleleJUnit.class);
   }
   protected void setUp() {    }

   public void testGetSet() {
      Allele data = new Allele();

      setNewAttributes(data);
      assertAttributes(data);

      setNewAttributes(data);
      assertAttributes(data);
   }
   public void testDuplicate() {
      Allele data = new Allele();
      setNewAttributes(data);
      assertAttributes(data);

      Allele data2 = data.duplicate();
      assertAttributes(data2); // must be the same as data
      assertAttributes(data);  // remains the same

      AlleleJUnit test2 = new AlleleJUnit();
      test2.setNewAttributes(data2); // own copy of everything!!!
      test2.assertAttributes(data2);

      assertAttributes(data); // will only pass this if data and data2 dont shared any attributes
   }
   public void testCopyAlleleFrom() {
      Allele data = new Allele();
      setNewAttributes(data);
      assertAttributes(data);

      Allele data2 = new Allele();
      data2.copyAlleleFrom(data);
      assertAttributes(data2); // must be the same as data
      assertAttributes(data);  // remains the same

      AlleleJUnit test2 = new AlleleJUnit();
      test2.setNewAttributes(data2); // own copy of everything!!!
      test2.assertAttributes(data2);

      assertAttributes(data); // will only pass this if data and data2 dont shared any attributes
   }
   public void testAddMultiply() {
      Allele A = new Allele();
      setNewAttributes(A);

      AlleleJUnit test2 = new AlleleJUnit();
      Allele B = new Allele();
      test2.setNewAttributes(B);

      B.multiplyProb(-1f);
      A.addProb(B.getProb());                  // must be zero
      assertEquals(0f, A.getProb(), delta_);

      setNewAttributes(A);
      assertEquals(true, prob_ != test2.prob_); // must be different

      test2.setNewAttributes(B);
      A.addProb(B.getProb());
      assertEquals(prob_ + test2.prob_, A.getProb(), delta_);
   }

   public void testSubtract() {
      Allele A = new Allele();
      setNewAttributes(A);

      AlleleJUnit test2 = new AlleleJUnit();
      Allele B = new Allele();
      test2.setNewAttributes(B);

      A.subtractProb(B.getProb());                  // must be zero
      assertEquals(0f, A.getProb(), delta_);

      setNewAttributes(A);
      assertEquals(true, prob_ != test2.prob_); // must be different
      A.subtractProb(B.getProb());
      assertEquals(prob_ - test2.prob_, A.getProb(), delta_);
   }

   private void setNewAttributes(Allele data) {
      generateNewAttributes();

      data.setProb(prob_);
      data.setId(id_);
   }

   private void assertAttributes(Allele data) {
      assertEquals(prob_, data.getProb(), delta_);
      assertEquals(id_, data.getId());
   }

   private void generateNewAttributes() {
      prob_ += 0.1;
      id_ = Integer.toString(count_++);
   }
}
