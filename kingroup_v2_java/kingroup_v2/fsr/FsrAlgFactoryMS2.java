package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/11/2005, Time: 15:21:22
 */
public class FsrAlgFactoryMS2 implements FsrAlgFactory
{
  public PartitionAlg makeAlg(SysPop sysPop, Kingroup project) {
    return new MS2AlgV2(sysPop);
  }
}
