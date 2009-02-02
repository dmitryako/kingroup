package kingroup.genetics;
import kingroup.KinGroupError;
import kingroup.genotype.Loci;
import kingroup.genotype.Locus;
import kingroup.model.HypothesisModel;
import kingroup.model.KinshipIBDModelV1;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 24, 2004, Time: 2:14:43 PM
 */
public class KinshipLikeHypo extends LikeKinship {
  private LikeDiploid likeDip;
  private LikeHaploDip likeHap;
  private KinshipIBDModelV1 hypo;
  private KinshipIBDModelV1 exclude;
//  private Kinship
  public KinshipLikeHypo(HypothesisModel hypo) {
    this.hypo = hypo;
  }
  public KinshipLikeHypo(KinshipIBDModelV1 hypo
    , KinshipIBDModelV1 exclude) {
    this.hypo = hypo;
    this.exclude = exclude;
  }
  final public double calculateHypothesis(Loci a, Loci b) {
    return logToProb(calculateHypothesisLog(a, b));
  }
  final public double calculateHypothesisLog(Loci a, Loci b) {
    double rm = hypo.getRm();
    double rp = hypo.getRp();
    if (hypo.getComplex()) {
      double rms = hypo.getRmMax();
      double rps = hypo.getRpMax();
      return searchBestFit(a, b, rm, rms, rp, rps);
    } else
      return calculateLog(a, b, rm, rp);
  }
  final private double calculateLogWithExclude(Loci a, Loci b
    , double rm, double rp) {
    if (exclude != null && exclude.contains(rm, rp))
      return Like.MIN_LOG;
    return calculateLog(a, b, rm, rp);
  }
  final private double calculateLog(Loci a, Loci b, double rm, double rp) {
    likeDip = new LikeDiploid(rm, rp);
//    if (hypo.getTreatHaploid() == HypothesisModel.ASSUME_MAT)
//      likeHap = new LikeHaploDip(rm);
//    else
//      likeHap = new LikeHaploDip(rp);
    double sum = 0;
    for (int ia = 0; ia < a.getNumLoci(); ia++) {
      Locus loca = a.getLocus(ia);
      Locus locb = b.getLocus(ia);
      if (a == null || b == null)
        return Like.MIN_LOG; // does not match at all; different data sets?
      double d = calculateLocusLog(loca, locb);
      if (d == Like.MIN_LOG) {
        sum = Like.MIN_LOG;
        break;
      }
      sum += d;
    }
    return sum;
  }
  // PRECOND: a != null  && b != null
  //    a.size() != 0  &  b.size() != 0
  final private double calculateLogNotDip(Locus a, Locus b) {
    // [041213dk] NOTE: this deals with missing loci. Zero will just ignore it
    if (a.size() == 0 || b.size() == 0)
      return 0;
//    if ((a.size() == 1 || b.size() == 1)
//      && hypo.getTreatHaploid() == HypothesisModel.EXCLUDE_HAPLOIDS)
//      return Like.MIN_LOG;
    if (a.size() == 1 && b.size() == 1)
      return likeHap.calculateHaploidLog(a.get(0), b.get(0));
    if (a.size() == 2 && b.size() == 1)
      return likeHap.calculateHaploDipLog(a, b.get(0));
    if (a.size() == 1 && b.size() == 2)
      return likeHap.calculateHaploDipLog(b, a.get(0));
    return Like.MIN_LOG;
  }
  // PRECOND: a != null  && b != null
  final private double calculateLocusLog(Locus a, Locus b) {
    if (a.size() == Locus.DIPLOID
      && b.size() == Locus.DIPLOID)
      return likeDip.calcDiploidLog(a, b);
    return calculateLogNotDip(a, b);
  }
  private double searchBestFit(Loci a, Loci b, double rm, double rms
    , double rp, double rps) {
    //using  calculateLog() findFirstIdxSLOW best fit
    if (rm == rms && rp == rps)
      return calculateLog(a, b, rm, rp);
    if (rm == rms)
      return searchBestFit(a, b, rm, rp, rps, true, hypo.getNumRps());
    if (rp == rps)
      return searchBestFit(a, b, rm, rp, rms, false, hypo.getNumRms());
    return searchBest2DFit(a, b, rm, rms, rp, rps);
  }
  private double searchBestFit(Loci a, Loci b
    , double rm, double rp
    , double rs, boolean isRps, int n) {
    if (hypo == null) {
      String mssg = "hypo == null";
      LOG.error(this, mssg, "");
      throw new KinGroupError(mssg);
    }
    if (n < 2) {
      String mssg = "n < 2";
      LOG.error(this, mssg, "");
      throw new KinGroupError(mssg);
    }
    double[] arr = new double[n];
    double r = rm;
    if (isRps)
      r = rp;
    double step = (rs - r) / (n - 1);
    for (int i = 0; i < n; i++) {
      double tmp = r + step * i;
      if (isRps)
        arr[i] = calculateLogWithExclude(a, b, rm, tmp);
      else
        arr[i] = calculateLogWithExclude(a, b, tmp, rp);
    }
    int idx = -1;
    idx = Vec.maxIdx(arr, arr.length);
    if (idx == -1) {
      String mssg = "idx == -1";
      LOG.error(this, mssg, "");
      throw new KinGroupError(mssg);
    }
    return arr[idx];
  }
  private double searchBest2DFit(Loci a, Loci b
    , double rm, double rms, double rp, double rps) {
    if (hypo == null
      || hypo.getNumRms() < 2
      || hypo.getNumRps() < 2
      )
      return Like.MIN_LOG;
    int n = hypo.getNumRms();
    double[] arr = new double[n];
    double step = (rms - rm) / (n - 1);
    for (int i = 0; i < n; i++) {
      double tmp = rm + step * i;
      arr[i] = searchBestFit(a, b, tmp, rp, rps, true, hypo.getNumRps());
    }
    int idx = -1;
    idx = Vec.maxIdx(arr, arr.length);
    if (idx == -1)
      return Like.MIN_LOG;
    return arr[idx];
  }
}
