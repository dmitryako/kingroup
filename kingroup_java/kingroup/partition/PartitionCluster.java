package kingroup.partition;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genotype.GenotypeGroup;
import kingroup.genotype.GenotypeGroupArrays;

import javax.langx.SysProp;
public class PartitionCluster {
  private GenotypeGroup[] v_ = null;
  // PUBLIC+
  public PartitionCluster() {
    v_ = new GenotypeGroup[0];
  }
  public PartitionCluster(int _size) {
    v_ = new GenotypeGroup[_size];
  }
  public PartitionCluster(PartitionCluster from) {
    v_ = from.v_;
  }
  // HIGHLY-USED
  final public GenotypeGroup getGenotypeGroup(int i) {
    return v_[i];
  }
  final public void add(GenotypeGroup a) {
    v_ = GenotypeGroupArrays.add(a, v_);
  }
  final public void remove(GenotypeGroup a) {
    v_ = GenotypeGroupArrays.remove(a, v_);
  }
  final public int size() {
    return v_.length;
  }
  final public void set(int i, GenotypeGroup a) {
    v_[i] = a;
  }
  public PartitionCluster duplicateGroupPartition() {
    PartitionCluster res = new PartitionCluster();
    res.duplicateGroupPartitonFrom(this);
    return res;
  }
  public void duplicateGroupPartitonFrom(PartitionCluster from) {
    v_ = new GenotypeGroup[from.size()];
    for (int i = 0; i < size(); i++)
      v_[i] = from.v_[i].duplicate();
  }
  public void copyGroupPartitonFrom(PartitionCluster from) {
    v_ = from.v_;
  }
  public String toString() {
    if (size() == 0)
      return "empty";
    StringBuffer res = new StringBuffer();
    for (int groupIdx = 0; groupIdx < size(); groupIdx++) {
      GenotypeGroup group = getGenotypeGroup(groupIdx);
      res.append("group ");
      res.append(group.getId());
      res.append("= ");
      for (int genoIdx = 0; genoIdx < group.size(); genoIdx++) {
        res.append(group.getGenotype(genoIdx).getId());
        if (genoIdx != group.size() - 1)
          res.append(", ");
      }
      res.append(SysProp.EOL);
    }
    return res.toString();
  }
}