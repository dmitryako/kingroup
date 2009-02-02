package kingroup_v2.kinship.like;
import junit.framework.TestCase;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/04/2006, Time: 13:05:21
 */
public class KinshipRMaxLikeEstimatorTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private static double EPS = 1e-7;

  protected void setUp() {
  }

  public void testCalcDiploidLocus_full_sib() {
    int[][] alleleArr = {{0, 1}, {0, 1}};
    SysPop pop = SysPopFactory.makeTestPopFrom(alleleArr);
    log.info("\nfreq=" + pop.getFreq());
    log.info("\npop=" + pop);

    KinshipRMaxLikeEstimator estim = new KinshipRMaxLikeEstimator(pop);
    estim.calc(0, 1);

//    assertEquals(0.015625, fullSib.calcDiploid(dipAA_, dipBB_), EPS);// 0.0156 from Kinship
//    assertEquals(0.015625, fullSib.calcProb(), EPS);
  }

}
