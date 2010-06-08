package kingroup_v2.partition.simpson;
import junit.framework.TestCase;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.PartitionAlg;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.Int2;
import javax.utilx.pair.IntPairFactory;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/05/2005, Time: 08:41:11
 */
public class SIMPS2Alg extends PartitionAlg
{
  private static ProjectLogger log = ProjectLogger.getLogger(SIMPS2Alg.class);
  public static final String REFERENCE = "Butler et al. (2004) Molecular Ecology 13, p1589-1600";
  protected final SysPop pop;
  protected final SimpsPartitionFactory factory;
  protected final SIMPS2AlgModel model;
  public SIMPS2Alg(SysPop pop, SIMPS2AlgModel model) {
    this.pop = pop;
    this.model = model;
    assert(pop.size() > 0);
    assert(pop.getNumLoci() > 0);
    factory = new SimpsPartitionFactory(pop);
  }

  public Partition partition() {
//    log.setDebug(true);
    log.setInfo(true);

    int count = 0;
    SimpsPartition currPart = factory.makeOnePerGroup(); // current
    double currSimps = currPart.calcSimpsonIndex();
    log.info("currSimps =", currSimps);
    log.info("currPart =\n", currPart);

    SimpsPartition bestPart = currPart;
    double bestSimps = 0;
    for (int i = 0; i < model.getNumIters(); i++) {
      count++;
      Int2 p = IntPairFactory.makeRandomIdxPair(pop.size()); // STEP 1, from p1591 of Butler etal MolEcol(2004)13, p1589
      CompBitSet gI = currPart.getGroupByIdIdx(p.a); log.info("gI=", gI); // STEP 2
      CompBitSet gJ = currPart.getGroupByIdIdx(p.b); log.info("gJ=", gJ);
      if (gI == gJ) {
        continue;
      }
      CompBitSet newJ = new CompBitSet(gJ);// STEP 3
      TestCase.assertEquals(newJ.get(p.a), false);
      newJ.set(p.a, true);
      if (model.getLoopBreaker() > 0
        && count > model.getLoopBreaker()) {
        log.info("break @ i = " + i + ", count > [model.getMaxCount()=" + model.getLoopBreaker());
        break;
      }
//      log.info("newJ =\n" + pop.toString(newJ));
      if (!model.getSibshipAlg().isSibGroupSLOW(pop, newJ)) {
        continue;
      }

      SimpsPartition newPart = new SimpsPartition(currPart);
      newPart.moveAWithB(p.a, p.b);
      double newSimps = newPart.calcSimpsonIndex();
//      log.info("newSimps = " + (float)newSimps + ", newPart =\n" + newPart);
      if (!moveToNewConfigRule(newSimps, currSimps))
        continue;

      count = 0; // reset count
      currPart = newPart;
      currSimps = newSimps;
      if (bestSimps < currSimps) {    // STEP 5
        bestSimps = currSimps;
        bestPart = new SimpsPartition(currPart);
//        log.info("bestSimps = " + (float)bestSimps + ", bestPart =\n" + bestPart);
      }
    }
    return bestPart;
  }
  public boolean moveToNewConfigRule(double newSimps, double currSimps)
  {
    return true;
  }
}
