package kingroup_v2.partition.simpson;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeFactory;
import kingroup.genotype.Locus;
import kingroup.papers.butler2004.ButlerPopBuilder;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup.population.PopGroup;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/05/2005, Time: 12:49:13
 */
public class SIMPSAlg2Test extends SysPopSampleTest {
  private SIMPS2AlgModel ALG_MODEL = new SIMPS2AlgModel();
  public static final int NUM_SIMPSON_TRIALS = 20;
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(SIMPSAlg2Test.class);
  }
  public void setUp() {
    super.setUp();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
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
  public void runModel(int familyType, int numTrials, int methodParam) {
    POP_MODEL.setFamilyType(familyType);
    OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
    freq.normalize(1.0f, false);
    LOG.trace(this, "UsrPopFactory=", freq);
    for (int i = 0; i < numTrials; i++) {
      GenotypeFactory.init();
      OldPop pop = new ButlerPopBuilder(freq).buildButler(POP_MODEL);
      LOG.trace(this, "pop=", pop.toString());

      SysPop simpsPop = OldPopToSysPopFactory.makePopFrom(pop);

      LOG.trace(this, "simpsPop=\n", simpsPop);
      SimpsPartitionFactory factory = new SimpsPartitionFactory(simpsPop);
      SimpsPartition group = factory.makeOneGroup();
      assertEquals(true, ALG_MODEL.getSibshipAlg().isSibGroupSLOW(simpsPop, group.getGroupByIdIdx(0)));
      PopToPartitionFactory popFactory = PopToPartitionFactory.makeInstance(pop); // must be called here, since we created a new population
      Partition partA = popFactory.makeFromPopulation(pop);
      LOG.trace(this, "partA=", partA);
      PartitionAlg method = loadMethod(simpsPop, methodParam);
      Partition partB = method.partition();
      LOG.trace(this, "partB=", partB);
      int dist = new LitowDistance().distance(partA, partB);
      LOG.trace(this, "D(A,B)=" + dist);
      assertEquals(0, dist);
    }
  }
  protected PartitionAlg loadMethod(SysPop simpsPop, int intParam) {
    ALG_MODEL.setNumIters(intParam);
    return new SIMPS2Alg(simpsPop, ALG_MODEL);
  }
  public PopGroup makeGroup(String grId, Locus L
    , OldAlleleFreq freq, GenotypeFactory genoMaker) {
    PopGroup gr = new PopGroup();
    gr.setId(grId);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(L);
    gr.addGenotype(geno);
    return gr;
  }
  public PopGroup makeGroup(String grId, Locus L, Locus L2
    , OldAlleleFreq freq, GenotypeFactory genoMaker) {
    PopGroup gr = makeGroup(grId, L, freq, genoMaker);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(L2);
    gr.addGenotype(geno);
    return gr;
  }
  public PopGroup makeGroup(String grId, Locus L, Locus L2, Locus L3
    , OldAlleleFreq freq, GenotypeFactory genoMaker) {
    PopGroup gr = makeGroup(grId, L, L2, freq, genoMaker);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(L3);
    gr.addGenotype(geno);
    return gr;
  }
  public PopGroup makeGroup(String grId, Locus L, Locus L2, Locus L3, Locus L4
    , OldAlleleFreq freq, GenotypeFactory genoMaker) {
    PopGroup gr = makeGroup(grId, L, L2, L3, freq, genoMaker);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(L4);
    gr.addGenotype(geno);
    return gr;
  }
  public void runPartion(OldPop pop, int numSimpsTrials) {
    LOG.trace(this, "pop=", pop.toString());
    PopToPartitionFactory popFactory = PopToPartitionFactory.makeInstance(pop); // must be called here, since we created a new population
    Partition partA = popFactory.makeFromPopulation(pop);
    SysPop simpsPop = OldPopToSysPopFactory.makePopFrom(pop);
    PartitionAlg method = loadMethod(simpsPop, numSimpsTrials);
    Partition partB = method.partition();
//      SIMPS2Alg method = new SIMPS2Alg(simpsPop, numSimpsTrials);
//      Partition partB = method.partition();
    LOG.trace(this, "partA=", partA);
    LOG.trace(this, "partB=", partB);
    int dist = new LitowDistance().distance(partA, partB);
    LOG.trace(this, "D(A,B)=" + dist);
    assertEquals(0, dist);
  }
}
