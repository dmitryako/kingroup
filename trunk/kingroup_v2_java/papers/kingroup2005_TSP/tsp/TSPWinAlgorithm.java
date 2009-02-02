package papers.kingroup2005_TSP.tsp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/06/2005, Time: 08:41:33
 */
public class TSPWinAlgorithm {
  private final int WIND_SIZE;
  private final TSPMap map;
  public TSPWinAlgorithm(TSPMap map, int windSize) {
    this.map = map;
    WIND_SIZE = windSize;
  }
  public TSPPath solve() {
    ArrayList paths = new ArrayList();
    int count = 0;
    TSPPath[] win = new TSPPath[WIND_SIZE];
    int currWindSize = 0;
    TSPWinAccessOrder order = new TSPWinAccessOrder(map);
    for (int nextIdx = order.nextIdx(); nextIdx != order.NOT_SET
      ; nextIdx = order.nextIdx()) {
      if (currWindSize == 0) {// VERY FIRST
        TSPPath newPath = new TSPPath(map);
        newPath.add(0, nextIdx);
        win[currWindSize++] = newPath;
        continue;
      }
      ArrayList tempStored = new ArrayList();
      for (int i = 0; i < currWindSize; i++) {
        TSPPath curr = win[i];
//            LOG.report(this, "curr=" + curr);
        for (int idx = 0; idx < curr.size() + 1; idx++) { // NOTE +1!!!!
          TSPPath newPath = new TSPPath(curr); // make a fresh DEEP copy
//               LOG.report(this, "STEP 5: newPart=" + newPart);
          newPath.add(idx, nextIdx);
//               LOG.report(this, "step 5: pop=\n" + pop);
          tempStored.add(newPath);
          newPath.loadDistance();
        }
      }
//STEP 8: sort by ASCENDING index
      Object[] arr = tempStored.toArray();
      Arrays.sort(arr, new Comparator() {
        public int compare(Object a, Object b) {
          return Double.compare(((TSPPath) a).distance(), ((TSPPath) b).distance());
        }
      });
//STEP 9: retain WIND_SIZE best partitions
      currWindSize = Math.min(WIND_SIZE, arr.length);
      for (int j = 0; j < currWindSize; j++) {
        win[j] = (TSPPath) arr[j];
//            LOG.report(this, "win["+j+"].simpson index=" + win[j].getSimpsonIndex());
      }
    }
    return win[0];
  }
}
