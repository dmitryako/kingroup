package kingroup_v2.partition.ms;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup_v2.partition.simpson.SysPopSampleTest;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistAlg;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/05/2005, Time: 08:00:52
 */
public class MSAlgAccessViaPairsTest extends SysPopSampleTest {
//  public static void main(String[] args) {
//    junit.textui.TestRunner.run(suite());
//  }
//  public static Test suite() {
//    return new TestSuite(MSAlgAccessPairsTest.class);
//  }
  public void testCalcLocusDistance() {
    LOG.setTrace(true);
    GenotypeDistAlg dist = new GenotypeDistLocusAvr();
    OldPop pop = makePop(LAA, LAB, LCC);
    LOG.trace(this, "pop=", pop.toString());
    SysPop simpsPop = OldPopToSysPopFactory.makePopFrom(pop);

    MSAlgAccessViaPairs order = new MSAlgAccessViaPairs(simpsPop, dist);
    assertEquals(true, order.nextIdx() == 0);
    assertEquals(true, order.nextIdx() == 1);
    assertEquals(true, order.nextIdx() == 2);
    pop = makePop(LAA, LAA, LCC, LCB);
    LOG.trace(this, "pop=", pop.toString());
    simpsPop = OldPopToSysPopFactory.makePopFrom(pop);
    order = new MSAlgAccessViaPairs(simpsPop, dist);
    assertEquals(true, order.nextIdx() == 0);
    assertEquals(true, order.nextIdx() == 1);
    assertEquals(true, order.nextIdx() == 2);
    assertEquals(true, order.nextIdx() == 3);
    pop = makePop(LAA, LCB, LAA, LCC);
    LOG.trace(this, "pop=", pop.toString());
    simpsPop = OldPopToSysPopFactory.makePopFrom(pop);
    order = new MSAlgAccessViaPairs(simpsPop, dist);
    assertEquals(true, order.nextIdx() == 0);
    assertEquals(true, order.nextIdx() == 2);
    assertEquals(true, order.nextIdx() == 1);
    assertEquals(true, order.nextIdx() == 3);
    LOG.setTrace(false);
  }
}
