package kingroup.partition;
import kingroup.genetics.KinshipRatioMtrxV1;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeGroup;

import javax.utilx.RandomSeed;
public abstract class PartitionMethod {
  protected static RandomSeed random_ = RandomSeed.getInstance();
  public void init() {
  }
  abstract public Genotype start(GenotypeGroup pool, KinshipRatioMtrxV1 pr);
  abstract public Genotype getNextGenotype(GenotypeGroup pool, KinshipRatioMtrxV1 pr);
  abstract public int getNumAttempts(GenotypeGroup pool);
}