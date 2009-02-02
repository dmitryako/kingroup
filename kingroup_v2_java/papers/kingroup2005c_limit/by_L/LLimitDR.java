package papers.kingroup2005c_limit.by_L;
import kingroup.genetics.PartitionSearchModel;
import kingroup.model.IBDFactory;
import kingroup.model.HypothesisModel;
import kingroup.model.KinshipIBDModelV1;
import kingroup.partition.PartitionEngineV2;
import kingroup.partition.PartitionMethodV2;
import kingroup.partition.PartitionModel;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.dr.DRAlgWithOldPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/11/2005, Time: 17:45:47
 */
public class LLimitDR extends LLimitDist
{
  protected HypothesisModel PRIM_MODEL = new HypothesisModel(IBDFactory.makeFullSibs());
  protected KinshipIBDModelV1 NULL_MODEL = IBDFactory.makeUnrelated();
  
  public static void main(String[] args) {
    LLimitDR test = new LLimitDR();
    LOG.setTrace(true);
    test.run_rxg();
    System.exit(0);
  }
  public LLimitDR() {
    N_TRIALS = 100; // 100-paper
    FILE = "DR";
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