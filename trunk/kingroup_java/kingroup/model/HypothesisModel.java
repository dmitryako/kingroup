package kingroup.model;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import java.text.DecimalFormat;
import java.text.NumberFormat;
public class HypothesisModel
  extends KinshipIBDModelV1 {
  public static final double RLN_10 = 1.0 / Math.log(10.0);
  public static final int THIS_FRACTION_DIGITS_IS_SCIENTIFIC = 6;
  private final static String SCIENTIFIC_FORMAT = "0.###E0";
  private final static DecimalFormat scientific_ = new DecimalFormat(SCIENTIFIC_FORMAT);
  private boolean displayByGroup;
  private boolean headerWithGroupId;
  private String displayGroup;
  private boolean displayHalfMatrix;
  private boolean displayUpperHalf;
  private boolean displaySorted;
  private int maxFractionDigits;
  final public static int EXCLUDE_HAPLOIDS = 0;
  final public static int ASSUME_MAT = 1;    // assume maternal inheritance
  final public static int ASSUME_PAT = 2;
  private int treatHaploids;
  final public void loadDefault() {
    //super.loadDefault();
    setDisplayLogs(true);
    setDisplayHalfMatrix(true);
    setDisplayUpperHalf(false);
    setDisplaySorted(false);
    setDisplayByGroup(true);
    setHeaderWithGroupId(true);
    setDisplayGroup("");
    setMaxFractionDigits(2);
    setTreatHaploid(EXCLUDE_HAPLOIDS);
  }
  public HypothesisModel getFormatModel() {
    return this;
  }
  final public double logToView(double d) {
    if (getDisplayLogs())
      return d * RLN_10; // 1.0 / Ln10  // converting to base of 10
    else
      return Math.exp(d);
  }
  final public String format(double d) {
    d = logToView(d);
    if (getMaxFractionDigits() == THIS_FRACTION_DIGITS_IS_SCIENTIFIC)
      return scientific_.format(d);
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(getMaxFractionDigits());
    return nf.format(d);
  }
  public HypothesisModel() {
  }
  public HypothesisModel(KinshipIBDModelV1 from) {
    this.loadDefault();
    from.copyTo(this);
  }
  // The displayLogs property
  private boolean displayLogs;
  final public boolean getDisplayLogs() {
    return displayLogs;
  }
  final public void setDisplayLogs(boolean s) {
    displayLogs = s;
  }
  final public boolean getDisplayByGroup() {
    return displayByGroup;
  }
  final public void setDisplayByGroup(boolean s) {
    displayByGroup = s;
  }
  final public boolean getHeaderWithGroupId() {
    return headerWithGroupId;
  }
  final public void setHeaderWithGroupId(boolean s) {
    headerWithGroupId = s;
  }
  final public String getDisplayGroup() {
    return displayGroup;
  }
  final public void setDisplayGroup(String s) {
    displayGroup = s;
  }
  final public boolean getDisplayHalfMatrix() {
    return displayHalfMatrix;
  }
  final public void setDisplayHalfMatrix(boolean s) {
    displayHalfMatrix = s;
  }
  final public boolean getDisplayUpperHalf() {
    return displayUpperHalf;
  }
  final public void setDisplayUpperHalf(boolean s) {
    displayUpperHalf = s;
  }
  final public boolean getDisplaySorted() {
    return displaySorted;
  }
  final public void setDisplaySorted(boolean s) {
    displaySorted = s;
  }
  final public int getMaxFractionDigits() {
    return maxFractionDigits;
  }
  final public void setMaxFractionDigits(int s) {
    maxFractionDigits = s;
  }
  final public void copyTo(HypothesisModel bean) {
    super.copyTo(bean);
    bean.setRm(getRm());
    bean.setRmMax(getRmMax());
    bean.setRp(getRp());
    bean.setRpMax(getRpMax());
    bean.setTreatHaploid(getTreatHaploid());
    bean.setComplex(getComplex());
    bean.setDisplayByGroup(getDisplayByGroup());
    bean.setDisplayGroup(getDisplayGroup());
    bean.setDisplayLogs(getDisplayLogs());
    bean.setDisplayHalfMatrix(getDisplayHalfMatrix());
    bean.setDisplayUpperHalf(getDisplayUpperHalf());
    bean.setDisplaySorted(getDisplaySorted());
    bean.setMaxFractionDigits(getMaxFractionDigits());
  }
  public int getTreatHaploid() {
    return treatHaploids;
  }
  public void setTreatHaploid(int treatHaploids) {
    this.treatHaploids = treatHaploids;
  }
}

