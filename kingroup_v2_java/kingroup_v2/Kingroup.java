package kingroup_v2;
import kingroup.project.KinGroupProjectModel;
import kingroup_v2.cervus.Cervus;
import kingroup_v2.fsr.FsrLowerBoundBean;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipFileFormat;
import kingroup_v2.like.milligan.Milligan;
import kingroup_v2.like.thompson.Thompson;
import kingroup_v2.pop.sample.PopBuilderModel;
import tsvlib.project.Project;
import tsvlib.project.ProjectModel;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/09/2005, Time: 15:39:47
 */
public class Kingroup extends Project {
  public static final int KINSHIP_FILE = 0;
  public static final int CERVUS_FILE = 1;
  private int fileType;

  private static int fsr_count = 0;
  public static final int FSR_SDR = fsr_count++;
  public static final int FSR_MS2 = fsr_count++;
  public static final int FSR_MS = fsr_count++;
  public static final int FSR_DR = fsr_count++;
  public static final int FSR_SIMPS = fsr_count++;
  public static final int FSR_MCS = fsr_count++;
  private int fsrAlg;

  private String errorLogFileName;
  private String lastImportedFileName;
  private String lastImportedCervusParentage;
  private String lastSavedFileName;
  private PopBuilderModel popBuilder;
  private FsrLowerBoundBean fsrLowerBound;
  private Kinship kinship;
  private Thompson thompson;
  private Milligan milligan;
  private Cervus cervus;

  private boolean diploidSpecie;
  private int simpsAlgNumIter;
  private boolean showUserView;
  private boolean sortByGroup;
  private int msAlgWindSize;
  private boolean sdrKeepLargest;
  private int numAccuracyTrials;
  private int simpsAlgLoopBreaker;

  private int KonHegAlgNumIter;

  private static int countR = 0;
  public static final int PAIRWISE_R_QG = countR++;
  public static final int PAIRWISE_R_QG_NN = countR++;
  public static final int PAIRWISE_R_KINSHIP_ML = countR++;
  public static final int PAIRWISE_R_LL = countR++;
  public static final int PAIRWISE_R_LR = countR++;
  public static final int PAIRWISE_R_LR_NN = countR++;
  public static final int PAIRWISE_R_IDENTIX = countR++;
  public static final int PAIRWISE_R_MILLIGAN = countR++;
  public static final int PAIRWISE_R_KINSHIP_2 = countR++;
  public static final int PAIRWISE_R_KH_INBRED = countR++;
  public static final int PAIRWISE_R_KH_OUTBRED = countR++;
  public static final int PAIRWISE_R_TEST = countR++;
  public static final int PAIRWISE_R_WANG = countR++;
  public static final int PAIRWISE_DIST_SQR = countR++;
  private int pairwiseRType;

  public Kingroup() {
    init();
  }
  private void init() {
    FsrLowerBoundBean fsr = new FsrLowerBoundBean();
    fsr.loadDefaults();
    setFsrLowerBound(fsr);

    PopBuilderModel builder = new PopBuilderModel();
    builder.loadDefaults();
    setPopBuilder(builder);

    Kinship kinship = new Kinship();
    kinship.loadDefault();
    setKinship(kinship);

    Milligan milligan = new Milligan();
    milligan.loadDefault();
    setMilligan(milligan);

    Thompson thompson = new Thompson();
    thompson.loadDefault();
    setThompson(thompson);

    Cervus cervus = new Cervus();
    cervus.loadDefault();
    setCervus(cervus);
  }
  public void loadDefault(String appName, String appVersion) {
    super.loadDefault(appName, appVersion);
    fileType = KINSHIP_FILE;

    setErrorLogFileName("ErrorLog.txt");

    setFsrAlg(FSR_DR);
    setShowUserView(true);
    setSortByGroup(true);
    setDiploidSpecie(true);
    setSimpsAlgNumIter(1000);
    setMsAlgWindSize(2);
    setSdrKeepLargest(false);
    setNumAccuracyTrials(10);
    setSimpsAlgLoopBreaker(-1);
    setPairwiseRType(Kingroup.PAIRWISE_R_QG);

    setKonHegAlgNumIter(1000);

    popBuilder.loadDefaults();
    fsrLowerBound.loadDefaults();
    kinship.loadDefault();
    milligan.loadDefault();
    thompson.loadDefault();
    cervus.loadDefault();

    setLookFeel(ProjectModel.SYSTEM_LOOK);
  }
  public void copyTo(ProjectModel to) {
    super.copyTo(to);
    if (!(to instanceof KinGroupProjectModel)) {
      //TODO
    }
    Kingroup bean = (Kingroup) to;

    bean.setFsrLowerBound(getFsrLowerBound());
    bean.setFsrAlg(getFsrAlg());
    bean.setShowUserView(getShowUserView());
    bean.setSortByGroup(getSortByGroup());
    bean.setDiploidSpecie(getDiploidSpecie());
    bean.setSimpsAlgNumIter(getSimpsAlgNumIter());
    bean.setSimpsAlgLoopBreaker(getSimpsAlgLoopBreaker());
    bean.setMsAlgWindSize(getMsAlgWindSize());
    bean.setSdrKeepLargest(getSdrKeepLargest());
    bean.setNumAccuracyTrials(getNumAccuracyTrials());
    bean.setPairwiseRType(getPairwiseRType());

    bean.setFileType(getFileType());
    bean.setLastImportedFileName(getLastImportedFileName());
    bean.setLastImportedCervusParentage(getLastImportedCervusParentage());
    bean.setLastSavedFileName(getLastSavedFileName());
    bean.setLastProjectFileName(getLastProjectFileName());
    bean.setPopBuilder(getPopBuilder());
    bean.setKinship(getKinship());
    bean.setMilligan(getMilligan());
    bean.setThompson(getThompson());
    bean.setCervus(getCervus());

    bean.setKonHegAlgNumIter(getKonHegAlgNumIter()); //[0dak080409]
  }
  public PopBuilderModel getPopBuilder() {
    return popBuilder;
  }
  public void setPopBuilder(PopBuilderModel popBuilder) {
    this.popBuilder = popBuilder;
  }
  public int getFileType() {
    return fileType;
  }
  public void setFileType(int fileType) {
    this.fileType = fileType;
  }
  public String getLastImportedFileName() {
    return lastImportedFileName;
  }
  public void setLastImportedFileName(String lastImportedFileName) {
    this.lastImportedFileName = lastImportedFileName;
  }
  public KinshipFileFormat getKinshipFileFormat() {
    return kinship.getFileFormat();
  }
  public Kinship getKinship() {
    return kinship;
  }
  public void setKinship(Kinship kinship) {
    this.kinship = kinship;
  }
  public String getLastSavedFileName() {
    return lastSavedFileName;
  }
  public void setLastSavedFileName(String lastSavedFileName) {
    this.lastSavedFileName = lastSavedFileName;
  }
  public int getFsrAlg() {
    return fsrAlg;
  }
  public void setFsrAlg(int fsrAlg) {
    this.fsrAlg = fsrAlg;
  }
  public void setDiploidSpecie(boolean diploidSpecie) {
    this.diploidSpecie = diploidSpecie;
  }
  public boolean getDiploidSpecie() {
    return diploidSpecie;
  }
  public int getSimpsAlgNumIter() {
    return simpsAlgNumIter;
  }
  public void setSimpsAlgNumIter(int simpsAlgNumIter) {
    this.simpsAlgNumIter = simpsAlgNumIter;
  }
  public boolean getShowUserView() {
    return showUserView;
  }
  public void setShowUserView(boolean showUserView) {
    this.showUserView = showUserView;
  }
  public void setSortByGroup(boolean sortByGroup) {
    this.sortByGroup = sortByGroup;
  }
  public boolean getSortByGroup() {
    return sortByGroup;
  }
  public int getMsAlgWindSize() {
    return msAlgWindSize;
  }
  public void setMsAlgWindSize(int msAlgWindSize) {
    this.msAlgWindSize = msAlgWindSize;
  }

  public boolean getSdrKeepLargest()
  {
    return sdrKeepLargest;
  }

  public void setSdrKeepLargest(boolean sdrKeepLargest)
  {
    this.sdrKeepLargest = sdrKeepLargest;
  }

  public int getNumAccuracyTrials()
  {
    return numAccuracyTrials;
  }

  public void setNumAccuracyTrials(int numAccuracyTrials)
  {
    this.numAccuracyTrials = numAccuracyTrials;
  }

  public int getSimpsAlgLoopBreaker()
  {
    return simpsAlgLoopBreaker;
  }

  public void setSimpsAlgLoopBreaker(int simpsAlgLoopBreaker)
  {
    this.simpsAlgLoopBreaker = simpsAlgLoopBreaker;
  }

  public String getErrorLogFileName()
  {
    return errorLogFileName;
  }

  public void setErrorLogFileName(String errorLogFileName)
  {
    this.errorLogFileName = errorLogFileName;
  }

  public void setCervus(Cervus cervus) {
    this.cervus = cervus;
  }
  public Cervus getCervus() {
    return cervus;
  }

  public String getLastImportedCervusParentage() {
    return lastImportedCervusParentage;
  }

  public void setLastImportedCervusParentage(String lastImportedCervusParentage) {
    this.lastImportedCervusParentage = lastImportedCervusParentage;
  }

  public FsrLowerBoundBean getFsrLowerBound()
  {
    return fsrLowerBound;
  }

  public void setFsrLowerBound(FsrLowerBoundBean fsrLowerBound)
  {
    this.fsrLowerBound = fsrLowerBound;
  }

  public Milligan getMilligan()
  {
    return milligan;
  }

  public void setMilligan(Milligan milligan)
  {
    this.milligan = milligan;
  }

  public Thompson getThompson()
  {
    return thompson;
  }

  public void setThompson(Thompson thompson)
  {
    this.thompson = thompson;
  }

  public int getPairwiseRType()
  {
    return pairwiseRType;
  }

  public void setPairwiseRType(int pairwiseRType)
  {
    this.pairwiseRType = pairwiseRType;
  }

  public int getKonHegAlgNumIter()
  {
    return KonHegAlgNumIter;
  }

  public void setKonHegAlgNumIter(int konHegAlgNumIter)
  {
    KonHegAlgNumIter = konHegAlgNumIter;
  }
}
