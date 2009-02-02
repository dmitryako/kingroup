package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.sdr.SDRAlg;
import kingroup_v2.partition.sdr.SDRAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/11/2005, Time: 15:32:06
 */
public class FsrAlgFactorySDR   implements FsrAlgFactory
{
  private SDRAlgModel model;
  public FsrAlgFactorySDR()
  {
    model = new SDRAlgModel();
  }
  public PartitionAlg makeAlg(SysPop sysPop, Kingroup project) {
    model.getDrAlgModel().setKinship(project.getKinship());
    return new SDRAlg(sysPop, model);
  }
}
