package kingroup.genetics;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
import kingroup.model.KinshipIBDModelV1;
import kingroup.population.PopGroup;
final public class KinshipRatioMtrxV1 extends KinshipLikeMtrxV1 {
  private KinshipIBDModelV1 hypoNull_; // hypothesis
  private KinshipLikeMtrxV1 null_ = null;
  private KinshipLikeMtrxV1 prim_ = null;
  public KinshipRatioMtrxV1(PopGroup data
    , KinshipIBDModelV1 hypo
    , KinshipIBDModelV1 hypo2) {
    super(data, hypo);
    hypoNull_ = hypo2;
  }
  final public KinshipIBDModelV1 getPrimaryIdentity() {
    return getIdentity();
  }
  final public KinshipIBDModelV1 getNullIdentity() {
    return hypoNull_;
  }
  final public KinshipLikeMtrxV1 getPrim() {
    return prim_;
  }
  final public KinshipLikeMtrxV1 getNull() {
    return null_;
  }
  public boolean calculateRatios() {
    if (hypoNull_ == null)
      return false;
    if (!calculateLogs()) // calculate primary
      return false;
    KinshipLikeMtrxV1 nullL = new KinshipLikeMtrxV1(getGenotypeData()
      , hypoNull_, getPrimaryIdentity());
    if (nullL == null || !nullL.calculateLogs())
      return false;
    load(this, nullL);
    return true;
  }
  public boolean calculateAll() {
    if (hypoNull_ == null)
      return false;
    prim_ = new KinshipLikeMtrxV1(getGenotypeData(), getPrimaryIdentity());
    if (prim_ == null || !prim_.calculateLogs())
      return false;
    null_ = new KinshipLikeMtrxV1(getGenotypeData()
      , getNullIdentity(), getPrimaryIdentity());
    if (null_ == null || !null_.calculateLogs())
      return false;
    load(prim_, null_);
    return true;
  }
  private void load(KinshipLikeMtrxV1 primL, KinshipLikeMtrxV1 nullL) {
    for (int r = 0; r < size(); r++) {
      for (int c = 0; c < r; c++) {
        double primLog = primL.get(c, r);
        double nullLog = nullL.get(c, r);
        if (primLog == Like.MIN_LOG || nullLog == Like.MIN_LOG)
          set(c, r, Like.MIN_LOG);
        else
          set(c, r, primLog - nullLog);
      }
    }
  }
}