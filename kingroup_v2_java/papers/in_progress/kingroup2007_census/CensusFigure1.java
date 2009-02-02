package papers.in_progress.kingroup2007_census;
import junit.framework.TestCase;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genetics.PartitionSearchModel;
import kingroup.genotype.GenotypeFactory;
import kingroup.model.HypothesisModel;
import kingroup.model.IBDFactory;
import kingroup.model.KinshipIBDModelV1;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.partition.PartitionEngineV2;
import kingroup.partition.PartitionMethodV2;
import kingroup.partition.PartitionModel;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.CensusPopBuilder;
import kingroup.population.CensusPopBuilderModel;
import kingroup.population.OldPop;
import kingroup.population.PopFactoryOLD;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.dr.DescRatioMethodV2;

import javax.iox.LOG;
import javax.utilx.DoubleArrayByInt;
import javax.utilx.pair.SumByInt;
import java.io.File;

public class CensusFigure1 //extends PerformanceChart
{
  private DoubleArrayByInt allDists_ = new DoubleArrayByInt();
  private SumByInt avrUnique_ = new SumByInt();
  private SumByInt avrDist_ = new SumByInt();
  private SumByInt count_ = new SumByInt();
  protected CensusPopBuilderModel POP_MODEL = new CensusPopBuilderModel();
  protected String DIR = "census";
  private final int NUM_ALLELES = 8;  //10-paper
  private final int MIN_NUM_LOCI = 1;  //1-paper
  private final int MAX_NUM_LOCI = 10; // 24-paper
  private final int NUM_TRIALS = 10; // 1000-paper, 2,500
  private final int POP_SIZE = 100;      // 100-paper
  private final int NUM_REPLICATES = 1;  //
  public CensusFigure1() {
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    POP_MODEL.loadDefaults();
    POP_MODEL.setIncParents(false);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.EQUAL_FREQ);
    POP_MODEL.setSize(POP_SIZE);
  }
  public void runP50() {
//      makeCensusFigure(DIR, "distance_50_NA4.txt"
//         , ButlerFamilyData.BUTLER_50x1, false, 4);
//      makeCensusFigure(DIR, "distance_P100_50x1_NA" + N_ALLELES + ".txt"
//         , ButlerFamilyData.BUTLER_3x1, false);
    makeCensusFigure(DIR, "distance_P100_50x1_NA" + NUM_ALLELES + ".txt"
      , ButlerFamilyData.BUTLER_50x1, false);

////      makeCensusFigure(DIR, "distance_5_10.txt"
////      , ButlerFamilyData.BUTLER_5x10, false, -1);
////      makeCensusFigure(DIR, "distance_5_10S_NA4.txt"
////      , ButlerFamilyData.BUTLER_5x10, false, 4);
    makeCensusFigure(DIR, "distance_P100_5x10S_NA" + NUM_ALLELES + ".txt"
      , ButlerFamilyData.BUTLER_5x10, false);
//      makeCensusFigure(DIR, "distance_5_10P_NA4.txt"
//      , ButlerFamilyData.BUTLER_5x8, true, 4);
//      makeCensusFigure(DIR, "distance_5_10P_NA8.txt"
//      , ButlerFamilyData.BUTLER_5x8, true, 8);

////      makeCensusFigure(DIR, "distance_25S_NA4.txt"
////      , ButlerFamilyData.BUTLER_25_10_10_4_1, false, 4);
    makeCensusFigure(DIR, "distance_P100_25S_NA" + NUM_ALLELES + ".txt"
      , ButlerFamilyData.BUTLER_25_10_10_4_1, false);
//      makeCensusFigure(DIR, "distance_25P_NA4.txt"
//      , ButlerFamilyData.BUTLER_23_8_8_2_1, true, 4);
//      makeCensusFigure(DIR, "distance_25P_NA8.txt"
//      , ButlerFamilyData.BUTLER_23_8_8_2_1, true, 8);

////      makeCensusFigure(DIR, "distance_46S_NA4.txt"
////      , ButlerFamilyData.BUTLER_46_1_1_1_1, false, 4);
    makeCensusFigure(DIR, "distance_P100_46S_NA" + NUM_ALLELES + ".txt"
      , ButlerFamilyData.BUTLER_46_1_1_1_1, false);
//      makeCensusFigure(DIR, "distance_46P_NA4.txt"
//      , ButlerFamilyData.BUTLER_44_1_1_1_1, true, 4);
//      makeCensusFigure(DIR, "distance_46P_NA8.txt"
//      , ButlerFamilyData.BUTLER_44_1_1_1_1, true, 8);
    System.exit(0);
  }
  public void makeCensusFigure(String dirName, String fileName, int familyType, boolean withParents) {
    POP_MODEL.setFamilyType(familyType);
    POP_MODEL.setIncParents(withParents);
    POP_MODEL.setNumReplicates(NUM_REPLICATES);
    POP_MODEL.setNumAlleles(NUM_ALLELES);
    allDists_ = new DoubleArrayByInt();
    avrDist_ = new SumByInt();
    avrUnique_ = new SumByInt();
    count_ = new SumByInt();
    HypothesisModel prim = new HypothesisModel(IBDFactory.makeCensus());
//      KinshipIBDModelV1 nullModel = IBDFactory.makeFullSibs();
    KinshipIBDModelV1 nullModel = IBDFactory.makeComplexFullSibsUnrelated();
//      KinshipIBDModelV1 nullModel = IBDFactory.makeUnrelated();
//      KinshipIBDModelV1 nullModel = IBDFactory.makeComplexFullsibParentUnrelated();
    PartitionSearchModel search = new PartitionSearchModel();
    search.setDisplaySearchSequence(false);
    search.setDisplaySorted(true);
    search.setDisplayUniqueOnly(true);                          // UNIQUE
    search.setSearchLocalSpace(false);                          // IGNORE LOCAL SPACE
    search.setMethod(PartitionSearchModel.DESCENDING_RATIO);    // DESCENDING
    PartitionModel partitionModel = new PartitionModel(prim, nullModel, search);
    for (int numLoci = MIN_NUM_LOCI; numLoci <= MAX_NUM_LOCI; numLoci++) {
      POP_MODEL.setNumLoci(numLoci);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);
      LOG.report(this, "numLoci=" + numLoci);
      for (int i = 0; i < NUM_TRIALS; i++) {
        GenotypeFactory.init();
//            Population pop = new CensusPopBuilder(UsrPopFactory).buildButler(POP_MODEL);
        OldPop pop = new CensusPopBuilder(freq).buildFromButler(POP_MODEL);
        TestCase.assertEquals(POP_MODEL.getSize(), pop.size());
        int unique = pop.countUnique();
        unique = POP_MODEL.getSize() - (POP_MODEL.getNumRepeats() + 1) * unique;
        avrUnique_.sum(numLoci, 100. * unique / POP_MODEL.getSize());
        int dist = calculateOneDistance(pop, partitionModel, unique);
        allDists_.add(numLoci, 100. * dist / POP_MODEL.getSize());
        avrDist_.sum(numLoci, 100. * dist / POP_MODEL.getSize());
        count_.sum(numLoci, 1);
      }
    }
    avrDist_.divide(count_);
    avrUnique_.divide(count_);
    LOG.saveToFile(avrDist_.toArray(), avrUnique_.toArray()
      , dirName + File.separator + "P" + POP_SIZE + "_unique", fileName);
    LOG.saveToFile(allDists_, dirName, fileName);
    LOG.saveToFile(allDists_, dirName + File.separator + "P" + POP_SIZE + "_" + NUM_TRIALS, fileName);
  }
  protected int calculateOneDistance(OldPop popA, PartitionModel partitionModel
    , int test) {
    PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population

//      PartitionAlg method = new DescRatioMethod();
//      PartitionEngine engine = new PartitionEngine(popA, partitionModel);
//
    PartitionMethodV2 method = new DescRatioMethodV2();
    PartitionEngineV2 engine = new PartitionEngineV2(popA, partitionModel);
    engine.calculate(method, false);
    OldPop popB = PopFactoryOLD.makeFrom(engine.getPartition(0));
    PopToPartitionFactory factory = PopToPartitionFactory.getInstance();
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);
    Partition partB = factory.makeFromPopulation(popB);
//      LOG.trace(this, "B=", partB);
    int dist = new LitowDistance().distance(partA, partB);
    if (dist != test) {
      LOG.setTrace(true);
//         LOG.trace(this, "popA=", popA);
//         LOG.trace(this, "popB=", popB);
//         LOG.trace(this, "partA=", partA);
//         LOG.trace(this, "partB=", partB);
      LOG.trace(this, "dist=" + dist + ", test=" + test);
//         LOG.trace(this, "#unique=" + popA.countUnique());
//         System.exit(1);
    }
//      int distAF = new AlmudevarFieldDistance().distance(partA, partB);
//      LOG.trace(this, "D(A,B)=", Integer.toString(dist));
    return dist;
  }
}