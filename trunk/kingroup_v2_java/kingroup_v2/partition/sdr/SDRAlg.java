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
 * User: jc138691, Date: 12/10/2005, Time: 15:06:19
 */
public class SDRAlg  extends PartitionAlg
{
  private static ProjectLogger log = ProjectLogger.getLogger(SDRAlg.class.getName());
//  public static final String REFERENCE = "Konovalov DA 4th Asia-Pacific Bioinformatics Conference \n" +
//    "(APBC2006) Taiwan, Taipei, 13-16 Feb, 2006, in press";
//  public static final String REFERENCE = "work in progress";
  private final SysPop pop;
  private final SDRAlgModel model;

  private static int MIN_VALID_SIMPS_GROUP = 3;

  public SDRAlg(SysPop pop, SDRAlgModel model) {
    this.pop = pop;
    this.model = model;
  }

  public Partition partition()
  {
    // SIMPSON
//    MS2AlgV2 method = new MS2AlgV2(simpsPop);
    MS2AlgV2 method = new MS2AlgV2(pop, model.getMs2AlgModel());
    Partition simps = method.partition();
//    log.info("simps=" + simps);

    Object[] sortedGroups = simps.sortBySize();
    BitSet bestSimpsGroup = (BitSet) simps.sortBySize()[0];
//    log.info("bestSimpsGroup=" + bestSimpsGroup);

    // PREPARE FOR DR
//    DRAlg dr = new DRAlg(pop, model.getDrAlgModel());
    DRAlg dr = new SDRAlgV2(pop, model.getDrAlgModel());
    DRAlgPartition simpsPart = dr.getPartition();

    boolean keepLargest = model.getKeepLargest();
    int groupIdx = 0;
    CompBitSet pool = new CompBitSet();

//    for (Iterator it = simps.iterator(); it.hasNext();) {
//      ComparableBitSet group = (ComparableBitSet) it.next();

    for (int i = 0; i < sortedGroups.length; i++)
    {
      CompBitSet group = (CompBitSet) sortedGroups[i];

      if (group.cardinality() < MIN_VALID_SIMPS_GROUP) {
        pool.or(group); // store all unassigned
        continue; // ignore
      }
//      if (keepLargest && group.cardinality() < bestSimpsGroup.cardinality()) {
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
    dr.setUnassignedPool(pool);
    return dr.partition();
  }

}
