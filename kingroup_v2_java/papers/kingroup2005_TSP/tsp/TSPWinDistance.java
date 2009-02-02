package papers.kingroup2005_TSP.tsp;
import javax.utilx.pair.IntPair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/06/2005, Time: 08:59:09
 */
public class TSPWinDistance extends IntPair {
  public final double dist;
  public TSPWinDistance(double dist, int a, int b) {
    super(a, b);
    this.dist = dist;
  }
}
