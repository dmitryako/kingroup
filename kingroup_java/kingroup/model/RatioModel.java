package kingroup.model;
import kingroup.genetics.KinPairSimModel;
import kingroup.project.KinGroupProjectModel;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Jul 19, 2004, Time: 10:50:38 AM
 */
// Facade class, p368 Larman
public class RatioModel {
  private HypothesisModel primModel_;
  private HypothesisModel nullModel_;
  private KinPairSimModel simModel_;
  public RatioModel(KinGroupProjectModel model) {
    primModel_ = model.getPrimModel();
    nullModel_ = model.getNullModel();
    simModel_ = model.getSimulationModel();
  }
  public HypothesisModel getHypoPrimModel() {
    return primModel_;
  }
  public HypothesisModel getHypoNullModel() {
    return nullModel_;
  }
  public KinPairSimModel getSimulationModel() {
    return simModel_;
  }
}
