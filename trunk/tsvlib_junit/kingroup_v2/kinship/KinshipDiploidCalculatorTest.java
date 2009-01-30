package kingroup_v2.kinship;

import junit.framework.TestCase;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 14:27:51
 */
public class KinshipDiploidCalculatorTest extends TestCase {
  private static double EPS = 1e-7;

  protected void setUp() {
  }

  public void testCalcDiploidLocus_full_sib() {
    KinshipDiploidLikeCalculator fullSib = new KinshipDiploidLikeCalculator(0.5, 0.5);
//    private Allele A_ = new Allele("A", 0.5f);
//    private Allele B_ = new Allele("B", 0.5f);
//    private Locus dipAA_ = new Locus().addLine(A_).addLine(A_);
//    private Locus dipAB_ = new Locus().addLine(A_).addLine(B_);
//    private Locus dipBB_ = new Locus().addLine(B_).addLine(B_);
    int a = 0;
    double fa = 0.5;
    int b = 1;
    double fb = 0.5;

    fullSib.setX(a);
    fullSib.setX2(a);
    fullSib.setY(a);
    fullSib.setY2(a);
    fullSib.setFx(fa);
    fullSib.setFx2(fa);
    fullSib.setFy(fa);
    fullSib.setFy2(fa);
//    assertEquals(0.140625, fullSib.calcDiploid(dipAA_, dipAA_), EPS);// 0.141 from Kinship
    assertEquals(0.140625, fullSib.calcProb(), EPS);

    fullSib.setY2(b);
    fullSib.setFy2(fb);
//    assertEquals(0.09375, fullSib.calcDiploid(dipAA_, dipAB_), EPS);// 0.0938 from Kinship
    assertEquals(0.09375, fullSib.calcProb(), EPS);

    fullSib.setY(b);
    fullSib.setFy(fb);
//    assertEquals(0.015625, fullSib.calcDiploid(dipAA_, dipBB_), EPS);// 0.0156 from Kinship
    assertEquals(0.015625, fullSib.calcProb(), EPS);
  }

  public void testCalcDiploidLocus_full_sib_2() {
    KinshipDiploidLikeCalculator fullSib = new KinshipDiploidLikeCalculator(0.5, 0.5);
//    Allele A = new Allele("A", 1f);
//    Allele B = new Allele("B", 0f);
//    Locus locusAA = new Locus().addLine(A).addLine(A);
//    Locus locusAB = new Locus().addLine(A).addLine(B);
//    Locus locusBB = new Locus().addLine(B).addLine(B);
    int a = 0;
    double fa = 1;
    int b = 1;
    double fb = 0;

    fullSib.setX(a);
    fullSib.setX2(a);
    fullSib.setY(a);
    fullSib.setY2(a);
    fullSib.setFx(fa);
    fullSib.setFx2(fa);
    fullSib.setFy(fa);
    fullSib.setFy2(fa);
//    assertEquals(1.0, fullSib.calcDiploid(locusAA, locusAA), EPS);
    assertEquals(1, fullSib.calcProb(), EPS);

    fullSib.setY2(b);
    fullSib.setFy2(fb);
//    assertEquals(0.0, fullSib.calcDiploid(locusAA, locusAB), EPS);
    assertEquals(0, fullSib.calcProb(), EPS);

    fullSib.setY(b);
    fullSib.setFy(fb);
//    assertEquals(0.0, fullSib.calcDiploid(locusAA, locusBB), EPS);
    assertEquals(0, fullSib.calcProb(), EPS);
  }

  public void testCalcDiploidLocus_full_sib_3() {
    KinshipDiploidLikeCalculator fullSib = new KinshipDiploidLikeCalculator(0.5, 0.5);
//    private Allele a1_ = new Allele("a1", 0.1f);
//    private Allele a2_ = new Allele("a2", 0.2f);
//    private Allele a3_ = new Allele("a3", 0.3f);
//    private Allele a4_ = new Allele("a4", 0.4f);
//    private Locus dipA12_ = new Locus().addLine(a1_).addLine(a2_);
//    private Locus dipA32_ = new Locus().addLine(a3_).addLine(a2_);
//    private Locus dipA23_ = new Locus().addLine(a2_).addLine(a3_);
//    private Locus dipA34_ = new Locus().addLine(a3_).addLine(a4_);

    int a1 = 0;
    int a2 = 1;
    int a3 = 2;
    int a4 = 3;
    double fa1 = 0.1;
    double fa2 = 0.2;
    double fa3 = 0.3;
    double fa4 = 0.4;

    fullSib.setX(a1);
    fullSib.setX2(a2);
    fullSib.setY(a2);
    fullSib.setY2(a3);
    fullSib.setFx(fa1);
    fullSib.setFx2(fa2);
    fullSib.setFy(fa2);
    fullSib.setFy2(fa3);
//     assertEquals(0.00420, fullSib.calcDiploid(dipA12_, dipA23_), EPS);
    assertEquals(0.00420, fullSib.calcProb(), EPS);

    fullSib.setX(a1);
    fullSib.setX2(a2);
    fullSib.setY(a3);
    fullSib.setY2(a2);
    fullSib.setFx(fa1);
    fullSib.setFx2(fa2);
    fullSib.setFy(fa3);
    fullSib.setFy2(fa2);
//     assertEquals(0.00420, fullSib.calcDiploid(dipA12_, dipA32_), EPS);
    assertEquals(0.00420, fullSib.calcProb(), EPS);

    fullSib.setX(a1);
    fullSib.setX2(a2);
    fullSib.setY(a3);
    fullSib.setY2(a4);
    fullSib.setFx(fa1);
    fullSib.setFx2(fa2);
    fullSib.setFy(fa3);
    fullSib.setFy2(fa4);
//     assertEquals(0.00240, fullSib.calcDiploid(dipA12_, dipA34_), EPS);
    assertEquals(0.00240, fullSib.calcProb(), EPS);

    fullSib.setX(a2);
    fullSib.setX2(a3);
    fullSib.setY(a3);
    fullSib.setY2(a4);
    fullSib.setFx(fa2);
    fullSib.setFx2(fa3);
    fullSib.setFy(fa3);
    fullSib.setFy2(fa4);
//     assertEquals(0.0192, fullSib.calcDiploid(dipA23_, dipA34_), EPS);
    assertEquals(0.0192, fullSib.calcProb(), EPS);

    fullSib.setX(a2);
    fullSib.setX2(a3);
    fullSib.setY(a3);
    fullSib.setY2(a2);
    fullSib.setFx(fa2);
    fullSib.setFx2(fa3);
    fullSib.setFy(fa3);
    fullSib.setFy2(fa2);
//     assertEquals(0.0486, fullSib.calcDiploid(dipA23_, dipA32_), EPS);
    assertEquals(0.0486, fullSib.calcProb(), EPS);
  }

  public void testCalcDiploidLocus_mother_offspring() {
//    LikeDiploid like = new LikeDiploid(1, 0);
    KinshipDiploidLikeCalculator ibd = new KinshipDiploidLikeCalculator(1, 0);

    int a = 0;
    double fa = 0.5;
    int b = 1;
    double fb = 0.5;

    ibd.setX(a);
    ibd.setX2(a);
    ibd.setY(a);
    ibd.setY2(a);
    ibd.setFx(fa);
    ibd.setFx2(fa);
    ibd.setFy(fa);
    ibd.setFy2(fa);
//    assertEquals(0.125, like.calcDiploid(dipAA_, dipAA_), EPS);
    assertEquals(0.125, ibd.calcProb(), EPS);

    ibd.setY2(b);
    ibd.setFy2(fb);
//    assertEquals(0.125, like.calcDiploid(dipAA_, dipAB_), EPS);
    assertEquals(0.125, ibd.calcProb(), EPS);

    ibd.setX(a);
    ibd.setX2(b);
    ibd.setY(a);
    ibd.setY2(b);
    ibd.setFx(fa);
    ibd.setFx2(fb);
    ibd.setFy(fa);
    ibd.setFy2(fb);
//    assertEquals(0.25, like.calcDiploid(dipAB_, dipAB_), EPS);
    assertEquals(0.25, ibd.calcProb(), EPS);

    ibd.setX(a);
    ibd.setX2(a);
    ibd.setY(b);
    ibd.setY2(b);
    ibd.setFx(fa);
    ibd.setFx2(fa);
    ibd.setFy(fb);
    ibd.setFy2(fb);
//    assertEquals(0.0, like.calcDiploid(dipAA_, dipBB_), EPS);
    assertEquals(0.0, ibd.calcProb(), EPS);
  }

  public void testCalcDiploidLocus_unrelated() {
//    LikeDiploid like = new LikeDiploid(0, 0);
    KinshipDiploidLikeCalculator ibd = new KinshipDiploidLikeCalculator(0, 0);

    int a = 0;
    double fa = 0.5;
    int b = 1;
    double fb = 0.5;

    ibd.setX(a);
    ibd.setX2(a);
    ibd.setY(a);
    ibd.setY2(a);
    ibd.setFx(fa);
    ibd.setFx2(fa);
    ibd.setFy(fa);
    ibd.setFy2(fa);
//    assertEquals(0.0625, like.calcDiploid(dipAA_, dipAA_), EPS);
    assertEquals(0.0625, ibd.calcProb(), EPS);

    ibd.setY2(b);
    ibd.setFy2(fb);
//    assertEquals(0.125, like.calcDiploid(dipAA_, dipAB_), EPS);
    assertEquals(0.125, ibd.calcProb(), EPS);

    ibd.setX(a);
    ibd.setX2(b);
    ibd.setY(a);
    ibd.setY2(b);
    ibd.setFx(fa);
    ibd.setFx2(fb);
    ibd.setFy(fa);
    ibd.setFy2(fb);
//    assertEquals(0.25, like.calcDiploid(dipAB_, dipAB_), EPS);
    assertEquals(0.25, ibd.calcProb(), EPS);

    ibd.setX(a);
    ibd.setX2(a);
    ibd.setY(b);
    ibd.setY2(b);
    ibd.setFx(fa);
    ibd.setFx2(fa);
    ibd.setFy(fb);
    ibd.setFy2(fb);
//    assertEquals(0.0625, like.calcDiploid(dipAA_, dipBB_), EPS);
    assertEquals(0.0625, ibd.calcProb(), EPS);
  }
}
