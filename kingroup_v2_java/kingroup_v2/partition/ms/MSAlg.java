package kingroup_v2.partition.ms;
import kingroup.algorithm.window.AlgWinAccessOrder;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.simpson.MSAlgGroup;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/01/2006, Time: 09:55:49
 */
public class MSAlg extends PartitionAlg {
  private static ProjectLogger log = ProjectLogger.getLogger(MSAlgV2.class.getName());
  public static final String REFERENCE = "Konovalov et al. (2005) BIOINFORMATICS 21, pp. 3912-3917";
  private final SysPop pop;
  protected AlgWinAccessOrder order;
  private final MSAlgModel model;
  public MSAlg(SysPop pop, MSAlgModel model, AlgWinAccessOrder order) {
    this.pop = pop;
    this.model = model;
    this.order = order;
    assert(pop.size() > 0);
    assert(pop.getNumLoci() > 0);
  }
  public Partition partition() {
    int count = 0;
    MSAlgPartition[] win = new MSAlgPartition[model.getWindSize()];
    int currWindSize = 0;
    //STEP 1: calculate all getGenotype distances
    //STEP 2: sort pairs in ascending order of distances
    //STEP 3: create pool of all individuals
    //STEP 4: select nextIdx unassigned
    while (order.hasNext())
    {
      int idIdx = order.nextIdx();
      if (currWindSize == 0) {// VERY FIRST PARTITION WITH ONE INDIVIDUAL
        MSAlgGroup group = new MSAlgGroup();
        group.set(idIdx);
        MSAlgPartition newPart = new MSAlgPartition(pop.size());
        newPart.add(group);
        win[currWindSize++] = newPart;
        continue;
      }
      //STEP 5: all new partitions from the currently stored
      ArrayList tempStored = new ArrayList();
      for (int i = 0; i < currWindSize; i++) {
        MSAlgPartition curr = win[i];
//            LOG.report(this, "curr=" + curr);
        for (int groupIdx = 0; groupIdx < curr.size() + 1; groupIdx++) { // NOTE +1!!!!
          MSAlgPartition newPart = new MSAlgPartition(curr, true); // make a fresh DEEP copy
//               LOG.report(this, "STEP 5: newPart=" + newPart);
          MSAlgGroup group = newPart.remove(groupIdx); // unlock the group
          if (group == null)
            group = new MSAlgGroup();  // one group must have the individual on its own.
//               LOG.report(this, "STEP 5: group=" + group);
          group.set(idIdx);
//               LOG.report(this, "STEP 5: group.set(" + idIdx);
//               LOG.report(this, "STEP 5: group=" + group);

          //STEP 6: check sibship
          //if (group.cardinality() > 2) {
          boolean oldSib = model.getSibshipAlg().isSibGroupSLOW(pop, group);
          if (!oldSib)
            continue;
          newPart.add(group);
          tempStored.add(newPart);
          //STEP 7: calc simpson index
          int index = newPart.calcSimpsonIndex();
          newPart.setSimpsonIdx(index);
//          log.info(IOx.eol + newPart);
        }
      }
      //STEP 8: sort by descening index
      Object[] arr = tempStored.toArray();
      Arrays.sort(arr, new Comparator() {
        public int compare(Object a, Object b) {
          return ((MSAlgPartition) b).getSimpsonIdx() - ((MSAlgPartition) a).getSimpsonIdx();
        }
      });
      //STEP 9: retain WIND_SIZE_LIMIT best partitions
      currWindSize = Math.min(model.getWindSize(), arr.length);
      for (int j = 0; j < currWindSize; j++) {
        win[j] = (MSAlgPartition) arr[j];
//        log.info("win["+j+"].simpson index=" + win[j].getSimpsonIdx());
      }
    }
    return win[0];
  }
}
