package kingroup_v2.like.milligan;
import kingroup_v2.kinship.DiploidLikeCalculator;
import kingroup_v2.kinship.KinshipDiploidLikeCalculator;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.vec.Vec;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/03/2006, Time: 13:51:15
 */
public class MilliganDiploidLikeCalculator extends DiploidLikeCalculator {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipDiploidLikeCalculator.class.getName());
  private final MilliganIBD ibd;
//  private KinshipLikeIBD like = new KinshipLikeIBD();
  double[] p = Vec.makeArray(0.0, MilliganIBD.SIZE);

  public MilliganDiploidLikeCalculator(MilliganIBD ibd) {
    this.ibd = ibd;
  }
//  public void setLikeIBD(KinshipLikeIBD like) {this.like = like;}

  public double calcProb() {
    BitSet keySet = new BitSet();
    keySet.set(x);
    keySet.set(x2);
    keySet.set(y);
    keySet.set(y2);
    int n = keySet.cardinality();
    switch (n) {
      case 1:
        return calcProb1();
      case 2:
        return calcProbN2();
      case 3:
        return calcProbN3();
      default: // all different
        return calcProb9();
    }
  }

  private double calcProbN2()  {
    if (x == x2) {
      if (y == y2)
        return calcProb2();
      else
        return calcProb3();
    }
    else { // x != x2
      if (y == y2)
        return calcProb5();
      else
        return calcProb7(); //
    }
  }

  private double calcProbN3()  {
    if (x == x2) {
        return calcProb4();
    }
    else { // x != x2
      if (y == y2)
        return calcProb6();
      else
        return calcProb8(); //
    }
  }

  private double calcProb1()  { // (a,a) (a,a)
    p[0] = fx;        // 1
    p[1] = fx * fx;   // 2
    p[2] = p[1];      // 2
    p[3] = p[1] * fx; // 3
    p[4] = p[1];      // 2
    p[5] = p[3];      // 3
    p[6] = p[1];      // 2
    p[7] = p[3];      // 3
    p[8] = p[3] * fx; // 4
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb2()  { // (a,a) (b,b)
    p[0] = 0;
    p[1] = fx * fy;
    p[2] = 0;
    p[3] = fx * fy * fy;
    p[4] = 0;
    p[5] = fx * fx * fy;
    p[6] = 0;
    p[7] = 0;
    p[8] = fx * fx2 * fy * fy2;
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb3()  { // (a,a) (a,b)
    double b = fy;
    if (x == y)
      b = fy2;
    p[0] = 0;
    p[1] = 0;
    p[2] = fx * b;
    p[3] = 2. * fx * fx * b;
    p[4] = 0;
    p[5] = 0;
    p[6] = 0;
    p[7] = fx * fx2 * b;
    p[8] = 2. * fx * fx2 * fy * fy2;
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb4()  // (a,a) (b,c)
  {
    double[] s = ibd.getArr();
    double f = 2. * fx * fy * fy2;
    return f*s[3] + fx*f*s[8];
  }
  private double calcProb5()  // (a,b) (a,a)
  {
    double b = fx;
    if (x == y)
      b = fx2;
    p[0] = 0;
    p[1] = 0;
    p[2] = 0;
    p[3] = 0;
    p[4] = fx * b;
    p[5] = 2. * fx * fx * b;
    p[6] = 0;
    p[7] = fy * fy2 * b;
    p[8] = 2. * fx * fx2 * fy * fy2;
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb6()  // (b,c) (a,a)
  {
    double[] s = ibd.getArr();
    double f = 2. * fx * fy * fy2;
    return f*s[5] + fx*f*s[8];
  }
  private double calcProb7()  // (a,b) (a,b)
  {
    p[ibd.K_0] = 4. * fx * fx2 * fy * fy2;
    p[ibd.K_1] = fx * fx2 * (fx + fx2);
    p[ibd.K_2] = 2. * fx * fx2;
    double[] s = ibd.getArr();
    return s[ibd.K_0] * p[ibd.K_0] + s[ibd.K_1] * p[ibd.K_1] + s[ibd.K_2] * p[ibd.K_2];
  }
  private double calcProb8()  // (a,b) (a,c)
  {
    double b = fx2;
    if (x2 == y  || x2 == y2)
      b = fx;
    double[] s = ibd.getArr();
    return b * fy * fy2 *s[7] + 4. * fx * fx2 * fy * fy2 * s[8];
  }
  private double calcProb9()
  {
    return ibd.get(ibd.K_0) * fx * fy * fx2 * fy2;
  }


  public double calcProbMatPat() {
    throw new RuntimeException("NOT READY YET!");
  }
}

