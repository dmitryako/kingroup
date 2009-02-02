package papers.kingroup2005c_limit;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup_v2.partition.smc.MCSAlg;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005c_limit.figure_5.LimitMCS;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2005, Time: 11:12:57
 */
public class LimitCalcChainMCS extends LimitMCS
{
  private static ProjectLogger log = ProjectLogger.getLogger(LimitCalcChainMCS.class.getName());
  private int countTrials = 0;

  public static void main(String[] args) {
    LimitCalcChainMCS test = new LimitCalcChainMCS();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public LimitCalcChainMCS() {
    ALG_MODEL.setNumIters(10000);

    N_ALLELES = 10;
    N_LOCI = 5;
    MIN_N_LOCI = N_LOCI;
    MAX_N_LOCI = N_LOCI;

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
    BUILDER_MODEL.setNumLoci(N_LOCI);

    N_TRIALS = 10;
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    MCSAlg method = new MCSAlg(sysPop, ALG_MODEL);
    Partition partA = PartitionFactory.makePartitionFrom(sysPop);
    Partition partB = method.partition(partA);

    countTrials++;
    String fileName = (ALG_NAME + "_U50_5x10S_NL" + N_LOCI + "_NA" + N_ALLELES
      + "_TRIAL" + countTrials + ".csv");
    LOG.saveToFile(IntVec.toCSV(method.getDistChain()), DIR, fileName);

    fileName = (ALG_NAME + "_U50_5x10S_NL" + N_LOCI + "_NA" + N_ALLELES
      + "_SimpsIdx_TRIAL" + countTrials + ".csv");
    LOG.saveToFile(Vec.toCSV(method.getSimpsIdxChain()), DIR, fileName);
    return partB;
  }
}