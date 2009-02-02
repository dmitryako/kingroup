package papers.inpress.kingroup2006_apbc.v2.figure_2;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.sdr.SDRAlgV2;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/01/2006, Time: 12:26:51
 */
public class APBC2006_AccuracySDR  extends APBC2006_AccuracyDR
{
  private static ProjectLogger log = ProjectLogger.getLogger(APBC2006_AccuracySDR.class.getName());
//  private SDRAlgModelV1 ALG_MODEL;
  public static void main(String[] args) {
    APBC2006_AccuracySDR test = new APBC2006_AccuracySDR();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public APBC2006_AccuracySDR()
  {
    ALG_NAME = "SDR";
//    ALG_MODEL = new SDRAlgModelV1();
//
//    MS2AlgModel ms2 = new MS2AlgModel();
//    ms2.setSibshipAlg(new DiploidSibship());
//    ms2.setGenotypeDistAlg(new GenotypeDistLocusAvr());
//
//    DRAlgModel dr = new DRAlgModel();
//    dr.setPrimIBDModel(KinshipIBDFactory.makeFullSib());
//    dr.setNullIBDModel(KinshipIBDFactory.makeUnrelated());
//
//    ALG_MODEL.setDrAlgModel(dr);
//    ALG_MODEL.setMs2AlgModel(ms2);
//    ALG_MODEL.setKeepLargest(true);
//
//    POP_SIZE = 50;
//    N_TRIALS = 100; // 100-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
//    PartitionAlg method = new SDRAlgV2(sysPop, ALG_MODEL);
    PartitionAlg method = new SDRAlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}

