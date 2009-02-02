package papers.kingroup2005c_limit.figure_5;
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
import papers.kingroup2005b_simpson.v1.Efficiency;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/11/2005, Time: 15:24:51
 */
public class LimitDR extends LimitDist {
  protected HypothesisModel PRIM_MODEL = new HypothesisModel(IBDFactory.makeFullSibs());
  protected KinshipIBDModelV1 NULL_MODEL = IBDFactory.makeUnrelated();
  
  public static void main(String[] args) {
    LimitDR test = new LimitDR();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public LimitDR() {
    N_TRIALS = 100; // 100-paper
    FILE = "DR";
  }
  protected Efficiency calcEfficiency(OldPop popA) {
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
    return new Efficiency(dist, 0, 0);
  }
}