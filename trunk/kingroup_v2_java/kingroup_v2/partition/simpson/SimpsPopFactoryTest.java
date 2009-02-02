package kingroup_v2.partition.simpson;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.GenotypeFactory;
import kingroup.papers.butler2004.ButlerPopBuilder;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/05/2005, Time: 13:32:23
 */
public class SimpsPopFactoryTest extends TestCase {
  protected ButlerPopBuilderModel POP_MODEL = new ButlerPopBuilderModel();
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(SimpsPopFactoryTest.class);
  }
  public void setUp() {
    int POP_SIZE = 50;
    int numAlleles = 10;
    int numLoci = 2;
    POP_MODEL.loadDefaults();
    POP_MODEL.setIncParents(true);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.EQUAL_FREQ);
    POP_MODEL.setSize(POP_SIZE);
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_1x2);
    POP_MODEL.setNumLoci(numLoci);
    POP_MODEL.setNumAlleles(numAlleles);
  }
  public void testMakeFrom() {
    KinGroupProjectV1.makeInstance("SimpsPopFactoryTest", "1");
//      POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_1x2);
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_1x3);
//      POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_3x1);
    OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
    freq.normalize(1.0f, false);
    int NUM_TRIALS = 20;

    SibshipAlg sibship = new DiploidSibship();
    for (int i = 0; i < NUM_TRIALS; i++) {
      GenotypeFactory.init();
      POP_MODEL.setIncParents(false);
//         POP_MODEL.setIncParents(true);
      OldPop popA = new ButlerPopBuilder(freq).buildButler(POP_MODEL);
      LOG.setTrace(true);
      LOG.trace(this, "popA=", popA.toString());
      SysPop simsPop = OldPopToSysPopFactory.makePopFrom(popA);
      LOG.trace(this, "pop=\n", simsPop);
      SimpsPartitionFactory factory = new SimpsPartitionFactory(simsPop);
      SimpsPartition group = factory.makeOneGroup();
      assertEquals(true, sibship.isSibGroupSLOW(simsPop, group.getGroupByIdIdx(0)));
    }
    LOG.setTrace(false);
  }
}
