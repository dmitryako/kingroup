package kingroup.partition;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.KinshipLikeMtrxV1;
import kingroup.genetics.Like;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeGroup;

import javax.langx.SysProp;
public class PartitionLikelihood extends PartitionCluster
  implements Comparable {
  private double total_ = 0; // LOG of total likelihood
  protected KinshipLikeMtrxV1 inside_; // pairwise likelihood for genotypes INSIDE the same group
  protected KinshipLikeMtrxV1 between_;
  public PartitionLikelihood(KinshipLikeMtrxV1 inside, KinshipLikeMtrxV1 between) {
    inside_ = inside;
    between_ = between;
  }
  public PartitionLikelihood duplicateLikelihoodPartition() {
    PartitionLikelihood res = new PartitionLikelihood(inside_, between_);
    res.duplicateLikelihoodPartitionFrom(this);
    return res;
  }
  public void duplicateLikelihoodPartitionFrom(PartitionLikelihood from) {
    duplicateGroupPartitonFrom(from); // deep copy
    inside_ = from.inside_;
    between_ = from.between_;
    total_ = from.total_;
  }
  public void copyLikelihoodPartitionFrom(PartitionLikelihood from) {
    copyGroupPartitonFrom(from); // deep copy
    inside_ = from.inside_;
    between_ = from.between_;
    total_ = from.total_;
  }
  final public double getLog() {
    return total_;
  }
  final public boolean isPossible() {
    return (total_ != Like.MIN_LOG);
  }
  final public int compareTo(Object o) {
    PartitionSequence tmp = (PartitionSequence) o;
    if (tmp.getLog() == this.getLog())
      return 0;
    if (tmp.getLog() < this.getLog())
      return -1;
    return 1;
  }
  public void add(int idx, Genotype g) {
    // if op is not a valid index, new group will be created
    GenotypeGroup group = null;
    if (idx >= size() || idx < 0) {  // create new subroup
      double val = calculateExcept(between_, null, g);
      total_ += val;
      group = new GenotypeGroup();
      add(group);  // addSimulation new subgroup
      group.setId("Group_" + size()); // label all groups by their
    } else {
      group = getGenotypeGroup(idx);
      double v = calculate(inside_, group, g); // additional likelihood inside this group
      total_ += v;
      v = calculateExcept(between_, group, g); // additional likel. to all groups except this group
      total_ += v;
    }
    group.addGenotype(g);
  }
  // return index of the group from which
  public GenotypeGroup removeGenotype(Genotype g, GenotypeGroup group) {
    if (group == null)
      return group;
    group.remove(g);
    if (group.size() == 0) {
      remove(group);  // empty group
      double val = calculateExcept(between_, null, g);
      total_ -= val;
      return group;
    }
    double v = calculate(inside_, group, g); // additional likelihood inside this group
    total_ -= v;
    v = calculateExcept(between_, group, g); // additional likel. to all groups except this group
    total_ -= v;
    return group;
  }
  private double calculateExcept(KinshipLikeMtrxV1 pairwiseL
    , GenotypeGroup data, Genotype g) {
    double v = 0;
    for (int i = 0; i < size(); i++) {
      GenotypeGroup group = getGenotypeGroup(i);
      if (data != null && group.getId().equals(data.getId()))
        continue; // all except given
      double newL = calculate(pairwiseL, group, g);
      v += newL;
    }
    return v;
  }
  private double calculate(KinshipLikeMtrxV1 pairwiseL
    , GenotypeGroup data, Genotype g) {
    double v = 0;
    for (int i = 0; i < data.size(); i++) {
      Genotype tmp = data.getGenotype(i);
      v += pairwiseL.getLog(tmp, g);
    }
    return v;
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append("TotalLog=").append(Double.toString(total_));
    res.append(SysProp.EOL);
    res.append(super.toString());
    return res.toString();
  }
}


