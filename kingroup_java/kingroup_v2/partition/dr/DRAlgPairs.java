package kingroup_v2.partition.dr;
import kingroup.algorithm.window.PairArray;

import javax.vecmathx.matrix.MtrxReadI;
import java.util.Arrays;
import java.util.Comparator;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/06/2005, Time: 15:09:18
 */
public class DRAlgPairs extends PairArray {
  private static ProjectLogger log = ProjectLogger.getLogger(DRAlgPairs.class.getName());
  public DRAlgPairs(MtrxReadI pr) {
    int n = pr.size();
    int arrSize = n * (n - 1) / 2;
    Object[] arr = new Object[arrSize];
    int idx = 0;
    for (short r = 0; r < n; r++) {
      for (short c = 0; c < r; c++) {
        double log = pr.get(c, r);
        arr[idx++] = new DRAlgRatioLog(log, c, r);
      }
    }
    Arrays.sort(arr, new Comparator() {
      public int compare(Object a, Object b) {
        return Double.compare(((DRAlgRatioLog) b).log, ((DRAlgRatioLog) a).log);
      }
    });
    for (int i = 0; i < arr.length; i++)
      add(arr[i]);
    arr = null;
//    log.info("All pairs=" + this);
  }

}
