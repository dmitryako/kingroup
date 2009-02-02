package kingroup_v2.kinship;

import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Created by: jc1386591
 * Date: 12/07/2006. Time: 11:35:22
 */
public class KingroupRFromFreq extends PairwiseRMtrx
{
  public KingroupRFromFreq(SysPop pop) {
    super(pop);
    //calc = new KingroupREstimV2(pop);
  }
  public void calc() {
    super.calc();
    correctForBias();
  }

  public void correctForBias() {
    int n = pop.size();
    double norm = 1. / n;
    double[] rr = new double[n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        rr[i] += get(i, j);
      }
      rr[i] *= norm;
    }
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {
        double eij = get(i, j);
        double cij = 0.5 * (rr[i] + rr[j]);
        double newR = eij + cij * (1 - eij);
        set(i,j, newR);
      }
    }
  }

}