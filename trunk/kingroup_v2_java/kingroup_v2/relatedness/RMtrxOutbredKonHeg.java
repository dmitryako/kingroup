package kingroup_v2.relatedness;

import kingroup_v2.cervus.AlleleAnalysisFactory;
import kingroup_v2.kinship.KHDistSqrEstimator;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Created by: jc1386591
 * Date: 25/06/2006. Time: 11:20:25
 */
public class RMtrxOutbredKonHeg extends PairwiseRMtrx
{
  protected SysPop refPop;

  public RMtrxOutbredKonHeg(SysPop pop) {
    super(pop);
    refPop = pop;
  }

  public void setReferencePop(SysPop pop) {
    refPop = pop;
  }

  public void calc() {
//    calc_viaT();
    calc_viaDist();
  }
  public void calc_viaDist() {
    estimator = new KHDistSqrEstimator(pop);
    super.calc();
    int n = pop.size();

//    double Hs = AlleleAnalysisFactory.calcTrueHeterozAvr(freq);
//    PairwisePMtrx pm = new PairwisePMtrx(new PairwiseRMtrxFactory(Kingroup.PAIRWISE_DIST_SQR));
//    double[] nullDistArr = pm.calcNullDistr(refPop);
//    double avrDist = DoubleArr.avr(nullDistArr);
//    double avrDist = 4. * Hs;
//    avrDist *= (2. * n / (2. * n - 1));

//    double[] obsHet = AlleleAnalysisFactory.calcObservHeteroz(pop);
    double h = AlleleAnalysisFactory.calcObservHeterozAvr(refPop);
//    double h = 0.5 * AlleleAnalysisFactory.calcVarXAvr(refPop);

    int nL = pop.getNumLoci();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {
        double dij = get(i, j);

//        double sum = 0;
//        for (int L = 0; L < nL; L++) {
//          double r = dij / h
//        }

        double newR = dij / h;
        set(i,j, 1. - newR);
      }
    }
//    log.info("corrected rmtrx=\n"+this.toString());
  }

//  public void calc_with_inbreeding() {
//    super.calc();
//
//    int n = pop.size();
//    double n2 = 2. * n - 1.;
//    double n1 = n - 1.;
//    double Hs = AlleleAnalysisFactory.calcTrueHeterozAvr(freq);
//    double He = Hs * 2. * n / n2;
//    double Ho = AlleleAnalysisFactory.calcHegKonHeterozAvr2(refPop);
//    double Heo = He - Ho;
//    double q = (2. * Hs - Ho) * n / n1;
//
//    double h = He + Heo / (2. * n1);
//    double f = n2 * Heo / (2. * n1 * h);
////    log.info("\nhet from rii=1=" + (float)het);
//    for (int i = 0; i < n; i++) {
//      for (int j = 0; j < i; j++) {
//        double tij = get(i, j);
//        double newR = (2. * (h - 1.)  + tij) / q;
//        set(i,j, newR);
//      }
//    }
////    log.info("corrected rmtrx=\n"+this.toString());
//  }


//  public static String makeMessageAboutBias() {
//    return "KINSHIP bias-corrections are not applicable\nsince this estimator does not use allele frequencies.";
//  }

// DO NOT USE THIS!!!
//  public void calc_byLeastSquares_WRONG() { // WRONGGGGGGGGG!
//    int n = pop.size();
////    log.info("pop=\n"+ pop);
//    for (int i = 0; i < n; i++) {
//      double[] x = AlleleAnalysisFactory.makeVector(i, pop);
////      log.info("x[]=\n"+ DoubleArr.toString(x));
//      for (int j = 0; j < i; j++) {
//        double[] y = AlleleAnalysisFactory.makeVector(j, pop);
////        log.info("y[]=\n"+ DoubleArr.toString(y));
//        double xy = Stats.cov(x, y);
//        double xx = Stats.cov(x, x);
//        double yy = Stats.cov(y, y);
//
//        double newR = 0.5 * xy * (1./xx + 1./yy);
//        set(i,j, newR);
//      }
//    }
////    log.info("corrected rmtrx=\n"+this.toString());
//  }
}
