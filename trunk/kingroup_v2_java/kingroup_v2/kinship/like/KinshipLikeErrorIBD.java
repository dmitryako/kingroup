package kingroup_v2.kinship.like;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/02/2006, Time: 15:33:02
 */
public class KinshipLikeErrorIBD extends KinshipLikeIBD
{
  private double e;
  private double e2;
  private double E2;
  private double eE;
  public KinshipLikeErrorIBD(double error)
  {
    this.e = error;
    e2 = e * e;
    double oneE = 1 - e;
    E2 = oneE * oneE;
    eE = e * oneE;
  }
  public double calcProb(double r, int x, double fx, int y, double fy) {
    double prob = super.calcProb(r, x, fx, y, fy);
    return E2 * prob + eE * (fx + fy) + e2;
  }
}
