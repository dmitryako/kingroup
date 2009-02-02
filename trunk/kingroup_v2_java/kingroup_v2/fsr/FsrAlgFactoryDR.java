package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.dr.DRAlg;
import kingroup_v2.partition.dr.DRAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/11/2005, Time: 15:33:56
 */
public class FsrAlgFactoryDR  implements FsrAlgFactory
{
  private DRAlgModel model;
  public FsrAlgFactoryDR()
  {
    model = new DRAlgModel();
  }
  public PartitionAlg makeAlg(SysPop sysPop, Kingroup project) {
    model.setKinship(project.getKinship());
    return new DRAlg(sysPop, model);
  }
}
