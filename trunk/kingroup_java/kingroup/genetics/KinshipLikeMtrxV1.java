package kingroup.genetics;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genotype.Genotype;
import kingroup.model.KinshipIBDModelV1;
import kingroup.population.PopGroup;

import javax.vecmathx.matrix.LowTriangleMatrix;
import javax.vecmathx.matrix.MtrxReadI;

public class KinshipLikeMtrxV1 extends LowTriangleMatrix
  implements MtrxReadI
{
  private PopGroup data_; // input data
  private KinshipIBDModelV1 hypo;
  private KinshipIBDModelV1 exclude;
  public KinshipLikeMtrxV1(PopGroup data, KinshipIBDModelV1 hypo) {
    super(data.size());
    data_ = data;
    this.hypo = hypo;
  }
  public KinshipLikeMtrxV1(PopGroup data
    , KinshipIBDModelV1 hypo, KinshipIBDModelV1 exclude) {
    super(data.size());
    data_ = data;
    this.hypo = hypo;
    this.exclude = exclude;
  }
  final public KinshipIBDModelV1 getIdentity() {
    return hypo;
  }
  final public PopGroup getGenotypeData() {
    return data_;
  }
  // PRECOND: c < size  &&  r < size
  final public double getLog(int r, int c) {
    if (r == c)
      return Like.MIN_LOG; // NOTE!!! This is used in KinshipLikeMtrxV1.sort()
    return get(r, c);
  }
  final public double getLog(Genotype g, Genotype g2) {
    int i = data_.find(g);
    if (i < 0)
      return Like.MIN_LOG;
    int i2 = data_.find(g2);
    if (i2 < 0)
      return Like.MIN_LOG;
    return getLog(i, i2);
  }
  final public boolean calculateLogs() {
    KinshipLikeHypo like = new KinshipLikeHypo(hypo, exclude);
    for (int i = 0; i < size(); i++) {
      Genotype a = data_.getGenotype(i);
      for (int j = i + 1; j < size(); j++) { // NOTE: i < j
        Genotype b = data_.getGenotype(j);
        double d = like.calculateHypothesisLog(a, b);
        set(i, j, d); // i < j // only above diagonal elements are calculated
      }
    }
    return true;
  }
}
