package papers.inpress.kingroup2006_apbc.v2.figure_1;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.ms2.MS2AlgModel;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/01/2006, Time: 11:55:12
 */
public class APBC2006_EfficiencyMS2 extends Figure1
{
  private static ProjectLogger log = ProjectLogger.getLogger(APBC2006_EfficiencyMS2.class.getName());
  private final MS2AlgModel ALG_MODEL;
  public static void main(String[] args) {
    APBC2006_EfficiencyMS2 test = new APBC2006_EfficiencyMS2();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public APBC2006_EfficiencyMS2() {
    ALG_NAME = "MS2";
    ALG_MODEL = new MS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    N_TRIALS = 100; // 200-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    MS2AlgV2 method = new MS2AlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}
