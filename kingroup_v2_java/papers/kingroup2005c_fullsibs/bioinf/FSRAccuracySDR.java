package papers.kingroup2005c_fullsibs.bioinf;
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
 * User: jc138691, Date: 3/08/2005, Time: 16:00:13
 */
public class FSRAccuracySDR extends FSRAccuracyMS2 {
  public static void main(String[] args) {
    FSRAccuracySDR test = new FSRAccuracySDR();
    LOG.setTrace(true);
    test.setup_r2();
    test.run_n20_uniform();
//      test.run_r2_uniform();
//      test.run_r5_uniform();
//      test.run_r10_uniform();
    System.exit(0);
  }
  public void setup_r2() {
    POP_SIZE = -1;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 12;//12-paper
    N_TRIALS = 100; // 100-paper
    FILE = "SDR_U";
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
    SDRAlgV1 method = new SDRAlgV1(popA, partitionModel, true);
    Partition partDRS = method.partition();
    int distDRS = new LitowDistance().distance(partA, partDRS);

/*
      // DEBUG
      PartitionEngineV2 engine = new PartitionEngineV2(popA, partitionModel);
      PartitionMethodV2 dr = new DRAlgWithOldPop();
      engine.calculate(dr, false);
      Partition partDR = engine.getPartition(0).toBitSetPartition();
      int distDR = new LitowDistance().distance(partA, partDR);

      if (distDRS != distDR) {
//         SysPop popSimps = OldPopToSysPopFactory.makePopFrom(popA);
//         SIMPS2Alg simps = new SIMPS2Alg(popSimps, SIMPS_ITERATIONS);
//         Partition partS = simps.partition();
//         int distS = new LitowDistance().distance(partA, partS);

         LOG.trace(this, "A=", partA);
         LOG.trace(this, "partDRS=", partDRS);
         LOG.trace(this, "partDR =", partDR);
//         LOG.trace(this, "partS  =", partS);
         LOG.trace(this, "D(A,B)=", Integer.toString(distDRS));
         System.exit(1);
      }
*/
    return distDRS;
  }
}