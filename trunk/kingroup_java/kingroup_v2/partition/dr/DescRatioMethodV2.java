package kingroup_v2.partition.dr;
import kingroup.KinGroupError;
import kingroup.genetics.KinshipRatioMtrxV1;
import kingroup.genotype.GenotypeGroup;
import kingroup.partition.PartitionMethodV2;
import kingroup.partition.PartitionV2DescRatioPairs;
import kingroup.partition.PartitionV2Pool;

import javax.iox.LOG;
import javax.utilx.pair.Int2;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 13:55:48
 */
public class DescRatioMethodV2 extends PartitionMethodV2 {
  private PartitionV2Pool pool_;
  private PartitionV2DescRatioPairs pairs_;
  public final static int LIMIT_ATTEMPTS = 1;
  protected int currPair_ = -1;
  public void init(KinshipRatioMtrxV1 pr) {
    pool_ = new PartitionV2Pool(pr.getGenotypeData().size()); // 3. setup unassigned pool of genotypes
    pairs_ = new PartitionV2DescRatioPairs(pr);
    currPair_ = -1;
  }
  public int getNumAttempts(GenotypeGroup pool) {
    return Math.min(LIMIT_ATTEMPTS, pool.size());
  }
  public boolean hasNext() {
    return (pool_.size() > 0);
  }
  public int nextIdx() {
    int res = currPair_;
    if (pool_.size() == 1) {
      currPair_ = -1;
      res = pool_.firstInt();
    } else if (pool_.size() == 0) {
      String mssg = "pool.size() == 0";
      LOG.error(this, mssg, "");
      throw new KinGroupError(mssg);
    } else if (currPair_ != -1) {
      currPair_ = -1;
    }
//      else if (pool_.size() == 2) {
//         res = random(pool_.firstInt(), pool_.lastInt());
//      }
    else {
      Int2 pair = pairs_.removeFirstPair();
      res = random(pair.b, pair.a);
      currPair_ = pair.pair(res);
    }
    if (!pool_.removeInt(res)) {
      String mssg = "!pool_.removeInt(res)";
      LOG.error(this, mssg, "");
      throw new KinGroupError(mssg);
    }
    return res;
  }
  private int random(int g, int g2) {
    return (random_.nextInt(2) == 0) ? g : g2;
  }
}