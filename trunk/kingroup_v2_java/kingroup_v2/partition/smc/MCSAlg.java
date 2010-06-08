package kingroup_v2.partition.smc;
import junit.framework.TestCase;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SimpsPartition;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
import javax.utilx.RandomSeed;
import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.Int2;
import javax.utilx.pair.IntPairFactory;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/08/2005, Time: 10:21:32
 */
public class MCSAlg extends SIMPS2Alg {
  public static final String REFERENCE = "submitted for publication";
  private int[] distChain;
  private double[] simpsChain;
  private static final int MOVE_DOWNHILL = 10;
  private final MCSAlgModel model;
  public MCSAlg(SysPop pop, MCSAlgModel model) {
    super(pop, model);
    this.model = model;
    distChain = new int[model.getNumIters() + 1];
    simpsChain = new double[model.getNumIters() + 1];
  }
  public int[] getDistChain() {
    return distChain;
  }
  public double[] getSimpsIdxChain() {
    return simpsChain;
  }
  public Partition partition(Partition partA) {
    SimpsPartition curr = factory.makeOnePerGroup(); // current

    double currSimps = curr.calcSimpsonIndex();
    int currDist = new LitowDistance().distance(partA, curr);
    distChain[0] = currDist;
    simpsChain[0] = currSimps;

    SimpsPartition bestPart = curr;
    double bestSimps = 0;
    for (int i = 0; i < model.getNumIters(); i++) {
      distChain[i + 1] = currDist;
      simpsChain[i + 1] = currSimps;
      Int2 p = IntPairFactory.makeRandomIdxPair(pop.size()); // STEP 1, from p1591 of Butler etal MolEcol(2004)13, p1589
//         LOG.report(this, "STEP 2: (i,j)=" + p);
      CompBitSet gI = curr.getGroupByIdIdx(p.a); // STEP 2
      CompBitSet gJ = curr.getGroupByIdIdx(p.b);
//         LOG.report(this, "      : gJ=" + gJ);
      if (gI == gJ) {
        continue;
      }
      CompBitSet newJ = new CompBitSet(gJ);// STEP 3
      TestCase.assertEquals(newJ.get(p.a), false);
      newJ.set(p.a, true);
//         LOG.report(this, "STEP 3: newJ=" + newJ);
      if (!model.getSibshipAlg().isSibGroupSLOW(pop, newJ)) {
        continue;
      }

      SimpsPartition newPart = new SimpsPartition(curr);
      newPart.moveAWithB(p.a, p.b);
      double newSimps = newPart.calcSimpsonIndex();
      if (!moveToNewConfigRule(newSimps, currSimps))
        continue;

      curr = newPart;
      currSimps = newSimps;

      currDist = new LitowDistance().distance(partA, curr);
      distChain[i + 1] = currDist;
      simpsChain[i + 1] = currSimps;

      LOG.report(this, "distChain[" + (i + 1) + "]=" + currDist);
      if (bestSimps < currSimps) {    // STEP 5
        bestSimps = currSimps;
        bestPart = new SimpsPartition(curr);
//            LOG.report(this, "      : best simpson=" + (float)bestSimps);
//            LOG.report(this, "      : best partition=" + bestPart);
      }
    }
    return bestPart;
  }

  public boolean moveToNewConfigRule(double newSimps, double currSimps)
  {
    int x = RandomSeed.getInstance().nextInt(MOVE_DOWNHILL);
//    LOG.report(this, "x=" + x);
    if (newSimps < currSimps
      && x > 0)  //e.g. will pass only 1 out of 10 times
      return false;
    return true;
  }
}
