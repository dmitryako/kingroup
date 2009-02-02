package kingroup_v2.partition.ms.window_only;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/06/2005, Time: 14:48:04
 */
public class SimpsWindOnlyAlg extends MSAlgV2 {
  public SimpsWindOnlyAlg(SysPop pop, MSAlgModel model) {
    super(pop, model);
    order = new SimpsRandomOrder(pop);
  }
}
