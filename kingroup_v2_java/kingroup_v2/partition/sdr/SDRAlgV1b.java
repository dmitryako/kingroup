package kingroup_v2.partition.sdr;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.dr.DRAlg;
import kingroup_v2.partition.dr.DRAlgPartition;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/02/2006, Time: 10:40:19
 */
public class SDRAlgV1b extends PartitionAlg
{
  private static ProjectLogger log = ProjectLogger.getLogger(SDRAlgV1b.class.getName());
//  public static final String REFERENCE = "Konovalov DA 4th Asia-Pacific Bioinformatics Conference \n" +
//    "(APBC2006) Taiwan, Taipei, 13-16 Feb, 2006, in press";
//  public static final String REFERENCE = "work in progress";
  private final SysPop pop;
  private final SDRAlgModelV1 model;

  private static int MIN_VALID_SIMPS_GROUP = 3;

  public SDRAlgV1b(SysPop pop, SDRAlgModelV1 model) {
    this.pop = pop;
    this.model = model;
  }

  public Partition partition()
  {
    MS2AlgV2 method = new MS2AlgV2(pop, model.getMs2AlgModel());
    Partition simps = method.partition();
//    log.info("simps=" + simps);

    // PREPARE FOR DR
    DRAlg dr = new DRAlg(pop, model.getDrAlgModel());
//    DRAlg dr = new SDRAlgV2(pop, model.getDrAlgModel());
    DRAlgPartition simpsPart = dr.getPartition();

    int groupIdx = 0;
    CompBitSet pool = new CompBitSet();
    pool.set(0, pop.size());  // set all as unassigned

    Object[] sortedGroups = simps.sortBySize();
    BitSet group = (BitSet) sortedGroups[0];
//    log.info("bestSimpsGroup=" + bestSimpsGroup);

    if (group.cardinality() >= MIN_VALID_SIMPS_GROUP)
    {
      pool.xor(group); // mark as assigned
      for (int idx = -1; ;) {
        idx = group.nextSetBit(idx + 1);
        if (idx == -1)
          break;
        simpsPart.addGenotypeToCluster(groupIdx, idx);
      }
      log.info("simpsPart=" + simpsPart      );
    }

    if (pool.cardinality() == 0)
      return simps;
    dr.setUnassignedPool(pool);

//    MSAlgGroups groups = new MSAlgGroups(sortedGroups);
//    AlgWinAccessOrder order = new AlgAccessViaGroups(pop.size(), groups);
//    dr.setOrder(order);
//    log.info("groups=" + groups.toString(pop));

    return dr.partition();
  }

}
