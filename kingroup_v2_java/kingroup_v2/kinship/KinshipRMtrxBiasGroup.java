package kingroup_v2.kinship;
import kingroup_v2.KingroupFrame;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;
import tsvlib.project.ProjectLogger;

import javax.swingx.ProgressWnd;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/03/2006, Time: 15:54:42
 */
public class KinshipRMtrxBiasGroup extends PairwiseRMtrx
{
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private ProgressWnd progress = null;
  final private SysPop refPop;
  private final KinshipAlleleFreqOpt opt;

  public KinshipRMtrxBiasGroup(SysPop group, SysPop largePop, Kinship kinship)
  {
    super(group);
    this.refPop = largePop;
    opt = kinship.getAlleleFreqOpt();
  }

  public void calc() {
    progress = new ProgressWnd(KingroupFrame.getInstance(), "relatedness");
    setProgress(progress);
    SysAlleleFreq saved = pop.getFreq();
//    log.info("saved=\n" + saved);

    SysAlleleFreq refPopFreq = SysAlleleFreqFactory.makeFrom(refPop, false);
//    log.info("largePop=\n" + largePop);
//    log.info("largePopFreq=\n" + largePopFreq);

    SysAlleleFreq biasCorrFreq = SysAlleleFreqFactory.makeDeepCopy(refPopFreq);
    int zeroCount = SysAlleleFreqFactory.subtract(pop, biasCorrFreq);
//    log.info("pop=\n" + pop);
//    log.info("biasCorrFreq=\n" + biasCorrFreq);
    if (zeroCount > 0) {
//      log.info("zeroCount=" + zeroCount);
//      log.info("largePop=\n" + largePop);
//      log.info("excluded group=\n" + pop);
//      log.info("largePopFreq=\n" + largePopFreq);
//      log.info("biasCorrFreq=\n" + biasCorrFreq);
    }
    if (zeroCount > 0  &&  !opt.getAllowZeroFreq()) {
      SysAlleleFreqFactory.replaceZero(biasCorrFreq, refPopFreq);
//      log.info("no zero biasCorrFreq=\n" + biasCorrFreq);
    }
    biasCorrFreq.normalize(1., false);
//    log.info("biasCorrFreq=\n" + biasCorrFreq);

    pop.setFreq(biasCorrFreq);
    super.calc();
    if (progress != null)
      progress.close();
    pop.setFreq(saved);
  }
}
