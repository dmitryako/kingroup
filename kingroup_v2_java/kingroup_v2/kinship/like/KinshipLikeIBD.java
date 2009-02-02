package kingroup_v2.kinship.like;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/09/2005, Time: 15:40:12
 */
public class KinshipLikeIBD {
  public double calcProb(double r, int x, double fx, int y, double fy) {
//    if (fy < 0 || fy > 1)
//      return 0;
//    if (r < -1 || r > 1)
//      return 0;
    if (r < 0)
      return negProb(r, x, fx, y, fy);
    return posProb(r, x, fx, y, fy);
  }
  public double posProb(double r, int x, double fx, int y, double fy) {
    if (x == y)
      return fx * (r + (1.-r) * fx);
    return fx * (1.-r) * fy;
  }

  private double negProb(double r, int x, double fx, int y, double fy)
  {
    double minX = fx / (fx - 1.);
    double minY = fy / (fy - 1.);
    if (r < Math.min(minX, minY) || r > 1  ||  r < -1)
//    if (r < 0.5*(minX + minY) || r > 1)
      return 0;
    if (x == y)
      return fx * (r + (1.-r) * fx);
    return fx * (1.-r) * fy;
  }
}
