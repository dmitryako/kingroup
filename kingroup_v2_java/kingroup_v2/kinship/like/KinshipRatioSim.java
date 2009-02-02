package kingroup_v2.kinship.like;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipSysPopFactory;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

import javax.swingx.ProgressWnd;
import javax.vecmathx.matrix.MtrxReadI;
import java.util.Arrays;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/10/2005, Time: 12:31:50
 */
public class KinshipRatioSim
  extends RatioSim
{
//  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRatioSim.class.getName());
  private ProgressWnd progress = null;
  private static final int PROGRESS_STEP = 10;

  private final KinshipIBD ibd;
  protected final Kinship kinship;

  public KinshipRatioSim(KinshipIBD ibd, Kinship kinship){
    this.ibd = ibd;
    this.kinship = kinship;
  }
  public void makeDiploidYFromX(int yIdx, int xIdx, SysPop pop) {
    KinshipSysPopFactory.makeDiploidYFromX(ibd, yIdx, xIdx, pop);
  }
  public MtrxReadI makeRatioMtrx(SysPop pop) {
    KinshipRatioMtrx mtrx = new KinshipRatioMtrx(pop);
    mtrx.calcRatio(kinship);
    return mtrx;
  }
  public boolean calc(SysAlleleFreq freq
    , String progressName) {
    progress = null;
    if (progressName != null) {
      progress = new ProgressWnd(KingroupFrame.getInstance(), progressName);
    }
    int x = 0;
    int y = 1;
    int PAIR_SIZE = 2;
    SysPop pair = new SysPop(PAIR_SIZE, freq.getNumLoci());
    pair.setFreq(freq);

    pair.setSize(PAIR_SIZE);
    rLogs = new double[kinship.getNumSimPairs()];
    for (int i = 0; i < rLogs.length; i++) {
      if (i % PROGRESS_STEP == 0
        && progress != null
        && progress.isCanceled(i, 0, rLogs.length)) {
        cleanup();
        return false;
      }
      SysPopFactory.makeRandomDiploid(x, pair);
      makeDiploidYFromX(y, x, pair);

      float error = kinship.getAlleleErrorRate();
      if (error != 0f)
        pair = SysPopFactory.makeWithAlleleError(pair, 100f * error);

//      log.info("pair=\n" + pair);
      MtrxReadI mtrx = makeRatioMtrx(pair);
      rLogs[i] = mtrx.get(x, y);
    }
    // log.info("ratios = " + DoubleArr.toString(rLogs));
    Arrays.sort(rLogs);
    // log.info("sorted = " + DoubleArr.toString(rLogs));
    if (progress != null)
      progress.close();
    return true;
  }
  private void cleanup() {
    rLogs = null;
    if (progress != null)
      progress.close();
    progress = null;
    System.gc();
  }
}
