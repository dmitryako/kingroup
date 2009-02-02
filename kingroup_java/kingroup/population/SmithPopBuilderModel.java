package kingroup.population;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.model.KinshipIBDModelV1;

import javax.iox.LOG;
/**
 * Model for the Model-View-Controller
 * Data structure for the example #3 from p1331 of
 * Smith et al Genetics, 158: 1329-1338 (2001)
 * hence the name Population-Smith
 */
public class SmithPopBuilderModel extends MendelPopBuilderModel {
  final public static String REFERENCE = "Smith BR et al. Genetics (2001) 158, 1329-1338";
  final public static String REFERENCE_SMITH_ET_AL_2001 = "Example3 from Smith et al. (2001) Genetics 158, p.1331";
  public static int FREQ_EQUAL = 0;
  public static int FREQ_SMITH_NONEQUAL = 1;
  public static int FREQ_SMITH_MIXED = 2;
  public static int FREQ_RANDOM = 3;
  private int freqDistribution;
  private boolean freqTargetSample;
  private boolean incParents;
  private KinshipIBDModelV1 primIdentity = new KinshipIBDModelV1();
  private KinshipIBDModelV1 nullIdentity = new KinshipIBDModelV1();
  private int numFullSibGroups; // number of kin groups
  private int groupSize;
  private int numHalfSibGroups;
  private int nHalfSibs2;
  private int size;
  private int skew;
  public void trace(Object from) {
    if (!LOG.isTraceOn())
      return;
    LOG.trace(from, "SmithPopBuilderModel.");
    LOG.trace(from, "getNumFullSibGroups()=", getNumFullSibGroups());
    LOG.trace(from, "getGroupSize()=", getGroupSize());
    LOG.trace(from, "getNumHalfSibGroups()=", getNumHalfSibGroups());
    LOG.trace(from, "getnHalfSibs2()=", getnHalfSibs2());
    LOG.trace(from, "getNumLoci()=", getNumLoci());
    LOG.trace(from, "getMaxNumAlleles()=", getNumAlleles());
    LOG.trace(from, "getFreqTargetSample()=", getFreqTargetSample());
    LOG.trace(from, "getIncParents()=", getIncParents());
    LOG.trace(this, "getFreqDistribution()=", getFreqDistribution());
  }
  public void loadDefaults() {
    primIdentity.loadDefault();
    primIdentity.setRm(0.5f);
    primIdentity.setRp(0.5f);
    nullIdentity.loadDefault();
  }
  /**
   * Shallow copy
   */
  public void copyTo(SmithPopBuilderModel model) {
    super.copyTo(model);
    model.setPrimIdentity(getPrimIdentity());
    model.setNullIdentity(getNullIdentity());
    model.setNumFullSibGroups(getNumFullSibGroups());
    model.setGroupSize(getGroupSize());
    model.setNumHalfSibGroups(getNumHalfSibGroups());
    model.setnHalfSibs2(getnHalfSibs2());
    model.setFreqDistribution(getFreqDistribution());
    model.setFreqTargetSample(getFreqTargetSample());
    model.setIncParents(getIncParents());
    model.setSize(getSize());
    model.setSkew(getSkew());
  }
  final public boolean isFreqRandom() {
    return freqDistribution == FREQ_RANDOM;
  }
  final public boolean isFreqEqual() {
    return freqDistribution == FREQ_EQUAL;
  }
  final public boolean isFreqSmithNonequal() {
    return freqDistribution == FREQ_SMITH_NONEQUAL;
  }
  final public boolean isFreqSmithMixed() {
    return freqDistribution == FREQ_SMITH_MIXED;
  }
  final public void setFreqDistribution(int v) {
    freqDistribution = v;
  }
  final public int getFreqDistribution() {
    return freqDistribution;
  }
  final public void setFreqTargetSample(boolean v) {
    freqTargetSample = v;
  }
  final public boolean getFreqTargetSample() {
    return freqTargetSample;
  }
  final public void setIncParents(boolean b) {
    incParents = b;
  }
  final public boolean getIncParents() {
    return incParents;
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
  final public void setNumFullSibGroups(int v) {
    numFullSibGroups = v;
  }
  final public int getNumFullSibGroups() {
    return numFullSibGroups;
  }
  final public void setGroupSize(int v) {
    groupSize = v;
  }
  final public int getGroupSize() {
    return groupSize;
  }
  final public void setNumHalfSibGroups(int v) {
    numHalfSibGroups = v;
  }
  final public int getNumHalfSibGroups() {
    return numHalfSibGroups;
  }
  public int getSize() {
    return size;
  }
  public void setSize(int size) {
    this.size = size;
  }
  public int getSkew() {
    return skew;
  }
  public void setSkew(int skew) {
    this.skew = skew;
  }
  public void loadTestData() {
    super.loadTestData();
    KinshipIBDModelV1 primModel = new KinshipIBDModelV1();
    primModel.loadSimple();
    KinshipIBDModelV1 nullModel = new KinshipIBDModelV1();
    nullModel.loadComplex();
    setPrimIdentity(primModel);
    setNullIdentity(nullModel);
    setNumLoci(2);
    setNumAlleles(4);
    setNumFullSibGroups(5);
    setGroupSize(10);
    setFreqDistribution(kingroup.population.SmithPopBuilderModel.FREQ_SMITH_NONEQUAL);
    setFreqTargetSample(true);
    setSize(10);
    setSkew(0);
    setNumHalfSibGroups(0);
    setnHalfSibs2(0);
  }
  public int getnHalfSibs2() {
    return nHalfSibs2;
  }
  public void setnHalfSibs2(int nHalfSibs2) {
    this.nHalfSibs2 = nHalfSibs2;
  }
}