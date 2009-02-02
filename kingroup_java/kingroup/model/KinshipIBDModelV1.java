package kingroup.model;
import kingroup_v2.kinship.KinshipIBDComplex;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
/**
 * DEPRICATED!!! This class was left for compatibility with KinGroupV1
 * Use KinshipIBDComplex and Kinship
 */
public class KinshipIBDModelV1 extends KinshipIBDComplex
{
  public KinshipIBDModelV1() {
  }
  public KinshipIBDModelV1(float rm, float rp) {
    setRm(rm);
    setRp(rp);
  }
  public KinshipIBDModelV1(KinshipIBDModelV1 from) {
    from.copyTo(this);
  }
  public HypothesisModel getFormatModel() {
    return new HypothesisModel(this);
  }
}

