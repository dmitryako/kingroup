package papers.kingroup2005c_limit;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.smc.MCSAlg;
import kingroup_v2.partition.smc.MCSAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005c_ms2.MS2DiploidSIMPS;

import javax.iox.LOG;
import javax.utilx.arrays.IntVec;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/08/2005, Time: 10:32:44
 */
public class LimitCalcChainMCS_old extends MS2DiploidSIMPS {
  private final MCSAlgModel ALG_MODEL;
  static int countTrials = 0;

  public static void main(String[] args) {
    LimitCalcChainMCS_old test = new LimitCalcChainMCS_old();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public LimitCalcChainMCS_old() {
    DIR = "papers" + File.separator + "kingroup2005c_limit" + File.separator + "output";

    N_ALLELES = 10;
    N_LOCI = 5;
    MIN_NUM_LOCI = N_LOCI;
    MAX_NUM_LOCI = N_LOCI;
    SIMPS_ITERATIONS = 10000; // 100,000-paper
    N_TRIALS = 10; // 100-paper
    FILE = "MCS";

    ALG_MODEL = new MCSAlgModel();
    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);
  }
  public void run_P50() {
    FILE += "_U" + POP_SIZE;
//      calcAccuracyByLoci(DIR, FILE+"_50x1S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_50x1, false, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_25x2S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_25x2, false, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_10x5S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_10x5, false, N_ALLELES);
    calcAccuracyByLoci(DIR, FILE + "_5x10S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_5x10, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_2x25S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_2x25, false, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_1x50S_NA"+N_ALLELES +".csv"
//              , ButlerFamilyData.BUTLER_1x50, false, N_ALLELES);
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    MCSAlg method = new MCSAlg(pop, ALG_MODEL);
    Partition partB = method.partition(partA);
    countTrials++;
    String fileName = (FILE + "_5x10S_NL" + N_LOCI + "_NA" + N_ALLELES
      + "_TRIAL" + countTrials + ".csv");
    LOG.saveToFile(IntVec.toCSV(method.getDistChain()), DIR, fileName);
    int dist = new LitowDistance().distance(partA, partB);
//      if (dist != 0) {
//         LOG.trace(this, "A=", partA);
//         LOG.trace(this, "B=", partB);
//         LOG.trace(this, "D(A,B)=", Integer.toString(dist));
//      }
    return dist;
  }
}