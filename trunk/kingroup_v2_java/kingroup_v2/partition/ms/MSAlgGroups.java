package kingroup_v2.partition.ms;
import kingroup_v2.partition.simpson.MSAlgGroup;
import kingroup_v2.partition.simpson.SibshipAlg;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistAlg;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
import javax.utilx.arrays.IntVec;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 15:44:58
 */
public class MSAlgGroups extends LinkedList<MSAlgGroup>
{
  private static ProjectLogger log = ProjectLogger.getLogger(MSAlgGroups.class.getName());
  public MSAlgGroups(Object[] groups)
  {
    for (int i = 0; i < groups.length; i++) {
      BitSet group = (BitSet)groups[i];
      MSAlgGroup sibGroup = new MSAlgGroup();
      sibGroup.or(group);
      add(sibGroup);
    }
  }
  public MSAlgGroups(SysPop pop
    , GenotypeDistAlg distAlg
    , SibshipAlg sibship)
  {
//    log.info("pop=\n" + pop);
    double avrDist = 0;
    int n = pop.size();
    double[][] dist = new double[n][n];
    int count = 0;
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < r; c++) {
        count++;
        double d = distAlg.calcGenotypeDistance(pop, c, r);
        dist[r][c] = d;
        dist[c][r] = d;
        avrDist += d;
//        log.info("d["+r+"]["+c+"]=" + (float)d + ", mean=" + (float)(avrDist/count));
      }
    }
    avrDist /= count;
//    log.info("distance=\n" + DoubleArr.toString(dist));

    MSAlgGroup done = new MSAlgGroup();
    Object[] mtrx = loadSorted(n, dist);
//    log.info("sorted=\n" + toString(mtrx));

    for (int i = 0; i < mtrx.length; i++) {
      if (done.cardinality() == pop.size())
        break;
      Object[] arr = (Object[])mtrx[i];
      MSAlgDistance p = (MSAlgDistance)arr[0];
      if (done.get(p.a) && done.get(p.b))
        continue; // both ids have been already assigned

      // build group
      MSAlgGroup sibGroup = new MSAlgGroup();  // one group must have the individual on its own.
      sibGroup.set(p.a);
      sibGroup.set(p.b);
      for (int k = 1; k < arr.length; k++) {
        MSAlgDistance p2 = (MSAlgDistance)arr[k];
        if (done.get(p2.a) && done.get(p2.b))
          continue; // both ids have been already assigned
        if (p2.dist >= avrDist)
          break;
        if (dist[p.b][p2.b] >= avrDist)
          break;
        MSAlgGroup tryGroup = new MSAlgGroup(sibGroup);  // putative
        tryGroup.set(p2.a);
        tryGroup.set(p2.b);
        if (!sibship.isSibGroupSLOW(pop, tryGroup))
          continue; // KEEP TRYING

        sibGroup.or(tryGroup);
//        log.info("group=\n" + sibGroup);
        done.or(sibGroup); // remember as done
      }
      add(sibGroup);
    }
//    log.info("All groups=" + toString(pop));
  }

  private String toString(Object[] mtrx)
  {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < mtrx.length; i++) {
      Object[] arr = (Object[])mtrx[i];
      for (int j = 0; j < arr.length; j++) {
        MSAlgDistance p = (MSAlgDistance)arr[j];
        buff.append(p);
      }
      buff.append(SysProp.EOL);
    }
    return buff.toString();
  }

  public String toString(SysPop pop)
  {
    StringBuffer buff = new StringBuffer();
    String str = super.toString();
    buff.append(str).append("\nby groups: ");
    for (int i = 0; i < size(); i++) {
      MSAlgGroup g = get(i);
      int[] arr = g.toArray();
      for (int j = 0; j < arr.length; j++) {
        int idIdx = arr[j];
        arr[j] = pop.getGroupId(idIdx);
      }
      buff.append(IntVec.toString(arr)).append(" ");
    }
    return buff.toString();
  }

  public Object[] loadSorted(int n, double[][] dist)
  {
    Object[] res = new Object[n];
    for (int r = 0; r < n; r++) {
      Object[] arr = new Object[n-1];
      int idx = 0;
      for (int c = 0; c < n; c++) {
        if (c == r)
          continue;
        double d = dist[c][r];
        arr[idx++] = new MSAlgDistance(d, c, r);
      }
      Arrays.sort(arr, new Comparator() {
        public int compare(Object a, Object b) {
          return Double.compare(((MSAlgDistance) a).dist, ((MSAlgDistance) b).dist);
        }
      });
      res[r] = arr;
    }

    Arrays.sort(res, new Comparator() {
      public int compare(Object a, Object b) {
        Object[] A = (Object[])a;
        Object[] B = (Object[])b;
        MSAlgDistance d = (MSAlgDistance)A[0];
        MSAlgDistance d2 = (MSAlgDistance)B[0];
        return Double.compare(d.dist, d2.dist);
      }
    });

    return res;
  }

}
