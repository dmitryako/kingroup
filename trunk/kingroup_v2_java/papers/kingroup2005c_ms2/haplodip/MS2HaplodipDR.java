package papers.kingroup2005c_ms2.haplodip;
import kingroup.genetics.PartitionSearchModel;
import kingroup.model.IBDFactory;
import kingroup.model.HypothesisModel;
import kingroup.model.KinshipIBDModelV1;
import kingroup.partition.PartitionEngineV2;
import kingroup.partition.PartitionMethodV2;
import kingroup.partition.PartitionModel;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup_v2.partition.dr.DRAlgWithOldPop;
import kingroup.population.OldPop;
import kingroup_v2.partition.distance.LitowDistance;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/09/2005, Time: 16:30:59
 */
public class MS2HaplodipDR extends MS2Haplodip {
  protected HypothesisModel PRIM_MODEL = new HypothesisModel(IBDFactory.makeFullSibs());
  protected KinshipIBDModelV1 NULL_MODEL = IBDFactory.makeUnrelated();
  
  public static void main(String[] args) {
    MS2HaplodipDR test = new MS2HaplodipDR();
    LOG.setTrace(true);
//      test.run_P50();
//      test.runP50_SDR_test();
    test.run_P50_with_halfsibs();
    System.exit(0);
  }
  public MS2HaplodipDR() {
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    N_TRIALS = 100; // 100-paper
    FILE = "DR";
  }
  public void run_P50_with_halfsibs() {
    PRIM_MODEL = new HypothesisModel(IBDFactory.makeFullSibsHaplod());
    NULL_MODEL = IBDFactory.makeComplexHalfsibUnrelatedHaplod();
    super.run_P50_with_halfsibs();
  }
  protected double calcOneDistance(OldPop popA) {
    PartitionSearchModel search = new PartitionSearchModel();
    search.setDisplaySearchSequence(false);
    search.setDisplaySorted(true);
    search.setDisplayUniqueOnly(true);                          // UNIQUE
    search.setSearchLocalSpace(false);                          // IGNORE LOCAL SPACE
    search.setMethod(PartitionSearchModel.DESCENDING_RATIO);    // DESCENDING
    PartitionModel partitionModel = new PartitionModel(PRIM_MODEL, NULL_MODEL, search);
    PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
//      PartitionEngine engine = new PartitionEngine(popA, partitionModel);
    PartitionEngineV2 engine = new PartitionEngineV2(popA, partitionModel);
//      PartitionMethodV2 method = new DescRatioMethodV2();
    PartitionMethodV2 method = new DRAlgWithOldPop();
    engine.calculate(method, false);

//      OldPop popB = PopFactoryOLD.makeFrom(engine.getPartition(0));
    PopToPartitionFactory factory = PopToPartitionFactory.getInstance();
    Partition partA = factory.makeFromPopulation(popA);
//      LOG.trace(this, "A=", partA);

//      Partition partB = factory.makeFromPopulation(popB);
    Partition partB = engine.getPartition(0).toBitSetPartition();
//      LOG.trace(this, "B=", partB);
    int dist = new LitowDistance().distance(partA, partB);
//      LOG.trace(this, "D(A,B)=", Integer.toString(dist));
    return dist;
  }
}