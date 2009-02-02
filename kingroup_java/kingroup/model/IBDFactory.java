package kingroup.model;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 15, 2004, Time: 2:09:18 PM
 */
public class IBDFactory {
  public static KinshipIBDModelV1 makeFullSibs() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadPrimDefault();
    res.setComplex(false);
    res.setRm(0.5f);
    res.setRp(0.5f);
    return res;
  }
  public static KinshipIBDModelV1 makeFullSibsHaplod() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadPrimDefault();
    res.setComplex(false);
    res.setRm(1.f);
    res.setRp(0.5f);
    return res;
  }
  public static KinshipIBDModelV1 makeComplexHalfsibUnrelatedHaplod() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadNullDefault();
    res.setComplex(true);
    res.setRm(0.0f);
    res.setRmMax(1.0f);
    res.setNumRms(2);
    res.setRp(0.0f);
    res.setRpMax(0.0f);
    res.setNumRps(1);
    return res;
  }
  public static KinshipIBDModelV1 makeCensus() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadPrimDefault();
    res.setComplex(true);
    res.setRm(0.75f);
    res.setRp(0.99999f);
    res.setRmMax(1.0f);
    res.setRpMax(1.0f);
    res.setNumRms(2);
    res.setNumRps(2);
    return res;
  }
  public static KinshipIBDModelV1 makeParentOffspring() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadPrimDefault();
    res.setComplex(false);
    res.setRm(0.9999f);
    res.setRp(0f);
    return res;
  }
  public static KinshipIBDModelV1 makeUnrelated() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadNullDefault();
    res.setComplex(false);
    res.setRm(0.0f);
    res.setRp(0.0f);
    return res;
  }
  public static KinshipIBDModelV1 makeComplexFullSibsUnrelated() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadNullDefault();
    res.setComplex(true);
    res.setRm(0.0f);
    res.setRmMax(0.5f);
    res.setNumRms(2);
    res.setRp(0.0f);
    res.setRpMax(0.5f);
    res.setNumRps(2);
    return res;
  }
  public static KinshipIBDModelV1 makeComplexParentUnrelated() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadNullDefault();
    res.setComplex(true);
    res.setRm(0.0f);
    res.setRmMax(1.0f);
    res.setNumRms(2);
    res.setRp(0.0f);
    res.setRpMax(0.0f);
    res.setNumRps(1);
    return res;
  }
  public static KinshipIBDModelV1 makeComplexHalfsibUnrelated() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadNullDefault();
    res.setComplex(true);
    res.setRm(0.0f);
    res.setRmMax(0.5f);
    res.setNumRms(2);
    res.setRp(0.0f);
    res.setRpMax(0.0f);
    res.setNumRps(1);
    return res;
  }
  public static KinshipIBDModelV1 makeComplexFullsibParentUnrelated() {
    KinshipIBDModelV1 res = new KinshipIBDModelV1();
    res.loadNullDefault();
    res.setComplex(true);
    res.setRm(0.0f);
    res.setRmMax(1.0f);
    res.setNumRms(3);
    res.setRp(0.0f);
    res.setRpMax(0.5f);
    res.setNumRps(2);
    return res;
  }
}
