package kingroup_v2.partition.ms;
import kingroup.algorithm.window.PairArray;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistAlg;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/05/2005, Time: 16:20:25
 */
public class MSAlgPairs extends PairArray
{
  private static ProjectLogger log = ProjectLogger.getLogger(MSAlgPairs.class.getName());
  private double avrDist;

  public MSAlgPairs(SysPop pop, GenotypeDistAlg distAlg)
  {
    int n = pop.size();
    int arrSize = n * (n - 1) / 2;
    Object[] arr = new Object[arrSize];
    int idx = 0;
    int count = 0;
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < r; c++) {
        count++;
        double dist = distAlg.calcGenotypeDistance(pop, c, r);

        // DEBUGGING
//        int DISPLAY_LIMIT = 20;
//        if (count < DISPLAY_LIMIT) {
//          log.info(IOx.eol
//            + pop.toString(r) + IOx.eol
//            + pop.toString(c) + IOx.eol
//            + "dist="+(float)dist
//          );
//        }

        arr[idx++] = new MSAlgDistance(dist, c, r);
        avrDist += dist;
      }
    }
    avrDist /= arrSize;
//    log.info("mean=" + (float)avrDist);
    Arrays.sort(arr, new Comparator() {
      public int compare(Object a, Object b) {
        return Double.compare(((MSAlgDistance) a).dist, ((MSAlgDistance) b).dist);
      }
    });
    for (int i = 0; i < arr.length; i++)
      add(arr[i]);
//    log.info("All pairs=" + this);
  }
  public double getAvrDist() {return avrDist;}
}
