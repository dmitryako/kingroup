package kingroup_v2.partition.dr;
import kingroup.algorithm.window.AlgWinAccessOrder;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.kinship.like.KinshipRatioMtrxAll;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/10/2005, Time: 08:51:04
 */
public class DRAlg extends PartitionAlg {
  public static final String REFERENCE = "Konovalov et al. (2004) Molecular Ecology Notes 4, p779-782";
  private static ProjectLogger log = ProjectLogger.getLogger(DRAlg.class.getName());
  private final SysPop pop;
//  private AlgAccessViaPairs order;
  private AlgWinAccessOrder order;
  private KinshipRatioMtrxAll mtrx;
  private DRAlgPartition part;

  public DRAlg(SysPop pop, DRAlgModel model) {
    this.pop = pop;
    mtrx = new KinshipRatioMtrxAll(pop);
    mtrx.calcComplexNull(model.getKinship());
    part = new DRAlgPartition(mtrx.getPrim(), mtrx.getNull());

    //todo: show progress?
    order = new DRAlgAccessOrder(mtrx.getRatio());
  }

  public AlgWinAccessOrder getOrder() {
    return order;
  }
  public void setOrder(AlgWinAccessOrder order) {
    this.order = order;
  }

  public Partition partition()
  {
    while (order.hasNext()) {
      int idx = order.nextIdx();
      if (idx == -1) {
        break;
      }
      DRAlgPartition best = null;
      for (int clusterIdx = 0; clusterIdx <= part.size(); clusterIdx++) {
        DRAlgPartition tmp = new DRAlgPartition(part); //shallowCopyClustersFrom

        // NOTE: clusterIdx == part.size() is used to create a new cluster
        if (clusterIdx < part.size())      // make a deep copy of this cluster
          tmp.set(clusterIdx, new DRAlgCluster(part.getCluster(clusterIdx)));
        tmp.addGenotypeToCluster(clusterIdx, idx); // if op is not a valid index, new group will be created
//        log.info("tmp=" + tmp);
        if (best == null  ||  best.getLog() < tmp.getLog())
        {
          best = tmp;
//          log.info("best=" + best);
        }
      }
      part = best; // remember the best partition
    }
    return part.toPartition();
  }

  public DRAlgPartition getPartition()
  {
    return part;
  }

  public void setUnassignedPool(CompBitSet pool)
  {
    order.setUnassignedPool(pool);
  }
}
