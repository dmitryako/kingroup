package kingroup_v2.partition.ms2;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.ms.MSAlgErrorTest;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/06/2005, Time: 11:48:12
 */
public class MS2AlgWrong_AccessViaPairsTest extends MSAlgErrorTest {
  private MS2AlgModel ALG_MODEL = new MS2AlgModel();
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(MS2AlgWrong_AccessViaPairsTest.class);
  }
  public void setUp() {
    super.setUp();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  public void testPartition() {
    super.testPartition();
  }
  protected PartitionAlg loadMethod(SysPop pop, int intParam) {
    return new MS2AlgV2(pop, ALG_MODEL);
  }
}