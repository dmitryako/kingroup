package papers.kingroup2005c_ms2;
import kingroup.genetics.PartitionSearchModel;
import kingroup.model.IBDFactory;
import kingroup.model.HypothesisModel;
import kingroup.model.KinshipIBDModelV1;
import kingroup.partition.PartitionModel;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.sdr.SDRAlgV1;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/08/2005, Time: 16:44:49
 */
public class MS2DiploidSDR extends MS2Diploid {
  
  public static void main(String[] args) {
    MS2DiploidSDR test = new MS2DiploidSDR();
    LOG.setTrace(true);
//      test.run_P50();
//      test.run_P50_with_parents();
    test.runP50_SDR_test();
    System.exit(0);
  }
  public MS2DiploidSDR() {
    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    N_TRIALS = 100; // 100-paper
    FILE = "SDR";
  }
  protected double calcOneDistance(OldPop popA) {
    HypothesisModel prim = new HypothesisModel(IBDFactory.makeFullSibs());
    KinshipIBDModelV1 nullModel = IBDFactory.makeUnrelated();
//      KinshipIBDModelV1 nullModel = IBDFactory.makeComplexParentUnrelated();
    PartitionSearchModel search = new PartitionSearchModel();
    search.setDisplaySearchSequence(false);
    search.setDisplaySorted(true);
    search.setDisplayUniqueOnly(true);                          // UNIQUE
    search.setSearchLocalSpace(false);                          // IGNORE LOCAL SPACE
    search.setMethod(PartitionSearchModel.DESCENDING_RATIO);    // DESCENDING
    PartitionModel partitionModel = new PartitionModel(prim, nullModel, search);
    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);
    SDRAlgV1 method = new SDRAlgV1(popA, partitionModel, false);
    Partition partB = method.partition();
    int dist = new LitowDistance().distance(partA, partB);
    if (dist != 0) {
//         LOG.trace(this, "A=", partA);
//         LOG.trace(this, "B=", partB);
//         LOG.trace(this, "D(A,B)=", Integer.toString(dist));
    }
    return dist;
  }
}