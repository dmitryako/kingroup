package kingroup_v2.kinship;
import kingroup.genetics.Like;

import javax.textx.FractionDigitsModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/09/2005, Time: 11:53:57
 */
public class Kinship {
  public static final String REFERENCE = "Goodnight & Queller (1999) Molecular Ecology 8 p1231";
  final public static int HAPLOID_EXCLUDE = 0;
  final public static int HAPLOID_MAT = 1;    // assume maternal inheritance
  final public static int HAPLOID_PAT = 2;
  final public static int HAPLOID_IGNORE = 3;

  public static final double RLN_10 = 1.0 / Math.log(10.0);
  private KinshipFileFormat fileFormat;  // FILE FORMAT
  private KinshipIBDComplex complexPrimIBD;  // identity by descent
  private KinshipIBDComplex complexNullIBD;
  private KinshipIBD[]     nullArr;
  private FractionDigitsModel digitsModel = new FractionDigitsModel();
  private boolean displayHalfMatrix;
  private boolean displaySorted;
  private boolean displaySortedById;
  private boolean showColumnHeaders;
//  private boolean saveToFile;
  private boolean displayAscending;
  private boolean displayByGroup;
  private boolean displayLogs;
  private float[] sigLevels;
  private boolean performSigTest;
  private int numSimPairs;
  private boolean displaySigFlag;
//  private boolean displayPValue;
  private int treatHaploid;
  private boolean hasGroupId;
  private float alleleErrorRate;
  private KinshipAlleleFreqOpt alleleFreqOpt;
  private boolean displayPValues;

  public Kinship() {
    init();
  }
  private void init() {
    KinshipAlleleFreqOpt freq = new KinshipAlleleFreqOpt();
    freq.loadDefault();
    setAlleleFreqOpt(freq);

    KinshipFileFormat file = new KinshipFileFormat();
    file.loadDefault();
    setFileFormat(file);

//    primIBDModel = new KinshipIBDComplex();  // identity by descent
    KinshipIBDComplex ibd = new KinshipIBDComplex();
    ibd.loadPrimDefault();
    setComplexPrimIBD(ibd);

//    nullIBDModel = new KinshipIBDComplex();
    ibd = new KinshipIBDComplex();
    ibd.loadNullDefault();
    setComplexNullIBD(ibd);

//    digitsModel = new FractionDigitsModel();
    FractionDigitsModel digits = new FractionDigitsModel();
    digits.loadDefault();
    setDigitsModel(digits);
    sigLevels = new float[0];

    nullArr = new KinshipIBD[1];
    nullArr[0] = getComplexNullIBD();
  }
  public void loadDefault() {
    fileFormat.loadDefault();
    alleleFreqOpt.loadDefault();
    complexPrimIBD.loadPrimDefault();
    complexNullIBD.loadNullDefault();
    digitsModel.loadDefault();
    setDisplayPValues(false);
    setDisplayLogs(true);
    setDisplayHalfMatrix(true);
    setDisplaySorted(false);
    setDisplaySortedById(false);
    setShowColumnHeaders(true);
//    setSaveToFile(false);
    setDisplayAscending(false);
    setDisplaySigFlag(true);
//    setDisplayPValue(true);
    setDisplayByGroup(true);
    setPerformSigTest(false);
    setNumSimPairs(1000);
    setSigLevels(new float[]{0.05f, 0.01f, 0.001f});
    setTreatHaploid(Kinship.HAPLOID_EXCLUDE);
    setAlleleErrorRate(0f);
    setHasGroupId(false);
  }
  private void setSigLevels(float[] a) {
    sigLevels = a;
  }
  final public double logToView(double d) {
    if (d == Like.MAX_LOG)
      return Like.MAX_LOG;

    if (getDisplayLogs())
    {
      if (d == Like.MIN_LOG)
        return Like.MIN_LOG;
      return d * RLN_10; // 1.0 / Ln10  // converting to base of 10
    }
    else {
      if (d == Like.MIN_LOG)
        return 0;
      return Math.exp(d);
    }
  }
  final public String formatLog(double d) {
    d = logToView(d);
    if (d == Like.MIN_LOG)
      return "x";
    if (d == Like.MAX_LOG)
      return "max";
    return digitsModel.format(d);
  }
  final public String format(double d) {
    return digitsModel.format(d);
  }
  public KinshipIBDComplex getComplexPrimIBD() {
    return complexPrimIBD;
  }
  public KinshipIBD getPrimIBD() {
    return complexPrimIBD;
  }
  public void setComplexPrimIBD(KinshipIBDComplex complexPrimIBD) {
    this.complexPrimIBD = complexPrimIBD;
  }
  public KinshipIBDComplex getComplexNullIBD() {
    return complexNullIBD;
  }
  public KinshipIBD getNullIBD() {
    return complexNullIBD;
  }
  public void setComplexNullIBD(KinshipIBDComplex complexNullIBD) {
    this.complexNullIBD = complexNullIBD;
  }
  public void setDisplayHalfMatrix(boolean displayHalfMatrix) {
    this.displayHalfMatrix = displayHalfMatrix;
  }
  public boolean getDisplayHalfMatrix() {
    return displayHalfMatrix;
  }
  public void setDisplaySorted(boolean displaySorted) {
    this.displaySorted = displaySorted;
  }
  public boolean getDisplaySorted() {
    return displaySorted;
  }
  public void setDisplayByGroup(boolean displayByGroup) {
    this.displayByGroup = displayByGroup;
  }
  public boolean getDisplayByGroup() {
    return displayByGroup;
  }
  public boolean getDisplayLogs() {
    return displayLogs;
  }
  public void setDisplayLogs(boolean displayLogs) {
    this.displayLogs = displayLogs;
  }
  public FractionDigitsModel getDigitsModel() {
    return digitsModel;
  }
  public void setDigitsModel(FractionDigitsModel digitsModel) {
    this.digitsModel = digitsModel;
  }
  public KinshipFileFormat getFileFormat() {
    return fileFormat;
  }
  public void setFileFormat(KinshipFileFormat fileFormat) {
    this.fileFormat = fileFormat;
  }
  public boolean getPerformSigTest() {
    return performSigTest;
  }
  public void setPerformSigTest(boolean performSigTest) {
    this.performSigTest = performSigTest;
  }
  public int getNumSimPairs() {
    return numSimPairs;
  }
  public void setNumSimPairs(int numSimPairs) {
    this.numSimPairs = numSimPairs;
  }
  public float[] getSigLevels() {
    return sigLevels;
  }
  public void setDisplaySigFlag(boolean displaySigFlag) {
    this.displaySigFlag = displaySigFlag;
  }
  public boolean getDisplaySigFlag() {
    return displaySigFlag;
  }
//  public boolean getDisplayPValue() {
//    return displayPValue;
//  }
//  public void setDisplayPValue(boolean displayPValue) {
//    this.displayPValue = displayPValue;
//  }
  public int getTreatHaploid() {
    return treatHaploid;
  }
  public void setTreatHaploid(int treatHaploid) {
    this.treatHaploid = treatHaploid;
  }

  public boolean getHasGroupId()
  {
    return hasGroupId;
  }

  public void setHasGroupId(boolean hasGroupId)
  {
    this.hasGroupId = hasGroupId;
  }

  public KinshipIBD[] getNullArr()
  {
    return nullArr;
  }

  public void setNullArr(KinshipIBD[] nullArr)
  {
    this.nullArr = nullArr;
  }

  public float getAlleleErrorRate()
  {
    return alleleErrorRate;
  }

  public void setAlleleErrorRate(float alleleErrorRate)
  {
    this.alleleErrorRate = alleleErrorRate;
  }

  public KinshipAlleleFreqOpt getAlleleFreqOpt()
  {
    return alleleFreqOpt;
  }

  public void setAlleleFreqOpt(KinshipAlleleFreqOpt alleleFreqOpt)
  {
    this.alleleFreqOpt = alleleFreqOpt;
  }

  public boolean getDisplaySortedById()
  {
    return displaySortedById;
  }

  public void setDisplaySortedById(boolean displaySortedById)
  {
    this.displaySortedById = displaySortedById;
  }

  public boolean getDisplayAscending()
  {
    return displayAscending;
  }

  public void setDisplayAscending(boolean displayAscending)
  {
    this.displayAscending = displayAscending;
  }

  public void setDisplayPValues(boolean displayPValues)
  {
    this.displayPValues = displayPValues;
  }

  public boolean getDisplayPValues()
  {
    return displayPValues;
  }

//  public boolean getSaveToFile()
//  {
//    return saveToFile;
//  }
//
//  public void setSaveToFile(boolean saveToFile)
//  {
//    this.saveToFile = saveToFile;
//  }

  public boolean getShowColumnHeaders() {
    return showColumnHeaders;
  }

  public void setShowColumnHeaders(boolean showColumnHeaders) {
    this.showColumnHeaders = showColumnHeaders;
  }
}
