package papers.kingroup2005_TSP.tsp;
import kingroup.algorithm.window.PairArray;

import java.util.Arrays;
import java.util.Comparator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/06/2005, Time: 08:57:14
 */
public class TSPWindPairs extends PairArray {
  public TSPWindPairs(TSPMap map) {
    int n = map.size();
    int arrSize = n * (n - 1) / 2;
    Object[] arr = new Object[arrSize];
    int idx = 0;
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < r; c++) {
        double dist = map.distance(c, r);
        arr[idx++] = new TSPWinDistance(dist, c, r);
      }
    }
    Arrays.sort(arr, new Comparator() {
      public int compare(Object a, Object b) {
        return Double.compare(((TSPWinDistance) a).dist, ((TSPWinDistance) b).dist);
      }
    });
    for (int i = 0; i < arr.length; i++)
      this.add(arr[i]);
    arr = null;
//      LOG.trace(this, "All pairs=", this);
  }
}
