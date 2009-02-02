package kingroup_v2.partition.ms2;
import kingroup.algorithm.window.AlgWinAccessOrder;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.simpson.MSAlgGroup;
import kingroup_v2.pop.sample.sys.SysPop;

import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/01/2006, Time: 14:31:27
 */
public class MS2Alg extends PartitionAlg {
  public static final String REFERENCE = "Konovalov (2006) Proceedings of 4th Asia-Pacific Bioinformatics Conference, " +
    " Taiwan, Taipei, pp7-16";
//  public static final String REFERENCE = "work in progress";
  private final SysPop pop;
  protected AlgWinAccessOrder order;
  private final MS2AlgModel model;
  public MS2Alg(SysPop pop, MS2AlgModel model, AlgWinAccessOrder order) {
    this.pop = pop;
    this.model = model;
    this.order = order;
    assert(pop.size() > 0);
    assert(pop.getNumLoci() > 0);
  }
  public Partition partition() {
    int count = 0;
    MS2Partition curr = null;
    while (order.hasNext()) 
    {
      int idIdx = order.nextIdx();
      if (curr == null) {// VERY FIRST PARTITION WITH ONE INDIVIDUAL
        MSAlgGroup group = new MSAlgGroup();
        group.set(idIdx);
        curr = new MS2Partition(pop.size());
        curr.add(group);
        continue;
      }

//         LOG.report(this, "curr=" + curr);
      Object[] bySize = curr.sortBySize();
      for (int j = 0; j < bySize.length + 1; j++) { // NOTE +1!!!!
        MSAlgGroup baseGroup = null;
        MSAlgGroup group;
        if (j < bySize.length) {
          baseGroup = (MSAlgGroup) bySize[j];
          group = new MSAlgGroup(baseGroup); // DEEP COPY!!!!
        } else {
          group = new MSAlgGroup();  // one group must have the individual on its own.
        }
//            LOG.report(this, "STEP 5: group=" + group);
        group.set(idIdx);
//            LOG.report(this, "STEP 5: group.set(" + idIdx);
//            LOG.report(this, "STEP 5: group=" + group);

//            if (group.cardinality() > 2) { //STEP 6: check sibship
//              //used this check to calculate partition space in ModSimps paper
        if (!model.getSibshipAlg().isSibGroupSLOW(pop, group))
          continue;
        if (baseGroup != null)
          curr.remove(baseGroup); // unlock base group
//            LOG.report(this, "curr.remove(baseGroup)=" + curr);
        curr.add(group);
//            LOG.report(this, "curr.addLine(group)=" + curr);
        int index = curr.calcSimpsonIndex();//STEP 7: calc simpson index
        curr.setSimpsonIdx(index);
        break;
      }
    }
    return curr;
  }
  public void setAssignedPool(BitSet done) {
    order.setAssignedPool(done);
  }
}
