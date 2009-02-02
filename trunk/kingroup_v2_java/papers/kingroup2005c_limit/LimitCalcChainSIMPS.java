package papers.kingroup2005c_limit;
import javax.iox.LOG;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2005, Time: 11:54:21
 */
public class LimitCalcChainSIMPS  extends LimitCalcChainMCS
{
  private static ProjectLogger log = ProjectLogger.getLogger(LimitCalcChainSIMPS.class.getName());

  public static void main(String[] args) {
    LimitCalcChainSIMPS test = new LimitCalcChainSIMPS();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public LimitCalcChainSIMPS() {
    ALG_NAME = "SIMPS";
    ALG_MODEL.setSimpsonAlg(true);
  }
}

