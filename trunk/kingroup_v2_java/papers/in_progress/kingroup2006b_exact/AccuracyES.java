package papers.in_progress.kingroup2006b_exact;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.exact.SimpsBKAlg;
import kingroup_v2.partition.simpson.exact.SimpsExactAlgModel;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005b_simpson.v1.SimpsAccuracyMS;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/07/2005, Time: 18:41:50
 */
public class AccuracyES extends SimpsAccuracyMS {
  private final SimpsExactAlgModel ALG_MODEL;
  public static void main(String[] args) {
    AccuracyES test = new AccuracyES();
    LOG.setTrace(true);
    test.setupTest_P10();
    test.runTest_P10();
//      test.setupP25();
//      test.runP25();
    System.exit(0);
  }
  public AccuracyES() {
    ALG_MODEL = new SimpsExactAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  public void setupTest_P10() {
    POP_SIZE = 10;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 15;//15-paper
    N_TRIALS = 2; // 100-paper
    DIR = "exact_simps";
    FILE = "ES_P" + POP_SIZE;
  }
  public void runTest_P10() {
    calcAccuracyByLoci(DIR, FILE + "_2x5S_NA" + N_ALLELES + ".csv"
      , ButlerFamilyData.BUTLER_2x5, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_1x10S_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_1x10, false, N_ALLELES);
//      calcAccuracyByLoci(DIR, FILE+"_5x5S_NA"+N_ALLELES+".csv"
//              , ButlerFamilyData.BUTLER_5x5, false, N_ALLELES);
  }
  public void setupP25() {
    POP_SIZE = 25;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1;
    MAX_NUM_LOCI = 10;
    N_TRIALS = 100; //100-paper
    DIR = "exact_simps";
    FILE = "ES_P" + POP_SIZE;
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
//      LOG.trace(this, "popA=", popA);
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
//      LOG.trace(this, "pop=", pop);
//      SimpsExactAlg method = new SimpsExactAlg(pop);
    SimpsBKAlg method = new SimpsBKAlg(pop, ALG_MODEL);
    Partition partB = method.partition();
    int dist = new LitowDistance().distance(partA, partB);
//      if (dist != 0) {
//         LOG.trace(this, "A=", partA);
//         LOG.trace(this, "B=", partB);
//         LOG.trace(this, "D(A,B)=", Integer.toString(dist));
//      }
    return dist;
  }
}
