package kingroup.genetics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.genotype.Allele;
import kingroup.genetics.LikeHaploDip;
import kingroup.genotype.Locus;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 25, 2004, Time: 9:04:15 AM
 */
public class LikeHaploDipJUnit extends TestCase {
   private double eps_ = 1e-7;
   private LikeHaploDip fullSib_ = new LikeHaploDip(0.5);

   private Allele a1_ = new Allele("a1", 0.1f);
   private Allele a2_ = new Allele("a2", 0.2f);
   private Allele a3_ = new Allele("a3", 0.3f);
   private Allele a4_ = new Allele("a4", 0.4f);
   private Locus dipA11_ = new Locus().add(a1_).add(a1_);
   private Locus dipA12_ = new Locus().add(a1_).add(a2_);
   private Locus dipA32_ = new Locus().add(a3_).add(a2_);
   private Locus dipA23_ = new Locus().add(a2_).add(a3_);

   public static Test suite() { return new TestSuite(LikeHaploDipJUnit.class); }
   public static void main(String[] args) {  junit.textui.TestRunner.run(suite()); }
   protected void setUp() {    }
   public void testCalcHaploDip_full_sib() {
      assertEquals(0.00550, fullSib_.calculateHaploDip(dipA11_, a1_), eps_);
      assertEquals(0.01200, fullSib_.calculateHaploDip(dipA12_, a1_), eps_);
      assertEquals(0.00600, fullSib_.calculateHaploDip(dipA23_, a1_), eps_);
      assertEquals(0.00600, fullSib_.calculateHaploDip(dipA32_, a1_), eps_);

      assertEquals(0.00100, fullSib_.calculateHaploDip(dipA11_, a2_), eps_);
      assertEquals(0.01400, fullSib_.calculateHaploDip(dipA12_, a2_), eps_);
      assertEquals(0.04200, fullSib_.calculateHaploDip(dipA23_, a2_), eps_);
      assertEquals(0.04200, fullSib_.calculateHaploDip(dipA32_, a2_), eps_);
   }
   public void testCalcHaploid_full_sib() {
      assertEquals(0.0550, fullSib_.calculateHaploid(a1_, a1_), eps_);
      assertEquals(0.0100, fullSib_.calculateHaploid(a2_, a1_), eps_);
   }
}
