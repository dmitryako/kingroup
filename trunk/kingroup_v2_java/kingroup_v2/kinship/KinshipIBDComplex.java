package kingroup_v2.kinship;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/09/2005, Time: 09:12:24
 */
public class KinshipIBDComplex extends KinshipIBD {
  private boolean complex;
  private double rmMax;  // identical by maternal window probability
  private double rpMax;  // identical by paternal window probability
  private int numRms; // number of Rm's
  private int numRps; // number of Rp's
//  private float alleleErrorRate;
//  private boolean showAlleleErrorRate;

  public void copyTo(KinshipIBDComplex model) {
    super.copyTo(model);
    model.setRmMax(getRmMax());
    model.setRpMax(getRpMax());
    model.setNumRms(getNumRms());
    model.setNumRps(getNumRps());
    model.setComplex(getComplex());
//    model.setShowAlleleErrorRate(getShowAlleleErrorRate());
//    model.setAlleleErrorRate(getAlleleErrorRate());
  }
  public void loadDefault() {
    super.loadDefault();
    setComplex(false);
    setRm(0);
    setRmMax(1);
    setRp(0);
    setRpMax(1);
    setNumRms(2);
    setNumRps(2);
//    setAlleleErrorRate(0f);
//    setShowAlleleErrorRate(false);
  }
  public boolean contains(double rm, double rp) {
    return ((float) rm == (float)getRm() && (float)rp == (float)getRp())
      || ((float) rp == (float)getRm() && (float)rm == (float)getRp());
  }
  public void loadSimple() {
    setComplex(false);
    setRm(0.5);
    setRp(0.5);
  }
  public void loadComplex() {
    setComplex(true);
    setRm(0.0f);
    setRp(0.0f);
    setRmMax(0.5f);
    setRpMax(0.5f);
    setNumRms(2);
    setNumRps(3);
  }
  final public void loadPrimDefault() {
    loadDefault();
    setRm(0.5f);
    setRp(0.5f);
  }
  final public void loadNullDefault() {
    loadDefault();
    setRm(0.0f);
    setRp(0.0f);
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append("\ncomplex=" + getComplex());
    buff.append("\nRm=" + getRm());
    buff.append("\nRp=" + getRp());
    buff.append("\nRmMax=" + getRmMax());
    buff.append("\nRpMax=" + getRpMax());
    buff.append("\nnumRms=" + getNumRms());
    buff.append("\nnumRps=" + getNumRps());
//    buff.append("\ntreatHaploids=" + getTreatHaploid());
    return buff.toString();
  }
  final public boolean getComplex() {
    return complex;
  }
  final public void setComplex(boolean s) {
    complex = s;
  }
  final public double getRmMax() {
    return rmMax;
  }
  final public void setRmMax(double s) {
    rmMax = s;
  }
  final public double getRpMax() {
    return rpMax;
  }
  final public void setRpMax(double s) {
    rpMax = s;
  }
  final public int getNumRms() {
    return numRms;
  }
  final public void setNumRms(int s) {
    numRms = s;
  }
  final public int getNumRps() {
    return numRps;
  }
  final public void setNumRps(int s) {
    numRps = s;
  }

//  public float getAlleleErrorRate()
//  {
//    return alleleErrorRate;
//  }
//
//  public void setAlleleErrorRate(float alleleErrorRate)
//  {
//    this.alleleErrorRate = alleleErrorRate;
//  }
//
//  public boolean getShowAlleleErrorRate()
//  {
//    return showAlleleErrorRate;
//  }
//
//  public void setShowAlleleErrorRate(boolean showAlleleErrorRate)
//  {
//    this.showAlleleErrorRate = showAlleleErrorRate;
//  }
}
