package kingroup_v2.partition.dr;
import kingroup.partition.bitsets.Partition;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
import javax.vecmathx.matrix.MtrxReadI;
import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/10/2005, Time: 09:58:35
 */
public class DRAlgPartition extends ArrayList //implements Comparable
{
  private static ProjectLogger log = ProjectLogger.getLogger(DRAlgPartition.class.getName());
  private double likeLog = 0; // LOG of total likelihood

  protected MtrxReadI inside; // pairwise likelihood for genotypes INSIDE the same group
  protected MtrxReadI between;

  public DRAlgPartition(MtrxReadI inside, MtrxReadI between) {
    this.inside = inside;
    this.between = between;
  }

  //shallowCopyClustersFrom
  public DRAlgPartition(DRAlgPartition from) {
    super(from);
    likeLog = from.likeLog;
    between = from.between;
    inside = from.inside;
  }

  public void addGenotypeToCluster(int clusterIdx, int genoIdx) {
//    log.info("Add geno=" + genoIdx + " to cluster=" + clusterIdx);

    // if clusterIdx is not a valid index, new group will be created
    DRAlgCluster cluster = null;
    if (clusterIdx >= size() || clusterIdx < 0) {  // create new cluster
      double val = calculateExcept(between, null, genoIdx);

//      log.info("  Between new cluster and other clusters log += " + val);
      likeLog += val;
      cluster = new DRAlgCluster();
      add(cluster);
    } else {
      cluster = getCluster(clusterIdx);
      double v = calculate(inside, cluster, genoIdx); // additional likelihood inside this group

//      log.info("  Inside cluster " + cluster + " log += " + v);
      likeLog += v;
      v = calculateExcept(between, cluster, genoIdx); // additional likel. to all groups except this group

//      log.info("  Between other cluster log += " + v);
      likeLog += v;
    }
    cluster.set(genoIdx);

//    log.info("Result group = " + cluster);
//    log.info("Result partition = " + this);
  }

  public DRAlgCluster getCluster(int clusterIdx) {
    return (DRAlgCluster)get(clusterIdx);
  }

  private double calculateExcept(MtrxReadI pairwiseL
    , DRAlgCluster excluded, int genoIdx) {
    double v = 0;
    for (int i = 0; i < size(); i++) {
      DRAlgCluster cluster = getCluster(i);
      if (excluded != null && cluster.equals(excluded))
        continue; // all except excluded
      double newL = calculate(pairwiseL, cluster, genoIdx);
      v += newL;
    }
    return v;
  }

  private double calculate(MtrxReadI pairwiseL
    , DRAlgCluster cluster, int genoIdx) {
    double v = 0;
    for (int idx = -1; ; ) {
      idx = cluster.nextSetBit(idx + 1); // OLD// cluster.get(i);
      if (idx == -1)
        break;
      v += pairwiseL.get(idx, genoIdx);
//      log.info("+ log(" + idx + "," + genoIdx + ")="
//        + (float)pairwiseL.getLog(idx, genoIdx));
    }
    return v;
  }

  public double getLog() {
    return likeLog;
  }

  public Partition toPartition() {
    Partition res = new Partition();
    int sampleSize = -1;
    for (int i = 0; i < size(); i++) {
      DRAlgCluster cluster = getCluster(i);
      CompBitSet set = new CompBitSet(cluster);
      res.add(set);
      if (sampleSize < set.length())
        sampleSize = set.length();
    }
    res.setSampleSize(sampleSize);
    return res;
  }
}
