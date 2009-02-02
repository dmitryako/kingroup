package kingroup_v2.pop.sample.sys;
import junit.framework.TestCase;
import kingroup_v2.pop.sample.PopBuilderModel;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2006, Time: 14:57:11
 */
public class SysPopNullHypothesisFactoryTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopNullHypothesisFactoryTest.class.getName());
  public void test() {
    int N_LOCI = 2;
    int N_ALLELES = 10;
    PopBuilderModel builder = new PopBuilderModel();
    builder.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    builder.setNumAlleles(N_ALLELES);
    builder.setNumLoci(N_LOCI);
    builder.setNumFullSibs(2);
    builder.setGroupSize(10);

    SysPop pop = SysPopFactory.makeSysPopFrom(builder);
    log.info("from pop=\n" + pop);
    SysPop nullPop = SysPopNullHypothesisFactory.makeNullHypothesis(pop);
    log.info("null pop=\n" + nullPop);
    int dbg = 1;
  }
}
