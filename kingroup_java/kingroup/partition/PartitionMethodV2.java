package kingroup.partition;
import kingroup.genetics.KinshipRatioMtrxV1;
import kingroup.genotype.GenotypeGroup;

import javax.utilx.RandomSeed;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 13:46:34
 */
public abstract class PartitionMethodV2 {
  protected static RandomSeed random_ = RandomSeed.getInstance();
  public void init(KinshipRatioMtrxV1 pr) {
  }
  abstract public int nextIdx();
  abstract public int getNumAttempts(GenotypeGroup pool);
  abstract public boolean hasNext();
}
