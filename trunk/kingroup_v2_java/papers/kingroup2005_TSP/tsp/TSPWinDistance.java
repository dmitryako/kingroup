package papers.kingroup2005_TSP.tsp;
import javax.utilx.pair.Int2;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/06/2005, Time: 08:59:09
 */
public class TSPWinDistance extends Int2 {
  public final double dist;
  public TSPWinDistance(double dist, int a, int b) {
    super(a, b);
    this.dist = dist;
  }
}
