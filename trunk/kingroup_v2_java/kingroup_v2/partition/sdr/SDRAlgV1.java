package kingroup_v2.partition.sdr;
import kingroup.partition.DRAlgPartitionV1;
import kingroup.partition.PartitionEngineV2;
import kingroup.partition.PartitionModel;
import kingroup.partition.bitsets.Partition;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.dr.DRAlgWithOldPop;
import kingroup_v2.partition.ms2.MS2AlgModel;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
import java.util.BitSet;
import java.util.Iterator;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/06/2005, Time: 07:19:24
 */
public class SDRAlgV1 extends PartitionEngineV2
{
  private MS2AlgModel ms2Model;
  private static ProjectLogger log = ProjectLogger.getLogger(SDRAlgV1.class.getName());
  public static final String REFERENCE = "Konovalov DA 4th Asia-Pacific Bioinformatics Conference \n" +
    "(APBC2006) Taiwan, Taipei, 13-16 Feb, 2006, in press";
  private final OldPop popA;
  private int MIN_VALID_SIMPS_GROUP = 3;
  private boolean keepLargest = true;

  public SDRAlgV1(OldPop data
    , PartitionModel model
    , boolean keepLargest) {
    super(data, model);
    this.popA = data;
    this.keepLargest = keepLargest;
    ms2Model = new MS2AlgModel();
  }
  public Partition partition() {
    DRAlgPartitionV1 simpsPart = new DRAlgPartitionV1(pr_.getPrim()
      , pr_.getNull());
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
//      LOG.report(this, "popA=" + popA);
//      LOG.report(this, "pop=" + pop);
    MS2AlgV2 method = new MS2AlgV2(pop, ms2Model);
    Partition simps = method.partition();
    BitSet bestSimpsGroup = (BitSet) simps.sortBySize()[0];

//      LOG.report(this, "simps=" + simps.toString());
    int groupIdx = 0;
    CompBitSet pool = new CompBitSet();
    for (Iterator it = simps.iterator(); it.hasNext();) {
      CompBitSet group = (CompBitSet) it.next();
      if (group.cardinality() < MIN_VALID_SIMPS_GROUP) {
        pool.or(group); // store all unassigned
        continue; // ignore
      }
      if (keepLargest && group.cardinality() < bestSimpsGroup.cardinality()) {
        pool.or(group); // store all unassigned
        continue; // ignore
      }
      for (int idx = -1; ;) {
        idx = group.nextSetBit(idx + 1);
        if (idx == -1)
          break;
        simpsPart.addGenotypeToCluster(groupIdx, idx);
      }
      groupIdx++;
    }
//    log.info("simpsPart=" + simpsPart);

    if (pool.cardinality() == 0)
      return simps;
    DRAlgWithOldPop dr = new DRAlgWithOldPop();
    dr.init(pr_);
    dr.setUnassignedPool(pool);
    simpsPart = assignPartition(dr, simpsPart);
//      LOG.report(this, "assignPartition(dr, part)=" + part);
    Partition partB = simpsPart.toBitSetPartition();
//      LOG.trace(this, "B=", partB);
    return partB;
  }
}