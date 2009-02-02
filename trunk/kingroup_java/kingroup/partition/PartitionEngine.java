package kingroup.partition;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.KinshipRatioMtrxV1;
import kingroup.genetics.PartitionSearchModel;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeGroup;
import kingroup.model.HypothesisModel;
import kingroup.population.PopGroup;
import kingroup.project.KinGroupProjectV1;

import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.utilx.arrays.vec.Vec;
import java.util.*;
public class PartitionEngine {
  private PartitionModel model_;
  private boolean validState_ = false;
  private KinshipRatioMtrxV1 pr_;
  private PopGroup data_;
  private PartitionSequence[] partitions_;
  private ProgressWnd progress_ = null;
  final public PartitionSequence[] getPartitionArray() {
    return partitions_;
  }
  final public PopGroup getGenotypeData() {
    return data_;
  }
  final public void setValid(boolean b) {
    validState_ = b;
  }
  final public boolean valid() {
    return validState_;
  }
  public PartitionEngine(PopGroup data, PartitionModel model) {
    model_ = model;
    KinshipRatioMtrxV1 pr = new KinshipRatioMtrxV1(data
      , model.getHypoPrimModel(), model.getHypoNullModel());
    if (!pr.calculateAll())
      return;
    setValid(false);
    data_ = pr.getGenotypeData();
    pr_ = pr;
    setValid(true);
  }
  public PartitionModel getModel() {
    return model_;
  }
  private void cleanup() {
    partitions_ = null;
    if (progress_ != null)
      progress_.close();
    progress_ = null;
  }
  private boolean isReady() {
    return valid();
  }
  public void calculate(PartitionMethod method, boolean showProgress) {
    if (!isReady())
      return;
    progress_ = null;
    if (showProgress)
      progress_ = ProgressWnd.getInstance();
    partitions_ = new PartitionSequence[method.getNumAttempts(data_)];

    // 1. for each getGenotype
    // 2. get current in sequence
    // 3. setup unassigned pool of genotypes
    for (int row = 0; row < partitions_.length; row++) { // 1. for each getGenotype
      method.init();
      PartitionSequence part = new PartitionSequence(pr_.getPrim()
        , pr_.getNull());
      partitions_[row] = part;
      assignPartition(method, part, data_);
      if (progress_ != null
        && progress_.isCanceled(row, 0, partitions_.length)) {
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
    if (progress_ != null)
      progress_.close();
    progress_ = null;
  }
  public void calculateExhaustive(PartitionMethod method
    , boolean showProgress
    , int max_size) {
    if (!isReady())
      return;
    progress_ = null;
    if (showProgress)
      progress_ = ProgressWnd.getInstance();
    partitions_ = null;
    // NOTE!!! not all genos may be assigned after the exhaustive search
    GenotypeGroup pool = exhaustiveGroups(method, max_size, data_);
    if (pool != null && pool.size() != 0) {
      // HOW to deal with the remainder?
      for (int row = 0; row < partitions_.length; row++) { // 1. for each getGenotype
        PartitionSequence grouping = partitions_[row];
        method.init();
        assignPartition(method, grouping, pool);
        if (progress_ != null && progress_.isCanceled(row, 0, partitions_.length)) {
          cleanup();
          return;
        }
      }
    }
    renameToTheSameFirstGroup();
    PartitionSearchModel search = KinGroupProjectV1.getInstance().getKinGroupSearchModel();
    if (search.getDisplayUniqueOnly())
      removeRedundantGroups();
    if (search.getDisplaySorted())
      Arrays.sort(partitions_);
    if (progress_ != null)
      progress_.close();
    progress_ = null;
  }
  public void assignPartition(PartitionMethod method
    , PartitionSequence partition
    , GenotypeGroup dataGroup) {
    if (dataGroup.size() == 0)
      return;
    GenotypeGroup pool = dataGroup.duplicate();// 3. setup unassigned pool of genotypes
    if (partition.size() == 0) { // starting fresh
      Genotype current = method.start(pool, pr_);
      partition.add(0, current); // addSimulation the very first group idx=0
      pool.remove(current);
      partition.storeSequence(current);
    }
    while (pool.size() > 0) {  // 10. keep doing until all genotypes have been assigned
      Genotype current = method.getNextGenotype(pool, pr_);
      PartitionLikelihood[] options = new PartitionLikelihood[partition.size() + 1];
      double[] arr = new double[partition.size() + 1];
      for (int groupIdx = 0; groupIdx <= partition.size(); groupIdx++) { // NOTE: <= is used to create a new group on op==population.size()
        PartitionLikelihood tmp = partition.duplicateLikelihoodPartition();
        tmp.add(groupIdx, current); // if op is not a valid index, new group will be created
        options[groupIdx] = tmp;
        arr[groupIdx] = tmp.getLog();
      }
      // at this point options-vector contains every possibility to choose from
      int best = Vec.maxIdx(arr, arr.length);
      pool.remove(current);  // this getGenotype is now assigned
      partition.storeSequence(current);
      partition.copyLikelihoodPartitionFrom(options[best]); // remember the best partition
    }
  }
  public GenotypeGroup exhaustiveGroups(PartitionMethod method
    , int arr_size
    , GenotypeGroup dataGroup) {
    if (dataGroup.size() == 0)
      return null;
    ArrayList groupingArr = new ArrayList();
    GenotypeGroup pool = dataGroup.duplicate();// 3. setup unassigned pool of genotypes
    Genotype current = method.start(pool, pr_);
    pool.remove(current);
    PartitionSequence prt = new PartitionSequence(pr_.getPrim()
      , pr_.getNull());
    prt.add(0, current); // addSimulation the very first group
    groupingArr.add(prt);
    prt.storeSequence(current);
    int old_groups = 1;
    int progress_step = 100;
    int progress_max = 100;
    try {
      while (pool.size() > 0) {
        current = method.getNextGenotype(pool, pr_);
        pool.remove(current);
        if (progress_max <= groupingArr.size())
          progress_max = 10 * groupingArr.size();
        old_groups = groupingArr.size();
        for (int i = 0; i < old_groups; i++) { // for each existing prt
          prt = (PartitionSequence) groupingArr.get(i);
          prt.storeSequence(current);
          if (progress_ != null
            && groupingArr.size() % progress_step == 0
            && progress_.isCanceled(groupingArr.size(), 0, progress_max)) {
            cleanup();
            return null;
          }
          boolean availableSlot = true;
          for (int groupIdx = 0; groupIdx <= prt.size(); groupIdx++) { // NOTE: groupIdx++
            PartitionSequence newPrt = new PartitionSequence(prt);
            newPrt.add(groupIdx, current); // if op is not a valid index, new group will be created
            if (!newPrt.isPossible())
              continue; // donot store impossible combinations
            if (availableSlot) {
              availableSlot = false;
              groupingArr.set(i, newPrt); // reuse one that is already available
              continue;
            }
            if (arr_size > 0 && groupingArr.size() >= arr_size)
              break;
            groupingArr.add(newPrt);
          }
        }
      } // while (pool.size() > 0)
    } catch (java.lang.Throwable error) {
      // try to recover
      String mssg = "The following error occurred:\n" + error.toString()
        + "\n\nGroupingArray.size() = " + groupingArr.size();
      JOptionPane.showMessageDialog(null, mssg, "Performing Exhaustive Descent"
        , JOptionPane.ERROR_MESSAGE);
      groupingArr.clear();
      System.gc();
    }
    partitions_ = new PartitionSequence[groupingArr.size()];
    for (int r = 0; r < partitions_.length; r++)
      partitions_[r] = (PartitionSequence) groupingArr.get(r);
    return pool;
  }
  public int getGroupingCount() {
    return partitions_.length;
  }
  public PartitionSequence getPartition(int i) {
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
  //
  // PRIVATE -
  private void removeRedundantGroups() {
    if (partitions_.length < 2)
      return;
    PartitionSequence[] unique = new PartitionSequence[partitions_.length];
    unique[0] = partitions_[0]; // keep the very first
    int uniqueSize = 1;
    for (int r = 1; r < partitions_.length; r++) { // NOTE =1
      PartitionSequence grouping = partitions_[r];
      if (findWithTheSameGroupIds(grouping, unique, uniqueSize))
        continue;
      unique[uniqueSize++] = grouping;
    }
    partitions_ = new PartitionSequence[uniqueSize];
    for (int r = 0; r < partitions_.length; r++)
      partitions_[r] = unique[r];
  }
  private boolean findWithTheSameGroupIds(PartitionSequence find, PartitionSequence[] inside, int _size) {
    int size = Math.min(inside.length, _size);
    for (int r = 0; r < size; r++)
      if (find.equalGroupIds(inside[r]))
        return true;
    return false;
  }
  private void renameToTheSameFirstGroup() {
    for (int r = 0; r < partitions_.length; r++) {
      PartitionSequence partition = partitions_[r];

      // NOTE this map MUST be built every time
      Map mapOldIdToNew = new TreeMap();
      int groupCount = 1;
      for (int idx = 0; idx < data_.size(); idx++) {
        Genotype geno = data_.getGenotype(idx);
//            LOG.trace(this, "data[" + idx + "]" + geno.toString());
        GenotypeGroup group = partition.getGroup(geno);
        String oldId = group.getId();
        String newId = (String) mapOldIdToNew.get(oldId);
        if (newId != null)
          continue;  // already mapped
        newId = "[" + (groupCount++) + "]"; // place formatLog control here
        mapOldIdToNew.put(oldId, newId);
      }

      // Apply new ids
      for (int groupIdx = 0; groupIdx < partition.size(); groupIdx++) {
        GenotypeGroup group = partition.getGenotypeGroup(groupIdx);
        String oldId = group.getId();
        String newId = (String) mapOldIdToNew.get(oldId);
        if (newId != null)
          group.setId(newId);
      }
    }
  } // renameToTheSameFirstGroup
  public PopGroup orderByGroups(int i) {
    return orderByGroups(getGenotypeData(), getPartition(i));
  }
  private PopGroup orderByGroups(PopGroup from
    , PartitionSequence how) {
    PopGroup res = new PopGroup();
    res.setLocusIds(from.getLocusIds());
    Object[] sortedGroups = new Object[how.size()];
    for (int g = 0; g < how.size(); g++)
      sortedGroups[g] = how.getGenotypeGroup(g);
    Arrays.sort(sortedGroups, new Comparator() {
      public int compare(Object o1, Object o2) {
        GenotypeGroup g1 = (GenotypeGroup) o1;
        GenotypeGroup g2 = (GenotypeGroup) o2;
        if (g1.getId().length() == g2.getId().length())
          return g1.getId().compareTo(g2.getId());
        else
          return g1.getId().length() - g2.getId().length();
      }
    });
    for (int g = 0; g < sortedGroups.length; g++) {
      GenotypeGroup group = (GenotypeGroup) sortedGroups[g];
      Object[] sorted = new Object[group.size()];
      for (int i = 0; i < group.size(); i++) {
        Genotype geno = group.getGenotype(i);
        geno.setGroupId(how.getGroup(geno).getId());
        //[041214dk] removed duplicate()
//            Genotype newGroupId = geno.duplicate();
//            newGroupId.setGroupId(how.getGroup(geno).getId());
//            sorted[i] = newGroupId;
        sorted[i] = geno;
      }
      Arrays.sort(sorted, new Comparator() {
        public int compare(Object o1, Object o2) {
          Genotype g1 = (Genotype) o1;
          Genotype g2 = (Genotype) o2;
          return g1.getId().compareTo(g2.getId());
        }
      });
      for (int i = 0; i < sorted.length; i++)
        res.addGenotype((Genotype) sorted[i]);
    }
    return res;
  }
}


