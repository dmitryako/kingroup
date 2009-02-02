package kingroup.partition;
import kingroup.genetics.KinshipLikeMtrxV1;
import kingroup.genetics.Like;
import kingroup.genotype.Genotype;

import javax.langx.SysProp;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 10:57:59
 */
public class DRAlgPartitionV1 extends PartitionV2
  implements Comparable {
  private double likeLog = 0; // LOG of total likelihood
  protected KinshipLikeMtrxV1 inside_; // pairwise likelihood for genotypes INSIDE the same group
  protected KinshipLikeMtrxV1 between_;
  public DRAlgPartitionV1(KinshipLikeMtrxV1 inside, KinshipLikeMtrxV1 between) {
    inside_ = inside;
    between_ = between;
  }
  public DRAlgPartitionV1 duplicate() {
    DRAlgPartitionV1 res = new DRAlgPartitionV1(inside_, between_);
    res.deepCopyFrom(this);
//      LOG.trace(this, "from=", this);
//      LOG.trace(this, "dupl=", res);
    return res;
  }
  public void deepCopyFrom(DRAlgPartitionV1 from) {
    deepCopyPartitonV2From(from); // deep copy
    inside_ = from.inside_;
    between_ = from.between_;
    likeLog = from.likeLog;
  }
  final public double getLog() {
    return likeLog;
  }
  final public boolean isPossible() {
    return (likeLog != Like.MIN_LOG);
  }
  final public int compareTo(Object o) {
    PartitionSequence tmp = (PartitionSequence) o;
    if (tmp.getLog() == this.getLog())
      return 0;
    if (tmp.getLog() < this.getLog())
      return -1;
    return 1;
  }
  public void addGenotypeToCluster(int clusterIdx, int genoIdx) {
//      LOG.trace(this, "Add geno=" + genoIdx + " to cluster=" + clusterIdx);
    // if op is not a valid index, new group will be created
    DRAlgClusterV1 group = null;
    if (clusterIdx >= size() || clusterIdx < 0) {  // create new cluster
      double val = calculateExcept(between_, null, genoIdx);
//         LOG.trace(this, "  Between new cluster and other clusters log += " + val);
      likeLog += val;
      group = new DRAlgClusterV1();
      add(group);
    } else {
      group = getCluster(clusterIdx);
      double v = calculate(inside_, group, genoIdx); // additional likelihood inside this group
//         LOG.trace(this, "  Inside cluster " + group + " log += " + v);
      likeLog += v;
      v = calculateExcept(between_, group, genoIdx); // additional likel. to all groups except this group
//         LOG.trace(this, "  Between other cluster log += " + v);
      likeLog += v;
    }
    group.add(genoIdx);
//      LOG.trace(this, "Result group = " + group);
//      LOG.trace(this, "Result partition = " + this);
  }
  private double calculateExcept(KinshipLikeMtrxV1 pairwiseL
    , DRAlgClusterV1 excluded, int genoIdx) {
    double v = 0;
    for (int i = 0; i < size(); i++) {
      DRAlgClusterV1 group = getCluster(i);
      if (excluded != null && group == excluded)
        continue; // all except excluded
      double newL = calculate(pairwiseL, group, genoIdx);
      v += newL;
    }
    return v;
  }
  private double calculate(KinshipLikeMtrxV1 pairwiseL
    , DRAlgClusterV1 cluster, int genoIdx) {
    double v = 0;
    for (int i = 0; i < cluster.size(); i++) {
      int idx = cluster.get(i);
      v += pairwiseL.getLog(idx, genoIdx);
//         LOG.trace(this, "+ log(" + idx + "," + genoIdx + ")="
//                 + (float)pairwiseL.getLog(idx, genoIdx));
    }
    return v;
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append("Log=").append(Float.toString((float) likeLog));
    res.append(SysProp.EOL);
    res.append(super.toString());
    return res.toString();
  }
  public Genotype getGenotype(int genoIdx) {
    return inside_.getGenotypeData().getGenotype(genoIdx);
  }
}
