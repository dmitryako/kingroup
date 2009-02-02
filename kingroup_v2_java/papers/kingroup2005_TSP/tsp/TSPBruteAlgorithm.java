package papers.kingroup2005_TSP.tsp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/05/2005, Time: 13:33:07
 */
public class TSPBruteAlgorithm {
  private final TSPMap map;
  public TSPBruteAlgorithm(TSPMap map) {
    this.map = map;
  }
  public TSPPath solve() {
    ArrayList paths = new ArrayList();
    TSPPath path = new TSPPath(map);
    path.add(0, 0);
    path.add(1, 1);
    paths.add(path);
    for (int i = 2; i < map.size(); i++) {
      ArrayList newPaths = new ArrayList();
      for (int p = 0; p < paths.size(); p++) {
        path = (TSPPath) paths.get(p);
        for (int idx = 0; idx < path.size(); idx++) {// NOTE: +1
          TSPPath newPath = new TSPPath(path);
          newPath.add(idx, i);
          newPaths.add(newPath);
//               LOG.report(this, "newPath=" + newPath);
        }
      }
      paths = newPaths;
    }
//      LOG.report(this, "final paths=" + paths);
    for (int i = 0; i < paths.size(); i++) {
      ((TSPPath) paths.get(i)).loadDistance();
    }
    Object[] arr = paths.toArray();
    Arrays.sort(arr, new Comparator() {
      public int compare(Object a, Object b) {
        return Double.compare(((TSPPath) a).distance(), ((TSPPath) b).distance());
      }
    });
    return (TSPPath) arr[0];
  }
}
