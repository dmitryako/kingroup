package kingroup.genetics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.genotype.Allele;
import kingroup.genotype.Loci;
import kingroup.genotype.Locus;
import kingroup.model.HypothesisModel;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 25, 2004, Time: 12:14:26 PM
 */
public class KKinshipLhHypothesisTest extends TestCase {
   private double eps_ = 1e-7;
   private HypothesisModel fullSibHypo_ = new HypothesisModel();
   private KinshipLikeHypo fullSib_;

   private Allele a1_ = new Allele("a1", 0.1f);
   private Allele a2_ = new Allele("a2", 0.2f);
   private Allele a3_ = new Allele("a3", 0.3f);
   private Allele a4_ = new Allele("a4", 0.4f);

   private Allele b1_ = new Allele("b1", 0.1f);
   private Allele b2_ = new Allele("b2", 0.2f);
   private Allele b3_ = new Allele("b3", 0.3f);
   private Allele b4_ = new Allele("b4", 0.4f);

   private Locus dipA11_ = new Locus().add(a1_).add(a1_);
   private Locus dipB11_ = new Locus().add(b1_).add(b1_);
   private Locus dipB12_ = new Locus().add(b1_).add(b2_);
   private Locus dipB34_ = new Locus().add(b3_).add(b4_);
   private Locus dipA12_ = new Locus().add(a1_).add(a2_);
   private Locus hapA1_ = new Locus().add(a1_);
   private Locus hapB1_ = new Locus().add(b1_);
   private Locus hapB2_ = new Locus().add(b2_);
   private Locus dipA34_ = new Locus().add(a3_).add(a4_);
   private Loci lociA11_B11_;
   private Loci lociA1_B1_;
   private Loci lociA1_B2_;
   private Loci lociA34_B12_;
   private Loci lociA12_A34_;
   private Loci lociA12_B34_;

   protected void setUp() {
      fullSibHypo_.loadDefault();
      fullSibHypo_.setTreatHaploid(HypothesisModel.ASSUME_MAT);
      fullSibHypo_.setRm(0.5f);
      fullSibHypo_.setRp(0.5f);

      fullSib_ = new KinshipLikeHypo(fullSibHypo_);

      lociA1_B1_ = new Loci().add(hapA1_).add(hapB1_);
      lociA1_B2_ = new Loci().add(hapA1_).add(hapB2_);

      lociA11_B11_ = new Loci().add(dipA11_).add(dipB11_);
      lociA34_B12_ = new Loci().add(dipA34_).add(dipB12_);
      lociA12_A34_ = new Loci().add(dipA12_).add(dipA34_);
      lociA12_B34_ = new Loci().add(dipA12_).add(dipB34_);
   }

   public static Test suite() { return new TestSuite(KKinshipLhHypothesisTest.class); }
   public static void main(String[] args) {  junit.textui.TestRunner.run(suite()); }
   public void testCalcDiploidLocus_full_sib() {
      assertEquals(3.025e-3, fullSib_.calculateHypothesis(lociA1_B1_, lociA1_B1_), eps_);//3.03e-3 in Kinship
      assertEquals(5.500e-4, fullSib_.calculateHypothesis(lociA1_B1_, lociA1_B2_), eps_);
      assertEquals(3.025e-5, fullSib_.calculateHypothesis(lociA1_B1_, lociA11_B11_), eps_ * 0.0001);//3.03e-5 in Kinship
      assertEquals(5.760e-6, fullSib_.calculateHypothesis(lociA34_B12_, lociA12_B34_), eps_ * 0.0001);
      assertEquals(5.760e-6, fullSib_.calculateHypothesis(lociA34_B12_, lociA12_A34_), eps_ * 0.0001);
   }
}

