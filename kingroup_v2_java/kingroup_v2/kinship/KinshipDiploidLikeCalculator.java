package kingroup_v2.kinship;
import kingroup_v2.kinship.like.KinshipLikeIBD;

import tsvlib.project.ProjectLogger;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 08:02:43
 */
public class KinshipDiploidLikeCalculator extends DiploidLikeCalculator {
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private double rm;
  private double rp;
  private KinshipLikeIBD like = new KinshipLikeIBD();

  public KinshipDiploidLikeCalculator(double rm, double rp) {
    this.rm = rm;
    this.rp = rp;
  }
  public void setLikeIBD(KinshipLikeIBD like) {this.like = like;}

  public double calcProb() {
    double res = 0;
    double p = like.calcProb(rm, x, fx, y, fy);
    double p2 = like.calcProb(rp, x2, fx2, y2, fy2);
    res += p * p2;
    if (y != y2) { // exchange y and y2
      p = like.calcProb(rm, x, fx, y2, fy2);
      p2 = like.calcProb(rp, x2, fx2, y, fy);
      res += p * p2;
    }
    if (x == x2) {
      return res;
    }

    // EXCHANGE x and x2
    p = like.calcProb(rm, x2, fx2, y, fy);
    p2 = like.calcProb(rp, x, fx, y2, fy2);
    res += p * p2;
    if (y != y2) { // exchange y and y2
      p = like.calcProb(rm, x2, fx2, y2, fy2);
      p2 = like.calcProb(rp, x, fx, y, fy);
      res += p * p2;
    }
    return res;
  }
  public double calcProbMatPat() {
//    // log.info("diploid =\n" + toString());
    if (isUnknownMat() && isUnknownPat()) {
      return calcProb();
    }
    // CHECK MENDELIAN
    if (!isValidChild(x, x2, matX, matX2)) {
      return 0;
    }
    if (!isValidChild(x, x2, patX, patX2)) {
      return 0;
    }
    if (!isValidChild(y, y2, matY, matY2)) {
      return 0;
    }
    if (!isValidChild(y, y2, patY, patY2)) {
      return 0;
    }
    double res = 0;
    if (isParent(x, matX, matX2)
      && isParent(y, matY, matY2)
      && isParent(x2, patX, patX2)
      && isParent(y2, patY, patY2)
      ) {
      double p = like.calcProb(rm, x, fx, y, fy);
      double p2 = like.calcProb(rp, x2, fx2, y2, fy2);
      res += p * p2;
    }
    if (y != y2) { // exchange y and y2
      if (isParent(x, matX, matX2)
        && isParent(y2, matY, matY2)
        && isParent(x2, patX, patX2)
        && isParent(y, patY, patY2)
        ) {
        double p = like.calcProb(rm, x, fx, y2, fy2);
        double p2 = like.calcProb(rp, x2, fx2, y, fy);
        res += p * p2;
      }
    }
    if (x == x2) {
      return res;
    }

    // EXCHANGE x and x2
    if (isParent(x2, matX, matX2)
      && isParent(y, matY, matY2)
      && isParent(x, patX, patX2)
      && isParent(y2, patY, patY2)
      ) {
      double p = like.calcProb(rm, x2, fx2, y, fy);
      double p2 = like.calcProb(rp, x, fx, y2, fy2);
      res += p * p2;
    }
    if (y != y2) { // exchange y and y2
      if (isParent(x2, matX, matX2)
        && isParent(y2, matY, matY2)
        && isParent(x, patX, patX2)
        && isParent(y, patY, patY2)
        ) {
        double p = like.calcProb(rm, x2, fx2, y2, fy2);
        double p2 = like.calcProb(rp, x, fx, y, fy);
        res += p * p2;
      }
    }
    return res;
  }
}
