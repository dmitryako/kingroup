package kingroup_v2.partition.simpson.exact;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.GenotypeFactory;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup.population.PopGroup;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPSAlg2Test;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/07/2005, Time: 15:06:01
 */
public class SimpsExactAlgTest extends SIMPSAlg2Test {
  private SimpsExactAlgModel ALG_MODEL;
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(SimpsExactAlgTest.class);
  }
  public void setUp() {
    super.setUp();
    ALG_MODEL = new SimpsExactAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  public void testPartition() {
    LOG.setTrace(true);
    KinGroupProjectV1.makeInstance("SimpsAlgTest", "1");
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
    KinGroupProjectV1.makeInstance("SimpsAlgTest", "1");
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
    pop = new OldPop(freq);
    gr = makeGroup("3fs", LAA, LAB, LAC, freq, genoMaker);
    pop.addGroup(gr);
    runPartion(pop);

    // (AA AB AC) (DD)
    pop = new OldPop(freq);
    gr = makeGroup("3fs", LAA, LAB, LAC, freq, genoMaker);
    pop.addGroup(gr);
    gr = makeGroup("one", LDD, freq, genoMaker);
    pop.addGroup(gr);
    runPartion(pop);
    LOG.setTrace(false);
  }
  protected PartitionAlg loadMethod(SysPop simpsPop, int intParam) {
    return new SimpsExactAlg(simpsPop, ALG_MODEL);
  }
  public void runPartion(OldPop pop) {
    LOG.trace(this, "pop=", pop.toString());
    PopToPartitionFactory popFactory = PopToPartitionFactory.makeInstance(pop); // must be called here, since we created a new population
    Partition partA = popFactory.makeFromPopulation(pop);
    SysPop simpsPop = OldPopToSysPopFactory.makePopFrom(pop);
    SimpsExactAlg method = new SimpsExactAlg(simpsPop, ALG_MODEL);
    Partition partB = method.partition();
    LOG.trace(this, "partA=", partA);
    LOG.trace(this, "partB=", partB);
    int dist = new LitowDistance().distance(partA, partB);
    LOG.trace(this, "D(A,B)=" + dist);
    assertEquals(0, dist);
  }
}
