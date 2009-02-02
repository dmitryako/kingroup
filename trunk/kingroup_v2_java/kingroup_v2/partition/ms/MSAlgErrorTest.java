package kingroup_v2.partition.ms;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPSAlg2Test;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/05/2005, Time: 11:52:38
 */
public class MSAlgErrorTest extends SIMPSAlg2Test {
  private static final int WINDOW_SIZE = 10;
  private MSAlgModel ALG_MODEL = new MSAlgModel();
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void setUp() {
    super.setUp();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  public void testPartition() {
    LOG.setTrace(true);
    KinGroupProjectV1.makeInstance("SimpsAlgTest", "1");
    POP_MODEL.setIncParents(false);
//         POP_MODEL.setIncParents(true);
    runModel(ButlerFamilyData.BUTLER_1x2, 20, WINDOW_SIZE);
    runModel(ButlerFamilyData.BUTLER_1x3, 30, WINDOW_SIZE);
    runModel(ButlerFamilyData.BUTLER_1x4, 40, WINDOW_SIZE);
    runModel(ButlerFamilyData.BUTLER_1x5, 50, WINDOW_SIZE);
    LOG.setTrace(false);
  }
  protected PartitionAlg loadMethod(SysPop pop, int intParam) {
    ALG_MODEL.setWindSize(intParam);
    return new MSAlgV2(pop, ALG_MODEL);
  }
}