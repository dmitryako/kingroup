package kingroup_v2.kinship;
import kingroup_v2.kinship.like.KinshipLikeIBD;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/09/2005, Time: 15:32:57
 */
public class KinshipHaplodipCalculator {
  private final double r;
  private final double notR;
  private KinshipLikeIBD like = new KinshipLikeIBD();

  public KinshipHaplodipCalculator(KinshipIBD model, Kinship kinship) {
    if (kinship.getTreatHaploid() == Kinship.HAPLOID_MAT)
      r = model.getRm();
    else
      r = model.getRp();
    notR = 1. - r;
  }
  public void setLikeIBD(KinshipLikeIBD like) {this.like = like;}
  public double calcProb(int x, double fx, int y, double fy, int y2, double fy2) {
    double res = 0;
    double p = like.calcProb(r, x, fx, y, fy);
    res += p * fy2;
    if (y != y2) { // exchange y and y2
      p = like.calcProb(r, x, fx, y2, fy2);
      res += p * fy;
    }
    return res;
  }
  public double calcProb(int x, double fx, int y, double fy) {
    return like.calcProb(r, x, fx, y, fy);
  }
  public void test() {

  }
  public void test2() {}
}
