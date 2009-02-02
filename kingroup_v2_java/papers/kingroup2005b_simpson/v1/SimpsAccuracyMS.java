package papers.kingroup2005b_simpson.v1;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/05/2005, Time: 17:00:06
 */
public class SimpsAccuracyMS extends SimpsAccuracy {
  protected final MSAlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 10;
  public static void main(String[] args) {
    SimpsAccuracyMS test = new SimpsAccuracyMS();
    LOG.setTrace(true);
    test.setupP25();
    test.runP25();
    System.exit(0);
  }
  public SimpsAccuracyMS() {
    ALG_MODEL = new MSAlgModel();
  }
  public void setupP25() {
    POP_SIZE = 25;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1;
    MAX_NUM_LOCI = 10;
    N_TRIALS = 10; //100-paper

    WIND_SIZE_LIMIT = 10;
    DIR = "simps_win";
    DIR += "_W" + WIND_SIZE_LIMIT;
    FILE = "locus_min";
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
  protected double calcOneDistance(OldPop popA) {
//    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
//    Partition partA = factory.makeFromPopulation(popA);
//    LOG.trace(this, "A=", partA);

    SysPop sysPop = OldPopToSysPopFactory.makePopFrom(popA);
//    LOG.trace(this, "sysPop=\n", sysPop);
    sysPop = SysPopFactory.shuffle(sysPop);
    Partition partA = PartitionFactory.makePartitionFrom(sysPop);
//    LOG.trace(this, "partA=", partA);

    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    Partition partB = method.partition();
//    LOG.trace(this, "partB=", partB);
    int dist = new LitowDistance().distance(partA, partB);
//      LOG.trace(this, "D(A,B)=", Integer.toString(dist));
    return dist;
  }
}
