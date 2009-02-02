package papers.kingroup2005_TSP.tsp;
import javax.utilx.RandomSeed;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/05/2005, Time: 13:25:23
 */
public class TSPMapFactory {
  public static TSPMap makeMap(int numNodes) {
    TSPMap res = new TSPMap(numNodes);
    RandomSeed rand = RandomSeed.getInstance();
    for (int i = 0; i < numNodes; i++) {
      res.add(rand.nextDouble(), rand.nextDouble());
    }
    return res;
  }
}
