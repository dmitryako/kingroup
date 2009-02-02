package papers.inpress.kingroup2006_apbc.v1.accuracy;
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
 * User: jc138691, Date: 28/06/2005, Time: 17:25:01
 */
public class APBCAccuracyMS2 extends APBCAccuracy
{
  private final MS2AlgModel ALG_MODEL;
  public static void main(String[] args) {
    APBCAccuracyMS2 test = new APBCAccuracyMS2();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public APBCAccuracyMS2() {
    ALG_MODEL = new MS2AlgModel();
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    ALG_MODEL.setSibshipAlg(new DiploidSibship());

    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 15;//15-paper
    N_TRIALS = 100; // 100-paper
    FILE = "MS2";
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
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
