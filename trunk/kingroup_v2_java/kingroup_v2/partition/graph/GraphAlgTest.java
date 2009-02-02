package kingroup_v2.partition.graph;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.GenotypeFactory;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.OldPop;
import kingroup.population.PopGroup;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.simpson.SIMPSAlg2Test;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/07/2005, Time: 11:18:23
 */
public class GraphAlgTest extends SIMPSAlg2Test {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(GraphAlgTest.class);
  }
  public void setUp() {
    super.setUp();
  }
  public void testPartition() {
    LOG.setTrace(true);
    KinGroupProjectV1.makeInstance("GraphAlgTestb  ", "1");
    POP_MODEL.setIncParents(false);
//         POP_MODEL.setIncParents(true);
    runModel(ButlerFamilyData.BUTLER_1x2, 20, NUM_SIMPSON_TRIALS);
    runModel(ButlerFamilyData.BUTLER_1x3, 30, NUM_SIMPSON_TRIALS);
    runModel(ButlerFamilyData.BUTLER_1x4, 40, NUM_SIMPSON_TRIALS);
    runModel(ButlerFamilyData.BUTLER_1x5, 50, NUM_SIMPSON_TRIALS);
    LOG.setTrace(false);
  }
  public void testPartition2() {
    LOG.setTrace(true);
    KinGroupProjectV1.makeInstance("DB_GraphAlgTest", "1");
    POP_MODEL.setNumLoci(1);
    OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
    GenotypeFactory genoMaker = GenotypeFactory.getInstance();
    OldPop pop = null;
    PopGroup gr = null;

    // (AB AC) (DD)
//      OldPop pop = new OldPop(UsrPopFactory);
//      PopGroup gr = makeGroup("2fs", LAB, LAC, UsrPopFactory, genoMaker);
//      pop.addGroup(gr);
//      gr = makeGroup("one", LDD, UsrPopFactory, genoMaker);
//      pop.addGroup(gr);
//      runPartion(pop, 30);

    // AA AB AC
    GenotypeFactory.init();
    pop = new OldPop(freq);
    gr = makeGroup("3fs", LAA, LAB, LAC, freq, genoMaker);
    pop.addGroup(gr);
    runPartion(pop, 20);

    // (AA AB AC) (DD)
    pop = new OldPop(freq);
    gr = makeGroup("3fs", LAA, LAB, LAC, freq, genoMaker);
    pop.addGroup(gr);
    gr = makeGroup("one", LDD, freq, genoMaker);
    pop.addGroup(gr);
    runPartion(pop, 30);
    LOG.setTrace(false);
  }
  protected PartitionAlg loadMethod(SysPop simpsPop, int intParam) {
    return new GraphAlg(simpsPop);
  }
}
