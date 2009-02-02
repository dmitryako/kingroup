package kingroup.partition;
import kingroup.genetics.PartitionSearchModel;
import kingroup.model.HypothesisModel;
import kingroup.model.KinshipIBDModelV1;
import kingroup.project.KinGroupProjectModel;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Jul 8, 2004, Time: 10:43:12 AM
 */

// Facade class, p368 Larman 
public class PartitionModel {
  private HypothesisModel hypoPrim_;
  private KinshipIBDModelV1 hypoNull_;
  private PartitionSearchModel searchModel_;
  public PartitionModel(KinGroupProjectModel model) {
    hypoPrim_ = model.getPrimModel();
    hypoNull_ = model.getNullModel();
    searchModel_ = model.getKinGroupSearchModel();
  }
  public PartitionModel(HypothesisModel prim, KinshipIBDModelV1 nullModel
    , PartitionSearchModel search) {
    hypoPrim_ = prim;
    hypoNull_ = nullModel;
    searchModel_ = search;
  }
  public void loadDefaults() {
    hypoPrim_.loadPrimDefault();
    hypoNull_.loadNullDefault();
    searchModel_.loadDefaults();
  }
  final public HypothesisModel getHypoPrimModel() {
    return hypoPrim_;
  }
  final public KinshipIBDModelV1 getHypoNullModel() {
    return hypoNull_;
  }
  final public PartitionSearchModel getSearchModel() {
    return searchModel_;
  }
}
