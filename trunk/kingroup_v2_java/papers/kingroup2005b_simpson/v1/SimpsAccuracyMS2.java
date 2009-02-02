package papers.kingroup2005b_simpson.v1;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms2.MS2AlgModel;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/06/2005, Time: 10:27:14
 */
public class SimpsAccuracyMS2 extends SimpsAccuracyMS {
  private final MS2AlgModel ALG_MODEL;
  public static void main(String[] args) {
    SimpsAccuracyMS2 test = new SimpsAccuracyMS2();
    LOG.setTrace(true);
    test.runP25();
    System.exit(0);
  }
  public SimpsAccuracyMS2() {
    POP_SIZE = 25;
    POP_MODEL.setSize(POP_SIZE);
    MIN_NUM_LOCI = 1;
    MAX_NUM_LOCI = 10;
    N_TRIALS = 10; //100-paper
    DIR = "simps_win_v2";

    ALG_MODEL = new MS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    MS2AlgV2 method = new MS2AlgV2(pop, ALG_MODEL);
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
