package kingroup.project;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.KinPairSimModel;
import kingroup.genetics.PartitionSearchModel;
import kingroup.model.HypothesisModel;
import kingroup.model.KinshipFileModelV1;
import kingroup.model.RatioSimulationModel;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.population.CensusPopBuilderModel;
import kingroup.population.SmithPopBuilderModel;
import tsvlib.project.Project;
import tsvlib.project.ProjectModel;
public class KinGroupProjectModel extends Project {
  private KinPairSimModel kinPairSimModel = new KinPairSimModel();
  private PartitionSearchModel kinGroupSearchModel = new PartitionSearchModel();
  private KinshipFileModelV1 kinshipFormatModel = new KinshipFileModelV1();
  private HypothesisModel primModel = new HypothesisModel();
  private HypothesisModel nullModel = new HypothesisModel();
  private CensusPopBuilderModel censusBuilder = new CensusPopBuilderModel();
  private SmithPopBuilderModel popBuilder = new SmithPopBuilderModel();
  private ButlerPopBuilderModel butlerModel = new ButlerPopBuilderModel();
  private RatioSimulationModel ratioSimulationModel = new RatioSimulationModel();
  private String lastImportedKinshipFileName = "";
  private String lastSavedResultsFileName = "";
  private boolean debugTrace;
  private boolean debugError;
//   private AccuracySimModel accuracySimModel = new AccuracySimModel();
  public void loadDefault(String appName, String appVersion) {
    super.loadDefault(appName, appVersion);
    censusBuilder.loadDefaults();
    popBuilder.loadDefaults();
    ratioSimulationModel.loadDefaults();
    butlerModel.loadDefaults();
//      accuracySimModel.loadDefault();
    KinPairSimModel sm = new KinPairSimModel();
    sm.loadDefaults();
    setSimulationModel(sm);
    PartitionSearchModel search = new PartitionSearchModel();
    search.loadDefaults();
    setKinGroupSearchModel(search);
    KinshipFileModelV1 fformat = new KinshipFileModelV1();
    fformat.loadDefaults();
    setKinshipFormatModel(fformat);
    HypothesisModel hypoBean = new HypothesisModel();
    hypoBean.loadPrimDefault();
    setPrimModel(hypoBean);
    HypothesisModel hypoBean2 = new HypothesisModel();
    hypoBean2.loadNullDefault();
    setNullModel(hypoBean2);

    //setDebugTrace(true);
    //setDebugReport(true);
    setDebugTrace(false);
    setDebugError(true);
  }
  public void copyTo(ProjectModel to) {
    super.copyTo(to);
    if (!(to instanceof KinGroupProjectModel))
      return;
    KinGroupProjectModel model = (KinGroupProjectModel) to;
    model.setPrimModel(getPrimModel());
    model.setNullModel(getNullModel());
    model.setPopulationBuilder(getPopulationBuilder());
    model.setCensusBuilder(getCensusBuilder());
    model.setRatioSimulationModel(getRatioSimulationModel());
    model.setButlerPopModel(getButlerPopModel());
//      model.setAccuracySimModel(getAccuracySimModel());
    model.setLastImportedKinshipFileName(getLastImportedKinshipFileName());
    model.setLastSavedResultsFileName(getLastSavedResultsFileName());
    model.setSimulationModel(getSimulationModel());
    model.setKinGroupSearchModel(getKinGroupSearchModel());
    model.setKinshipFormatModel(getKinshipFormatModel());
    model.setDebugTrace(getDebugTrace());
    model.setDebugError(getDebugError());
  }
  public void updateFileNames(String name) {
    if (lastImportedKinshipFileName == null
      || lastImportedKinshipFileName.length() == 0)
      lastImportedKinshipFileName = new String(name);
    if (lastSavedResultsFileName == null
      || lastSavedResultsFileName.length() == 0)
      lastSavedResultsFileName = new String(name);
  }
  final public void setLastImportedKinshipFileName(String v) {
    lastImportedKinshipFileName = v;
    updateFileNames(v);
  }
  final public String getLastImportedKinshipFileName() {
    return lastImportedKinshipFileName;
  }
  final public void setLastSavedResultsFileName(String v) {
    lastSavedResultsFileName = v;
    updateFileNames(v);
  }
  final public String getLastSavedResultsFileName() {
    return lastSavedResultsFileName;
  }
  public void setSimulationModel(KinPairSimModel b) {
    kinPairSimModel = b;
  }
  final public KinPairSimModel getSimulationModel() {
    return kinPairSimModel;
  }
  public void setKinGroupSearchModel(PartitionSearchModel v) {
    kinGroupSearchModel = v;
  }
  final public PartitionSearchModel getKinGroupSearchModel() {
    return kinGroupSearchModel;
  }
  final public void setKinshipFormatModel(KinshipFileModelV1 v) {
    kinshipFormatModel = v;
  }
  final public KinshipFileModelV1 getKinshipFormatModel() {
    return kinshipFormatModel;
  }
  final public HypothesisModel getPrimModel() {
    return primModel;
  }
  public void setPrimModel(HypothesisModel b) {
    primModel = b;
  }
  final public HypothesisModel getNullModel() {
    return nullModel;
  }
  public void setNullModel(HypothesisModel b) {
    nullModel = b;
  }
  final public ButlerPopBuilderModel getButlerPopModel() {
    return butlerModel;
  }
  public void setButlerPopModel(ButlerPopBuilderModel butlerModel) {
    this.butlerModel = butlerModel;
  }
  final public void setPopulationBuilder(SmithPopBuilderModel b) {
    popBuilder = b;
  }
  final public SmithPopBuilderModel getPopulationBuilder() {
    return popBuilder;
  }
  final public void setCensusBuilder(CensusPopBuilderModel b) {
    censusBuilder = b;
  }
  final public CensusPopBuilderModel getCensusBuilder() {
    return censusBuilder;
  }
  final public void setRatioSimulationModel(RatioSimulationModel b) {
    ratioSimulationModel = b;
  }
  final public RatioSimulationModel getRatioSimulationModel() {
    return ratioSimulationModel;
  }
  final public void setDebugTrace(boolean v) {
    debugTrace = v;
  }
  final public boolean getDebugTrace() {
    return debugTrace;
  }
  final public void setDebugError(boolean v) {
    debugError = v;
  }
  final public boolean getDebugError() {
    return debugError;
  }

//   final public AccuracySimModel getAccuracySimModel() { return accuracySimModel;   }
//   public void setAccuracySimModel(AccuracySimModel accuracySimModel) {
//      this.accuracySimModel = accuracySimModel;
//   }
}
