package kingroup.partition;
import kingroup.genetics.KinshipRatioMtrxV1;
import kingroup.genetics.PartitionSearchModel;
import kingroup.model.HypothesisModel;
import kingroup.population.PopGroup;

import javax.swingx.ProgressWnd;
import javax.utilx.arrays.vec.Vec;
import java.util.Arrays;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 10:42:24
 */
public class PartitionEngineV2 {
  private PartitionModel model_;
  private boolean validState_ = false;
  protected KinshipRatioMtrxV1 pr_;
  private DRAlgPartitionV1[] partitions_;
  private ProgressWnd progress = null;
  final public DRAlgPartitionV1[] getPartitionArray() {
    return partitions_;
  }
  final public PopGroup getGenotypeData() {
    return pr_.getGenotypeData();
  }
  final public void setValid(boolean b) {
    validState_ = b;
  }
  final public boolean valid() {
    return validState_;
  }
  public PartitionEngineV2(PopGroup data, PartitionModel model) {
//      LOG.setTrace(true);
    model_ = model;
    KinshipRatioMtrxV1 pr = new KinshipRatioMtrxV1(data
      , model.getHypoPrimModel(), model.getHypoNullModel());
    if (!pr.calculateAll())
      return;
    setValid(false);
    pr_ = pr;
    setValid(true);
  }
  public PartitionModel getModel() {
    return model_;
  }
  private void cleanup() {
    partitions_ = null;
    if (progress != null)
      progress.close();
    progress = null;
  }
  private boolean isReady() {
    return valid();
  }
  public void calculate(PartitionMethodV2 method, boolean showProgress) {
    if (!isReady())
      return;
    progress = null;
    if (showProgress)
      progress = ProgressWnd.getInstance();
    partitions_ = new DRAlgPartitionV1[method.getNumAttempts(pr_.getGenotypeData())];

    // 1. for each getGenotype
    // 2. get current in sequence
    // 3. setup unassigned pool of genotypes
    for (int row = 0; row < partitions_.length; row++) {
      method.init(pr_);
      DRAlgPartitionV1 part = new DRAlgPartitionV1(pr_.getPrim()
        , pr_.getNull());
      partitions_[row] = assignPartition(method, part);
      if (progress != null
        && progress.isCanceled(row, 0, partitions_.length)) {
        cleanup();
        return;
      }
    }
    renameToTheSameFirstGroup();
    PartitionSearchModel searchModel = model_.getSearchModel();
    if (searchModel.getDisplayUniqueOnly())
      removeRedundantGroups();
    if (searchModel.getDisplaySorted())
      Arrays.sort(partitions_);
    if (progress != null)
      progress.close();
    progress = null;
  }
  public DRAlgPartitionV1 assignPartition(PartitionMethodV2 method
    , DRAlgPartitionV1 part) {
    int currentIdx;
    if (part.size() == 0) { // starting fresh
      currentIdx = method.nextIdx();
      part.addGenotypeToCluster(0, currentIdx);
//         LOG.trace(this, "part = ", part);
    }
    while (method.hasNext()) {
      currentIdx = method.nextIdx();
      if (currentIdx == -1) {
        int dbg = 1;
        break;
      }
      DRAlgPartitionV1[] options = new DRAlgPartitionV1[part.size() + 1];
      double[] arr = new double[part.size() + 1];
      for (int groupIdx = 0; groupIdx <= part.size(); groupIdx++) { // NOTE: <= is used to create a new group on op==population.size()
        DRAlgPartitionV1 tmp = part.duplicate();
        tmp.addGenotypeToCluster(groupIdx, currentIdx); // if op is not a valid index, new group will be created
        options[groupIdx] = tmp;
        arr[groupIdx] = tmp.getLog();
      }
      // at this point options-vector contains every possibility to choose from
      int best = Vec.maxIdx(arr, arr.length);
      part = options[best]; // remember the best part
    }
    return part;
  }
  public int getGroupingCount() {
    return partitions_.length;
  }
  public DRAlgPartitionV1 getPartition(int i) {
    if (i >= partitions_.length)
      return null;
    return partitions_[i];
  }
  public double[] getLikelihoodViews(HypothesisModel hypo) {
    double[] res = new double[getGroupingCount()];
    double base = getPartition(0).getLog();
    for (int i = 0; i < getGroupingCount(); i++)
      res[i] = hypo.logToView(getPartition(i).getLog() - base);
    return res;
  }
  private void removeRedundantGroups() {
    if (partitions_.length < 2)
      return;
  }
  private void renameToTheSameFirstGroup() {
    for (int r = 0; r < partitions_.length; r++) {
      DRAlgPartitionV1 partition = partitions_[r];
      int groupCount = 1;
      PopGroup data = pr_.getGenotypeData();
      for (int idx = 0; idx < data.size(); idx++) {
        DRAlgClusterV1 group = partition.getClusterByGenoIdxSLOW(idx);
        String oldId = group.getClusterId();
        if (oldId != null)
          continue;  // already mapped
        String newId = "[" + (groupCount++) + "]"; // place formatLog control here
        group.setClusterId(newId);
      }
    }
  }
}
