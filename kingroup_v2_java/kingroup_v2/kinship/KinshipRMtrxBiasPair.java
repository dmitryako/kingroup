package kingroup_v2.kinship;
import kingroup_v2.KingroupFrame;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

import javax.swingx.ProgressWnd;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/03/2006, Time: 15:39:40
 */
public class KinshipRMtrxBiasPair extends PairwiseRMtrx
{
  private ProgressWnd progress = null;
  private final  SysPop largePop;
  private final KinshipAlleleFreqOpt opt;

  public KinshipRMtrxBiasPair(SysPop group, SysPop largePop, Kinship kinship)
  {
    super(group);
    this.largePop = largePop;
    opt = kinship.getAlleleFreqOpt();
  }
  public void calc() {
    progress = new ProgressWnd(KingroupFrame.getInstance(), "relatedness");
    SysAlleleFreq saved = pop.getFreq();

    SysAlleleFreq freq = SysAlleleFreqFactory.makeFrom(largePop, false);
    // log.info("pop=\n"+pop);
    for (int i = 0; i < pop.size(); i++) {
      if (progress != null
        && progress.isCanceled(i, 0, pop.size())) {
        return;
      }
      for (int k = 0; k < i; k++) {
        SysAlleleFreq tmpFreq = SysAlleleFreqFactory.makeDeepCopy(freq);
//        SysAlleleFreq freq = SysAlleleFreqFactory.makeBiasByPair(i, k, pop, largePop);

        int zeroCount = SysAlleleFreqFactory.subtract(i, pop, tmpFreq);
        zeroCount += SysAlleleFreqFactory.subtract(k, pop, tmpFreq);
        if (zeroCount > 0  &&  !opt.getAllowZeroFreq()) {
          SysAlleleFreqFactory.replaceZero(tmpFreq, freq);
        }
        tmpFreq.normalize(1., false);

        pop.setFreq(tmpFreq);
        double sum = estimator.calc(i, k);
        set(i, k, sum); // i < k // only the above diagonal elements are calculated
      }
    }
    if (progress != null)
      progress.close();
    pop.setFreq(saved);
  }
}
