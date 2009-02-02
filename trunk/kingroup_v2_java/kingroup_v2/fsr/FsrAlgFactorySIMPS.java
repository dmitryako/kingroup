package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/11/2005, Time: 15:34:55
 */
public class FsrAlgFactorySIMPS implements FsrAlgFactory
{
  private SIMPS2AlgModel model;
  public FsrAlgFactorySIMPS()
  {
    model = new SIMPS2AlgModel();
  }
  public PartitionAlg makeAlg(SysPop sysPop, Kingroup project) {
    model.setSibshipAlg(new DiploidSibship());
    model.setLoopBreaker(project.getSimpsAlgLoopBreaker());
    model.setNumIters(project.getSimpsAlgNumIter());
    return new SIMPS2Alg(sysPop, model);
  }
}
