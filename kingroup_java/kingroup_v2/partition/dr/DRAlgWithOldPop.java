package kingroup_v2.partition.dr;
import kingroup.genetics.KinshipRatioMtrxV1;
import kingroup.genotype.GenotypeGroup;
import kingroup.partition.PartitionMethodV2;

import java.util.BitSet;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/06/2005, Time: 15:33:42
 */
public class DRAlgWithOldPop extends PartitionMethodV2 {
  public static final String REFERENCE = "Konovalov et al. (2004) Molecular Ecology Notes 4, p779-782";
  private DRAlgAccessOrder order;
  public final static int LIMIT_ATTEMPTS = 1;
  public void init(KinshipRatioMtrxV1 pr) {
    order = new DRAlgAccessOrder(pr);
  }
  public int getNumAttempts(GenotypeGroup pool) {
    return Math.min(LIMIT_ATTEMPTS, pool.size());
  }
  public boolean hasNext() {
    return order.hasNext();
  }
  public int nextIdx() {
    return order.nextIdx();
  }
  public void setUnassignedPool(BitSet pool) {
    order.setUnassignedPool(pool);
  }
}