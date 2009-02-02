package kingroup.genetics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 24, 2004, Time: 2:43:03 PM
 */
public class LikeDiploidTest extends TestCase {
   private static double EPS = 1e-7;
   private LikeDiploid fullSib = new LikeDiploid(0.5, 0.5);

   private Allele A_ = new Allele("A", 0.5f);
   private Allele B_ = new Allele("B", 0.5f);
   private Locus dipAA_ = new Locus().add(A_).add(A_);
   private Locus dipAB_ = new Locus().add(A_).add(B_);
   private Locus dipBB_ = new Locus().add(B_).add(B_);

   private Allele a1_ = new Allele("a1", 0.1f);
   private Allele a2_ = new Allele("a2", 0.2f);
   private Allele a3_ = new Allele("a3", 0.3f);
   private Allele a4_ = new Allele("a4", 0.4f);
   private Locus dipA12_ = new Locus().add(a1_).add(a2_);
   private Locus dipA32_ = new Locus().add(a3_).add(a2_);
   private Locus dipA23_ = new Locus().add(a2_).add(a3_);
   private Locus dipA34_ = new Locus().add(a3_).add(a4_);

   public static Test suite() { return new TestSuite(LikeDiploidTest.class); }
   public static void main(String[] args) {  junit.textui.TestRunner.run(suite()); }
   protected void setUp() {    }
   public void testCalcDiploidLocus_full_sib() {
      assertEquals(0.140625, fullSib.calcDiploid(dipAA_, dipAA_), EPS);// 0.141 from Kinship
      assertEquals(0.09375, fullSib.calcDiploid(dipAA_, dipAB_), EPS);// 0.0938 from Kinship
      assertEquals(0.015625, fullSib.calcDiploid(dipAA_, dipBB_), EPS);// 0.0156 from Kinship
   }

   public void testCalcDiploidLocus_full_sib_2() {
      Allele A = new Allele("A", 1f);
      Allele B = new Allele("B", 0f);
      Locus locusAA = new Locus().add(A).add(A);
      Locus locusAB = new Locus().add(A).add(B);
      Locus locusBB = new Locus().add(B).add(B);
      assertEquals(1.0, fullSib.calcDiploid(locusAA, locusAA), EPS);
      assertEquals(0.0, fullSib.calcDiploid(locusAA, locusAB), EPS);
      assertEquals(0.0, fullSib.calcDiploid(locusAA, locusBB), EPS);
   }

   public void testCalcDiploidLocus_full_sib_3() {
      assertEquals(0.00420, fullSib.calcDiploid(dipA12_, dipA23_), EPS);
      assertEquals(0.00420, fullSib.calcDiploid(dipA12_, dipA32_), EPS);
      assertEquals(0.00240, fullSib.calcDiploid(dipA12_, dipA34_), EPS);
      assertEquals(0.0192, fullSib.calcDiploid(dipA23_, dipA34_), EPS);
      assertEquals(0.0486, fullSib.calcDiploid(dipA23_, dipA32_), EPS);
   }

   public void testCalcDiploidLocus_mother_offspring() {
      LikeDiploid like = new LikeDiploid(1, 0);
      assertEquals(0.125, like.calcDiploid(dipAA_, dipAA_), EPS);
      assertEquals(0.125, like.calcDiploid(dipAA_, dipAB_), EPS);
      assertEquals(0.25, like.calcDiploid(dipAB_, dipAB_), EPS);
      assertEquals(0.0, like.calcDiploid(dipAA_, dipBB_), EPS);
   }

   public void testCalcDiploidLocus_unrelated() {
      LikeDiploid like = new LikeDiploid(0, 0);
      assertEquals(0.0625, like.calcDiploid(dipAA_, dipAA_), EPS);
      assertEquals(0.125, like.calcDiploid(dipAA_, dipAB_), EPS);
      assertEquals(0.25, like.calcDiploid(dipAB_, dipAB_), EPS);
      assertEquals(0.0625, like.calcDiploid(dipAA_, dipBB_), EPS);
   }
}
