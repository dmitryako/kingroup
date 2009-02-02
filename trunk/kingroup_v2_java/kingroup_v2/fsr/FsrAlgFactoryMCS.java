package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.smc.MCSAlg;
import kingroup_v2.partition.smc.MCSAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/12/2005, Time: 14:03:38
 */
public class FsrAlgFactoryMCS implements FsrAlgFactory
{
  private MCSAlgModel model;
  public FsrAlgFactoryMCS()
  {
    model = new MCSAlgModel();
  }
  public PartitionAlg makeAlg(SysPop sysPop, Kingroup project) {
    model.setLoopBreaker(project.getSimpsAlgLoopBreaker());
    model.setNumIters(project.getSimpsAlgNumIter());
    model.setSimpsonAlg(false);
    model.setSibshipAlg(new DiploidSibship());
    return new MCSAlg(sysPop, model);
  }
}