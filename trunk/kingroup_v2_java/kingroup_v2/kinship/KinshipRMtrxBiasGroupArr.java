package kingroup_v2.kinship;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/03/2006, Time: 08:40:14
 */
public class KinshipRMtrxBiasGroupArr extends PairwiseRMtrx
{
  final private SysPop[] groups;
  private final KinshipAlleleFreqOpt opt;

  public KinshipRMtrxBiasGroupArr(SysPop[] groups, SysPop pop, Kinship kinship)
  {
    super(pop);
    this.groups = groups;
    opt = kinship.getAlleleFreqOpt();
  }

  public void calc() {
    SysAlleleFreq saved = pop.getFreq();

    SysAlleleFreq refPopFreq = SysAlleleFreqFactory.makeFrom(pop, false);
    for (int g = 0; g < groups.length; g++) {
      SysPop group = groups[g];
      for (int g2 = 0; g2 <= g; g2++) {
        SysPop group2 = groups[g2];
        SysAlleleFreq biasCorrFreq = SysAlleleFreqFactory.makeDeepCopy(refPopFreq);
        int zeroCount = SysAlleleFreqFactory.subtract(group, biasCorrFreq);
        if (g != g2) {
          zeroCount += SysAlleleFreqFactory.subtract(group2, biasCorrFreq);
        }
        if (zeroCount > 0  &&  !opt.getAllowZeroFreq()) {
          SysAlleleFreqFactory.replaceZero(biasCorrFreq, refPopFreq);
        }
        biasCorrFreq.normalize(1., false);
        pop.setFreq(biasCorrFreq);
        for (int i = 0; i < group.size(); i++) {
          int popIdx = group.getIdIdx(i);
          for (int i2 = 0; i2 < group2.size(); i2++) {
            if (g == g2 && i >= i2) // calc lower triangle for the same group
              continue;
            int popIdx2 = group2.getIdIdx(i2);
            double sum = estimator.calc(popIdx, popIdx2);
            set(popIdx, popIdx2, sum);
          }
        }
      }
    }
    pop.setFreq(saved);
  }
}
