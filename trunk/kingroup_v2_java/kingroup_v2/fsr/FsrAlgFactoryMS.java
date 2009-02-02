package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/11/2005, Time: 15:30:43
 */
public class FsrAlgFactoryMS implements FsrAlgFactory
{
  private MSAlgModel model;
  public FsrAlgFactoryMS()
  {
    model = new MSAlgModel();
    model.setSibshipAlg(new DiploidSibship());
    model.setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  public PartitionAlg makeAlg(SysPop sysPop, Kingroup project) {
    model.setWindSize(project.getMsAlgWindSize());
    return new MSAlgV2(sysPop, model);
  }
}
