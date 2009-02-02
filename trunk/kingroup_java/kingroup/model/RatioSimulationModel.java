package kingroup.model;
import javax.iox.LOG;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 4, 2004, Time: 2:52:29 PM
 */
public class RatioSimulationModel { // Tested in RatioSimulationBeanTest
  private KinshipIBDModelV1 primIdentity = new KinshipIBDModelV1();
  private KinshipIBDModelV1 nullIdentity = new KinshipIBDModelV1();
  private int simulationSize;
  public void trace(Object from) {
    if (!LOG.isTraceOn())
      return;
  }
  public void loadDefaults() {
    primIdentity.loadDefault();
    primIdentity.setRm(0.5f);
    primIdentity.setRp(0.5f);
    nullIdentity.loadDefault();
    simulationSize = 1000;
  }
  /**
   * Shallow copy
   */
  public void copyTo(RatioSimulationModel model) {
    model.setPrimIdentity(getPrimIdentity());
    model.setNullIdentity(getNullIdentity());
    model.setSimulationSize(getSimulationSize());
  }
  final public void setPrimIdentity(KinshipIBDModelV1 v) {
    primIdentity = v;
  }
  final public KinshipIBDModelV1 getPrimIdentity() {
    return primIdentity;
  }
  final public void setNullIdentity(KinshipIBDModelV1 v) {
    nullIdentity = v;
  }
  final public KinshipIBDModelV1 getNullIdentity() {
    return nullIdentity;
  }
  final public void setSimulationSize(int i) {
    simulationSize = i;
  }
  final public int getSimulationSize() {
    return simulationSize;
  }
}
