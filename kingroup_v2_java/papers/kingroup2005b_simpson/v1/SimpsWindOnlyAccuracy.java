package papers.kingroup2005b_simpson.v1;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.ms.window_only.SimpsWindOnlyAlg;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/06/2005, Time: 14:41:53
 */
public class SimpsWindOnlyAccuracy extends SimpsAccuracyMS {
  private final MSAlgModel ALG_MODEL;
  public static void main(String[] args) {
    SimpsWindOnlyAccuracy test = new SimpsWindOnlyAccuracy();
    LOG.setTrace(true);
    test.runP25();
    System.exit(0);
  }
  public SimpsWindOnlyAccuracy() {
    N_TRIALS = 100; //100-paper
    WIND_SIZE_LIMIT = 10;
    DIR = "simps_window_only";
    DIR += "_W" + WIND_SIZE_LIMIT;

    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
  protected double calcOneDistance(OldPop popA) {
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    MSAlgV2 method = new SimpsWindOnlyAlg(pop, ALG_MODEL);
    Partition partB = method.partition();
//      LOG.trace(this, "B=", partB);
    int dist = new LitowDistance().distance(partA, partB);
//      LOG.trace(this, "D(A,B)=", Integer.toString(dist));
    return dist;
  }
}