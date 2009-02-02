package papers.kingroup2005_TSP.tsp;
import kingroup.algorithm.window.AlgAccessViaPairs;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/06/2005, Time: 08:50:27
 */
public class TSPWinAccessOrder extends AlgAccessViaPairs {
  public TSPWinAccessOrder(TSPMap map) {
    super(map.size(), new TSPWindPairs(map));
  }
}
