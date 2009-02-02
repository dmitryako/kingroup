package papers.kingroup2005c_limit.figure_5;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.jw.JWAlg_v2;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005b_simpson.v1.Efficiency;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/11/2005, Time: 15:36:57
 */
public class LimitJW extends LimitDist
{
  private static ProjectLogger log = ProjectLogger.getLogger(LimitJW.class.getName());

  
  public static void main(String[] args) {
    LimitJW test = new LimitJW();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public LimitJW() {
    N_TRIALS = 10; // 100-paper
    MIN_N_GROUPS = 3;
    FILE = "JW";
  }
  protected Efficiency calcEfficiency(OldPop popA)
  {
    log.info("popA=" + popA);

    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    log.info("partA=" + partA);

    SysPop pop = OldPopToSysPopFactory.makePopFrom(popA);
    log.info("pop=\n" + pop);

    JWAlg_v2 method = new JWAlg_v2(pop, popA.getAlleleFreq());
    method.setDebug(true);
    Partition partB = method.partition();
    log.info("partB=" + partB);

    int dist = new LitowDistance().distance(partA, partB);
    log.info("D(A,B)=" + dist);
    return new Efficiency(dist, 0, 0);
  }

}