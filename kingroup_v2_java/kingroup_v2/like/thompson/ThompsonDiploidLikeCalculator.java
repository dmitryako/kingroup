package kingroup_v2.like.thompson;
import kingroup_v2.kinship.DiploidLikeCalculator;

import javax.utilx.arrays.vec.Vec;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 10:36:07
 */
public class ThompsonDiploidLikeCalculator extends DiploidLikeCalculator {
//  private static ProjectLogger log = ProjectLogger.getLogger(ThompsonDiploidLikeCalculator.class.getName());
  private final ThompsonIBD ibd;
  double[] p = Vec.makeArray(0.0, ThompsonIBD.SIZE);

  //DEBUGGING
  final int N_TYPES = 10;
  boolean type[] = new boolean[N_TYPES];

  public ThompsonDiploidLikeCalculator(ThompsonIBD ibd) {
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
//    if (!type[1]) {
//      type[1] = true;
//      log.info("type 1");
//    }

    p[2] = fx * fx;    // k0 = p^2
    p[1] = p[2] * fx;  // k1 = p^3
    p[0] = p[1] * fx;  // k2 = p^4
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb2()  { // (a,a) (b,b)
//    if (!type[2]) {
//      type[2] = true;
//      log.info("type 2");
//    }

    p[0] = fx * fx2 * fy * fy2;
    return ibd.get(0) * p[0];
  }
  private double calcProb3()  { // (a,a) (a,b)
//    if (!type[3]) {
//      type[3] = true;
//      log.info("type 3");
//    }

    double b = fy;
    if (x == y)
      b = fy2;
    p[0] = 2. * fx * fx2 * fy * fy2;
    p[1] = fx * fx2 * b;
    p[2] = 0;
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb4()  // (a,a) (b,c)
  {
//    if (!type[4]) {
//      type[4] = true;
//      log.info("type 4");
//    }
    return 2. * fx * fx2 * fy * fy2 * ibd.get(0);
  }
  private double calcProb5()  // (a,b) (a,a)
  {
//    if (!type[5]) {
//      type[5] = true;
//      log.info("type 5");
//    }
    double b = fx;
    if (x == y)
      b = fx2;
    p[0] = 2. * fx * fx2 * fy * fy2;
    p[1] = fy * fy2 * b;
    p[2] = 0;
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb6()  // (b,c) (a,a)
  {
//    if (!type[6]) {
//      type[6] = true;
//      log.info("type 6");
//    }
    return calcProb4();
  }
  private double calcProb7()  // (a,b) (a,b)
  {
//    if (!type[7]) {
//      type[7] = true;
//      log.info("type 7");
//    }
    p[0] = 4. * fx * fx2 * fy * fy2;
    p[1] = fx * fx2 * (fx + fx2);
    p[2] = 2. * fx * fx2;
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb8()  // (a,b) (a,c)
  {
//    if (!type[8]) {
//      type[8] = true;
//      log.info("type 8");
//    }
    double b = fx2;
    if (x2 == y  || x2 == y2)
      b = fx;
    p[0] = 4. * fx * fx2 * fy * fy2;
    p[1] = b * fy * fy2;
    p[2] = 0;
    return Vec.dot(ibd.getArr(), p);
  }
  private double calcProb9()
  {
//    if (!type[9]) {
//      type[9] = true;
//      log.info("type 9");
//    }
    return 4.* ibd.get(0) * fx * fy * fx2 * fy2;
  }


  public double calcProbMatPat() {
    throw new RuntimeException("NOT READY YET!");
  }
}

