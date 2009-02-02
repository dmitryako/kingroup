package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/11/2005, Time: 15:20:30
 */
public interface FsrAlgFactory
{
  public PartitionAlg makeAlg(SysPop sysPop, Kingroup project);
}
