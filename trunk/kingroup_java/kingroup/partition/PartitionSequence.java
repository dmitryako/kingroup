package kingroup.partition;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.KinshipLikeMtrxV1;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeGroup;

import javax.langx.SysProp;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
final public class PartitionSequence extends PartitionLikelihood {
  private Vector seq_ = new Vector();
  private Map mapGenotypeToGroup_ = new HashMap();
  public PartitionSequence(KinshipLikeMtrxV1 inside, KinshipLikeMtrxV1 between) {
    super(inside, between);
  }
  public PartitionSequence(PartitionSequence from) {
    super(from.inside_, from.between_);
    duplicateLikelihoodPartitionFrom(from);
    seq_ = new Vector(from.seq_); // deep copy
  }
  // seq_
  public void storeSequence(Genotype key) {
    seq_.add(key);
  }
  public Genotype getSequence(int i) {
    if (seq_.size() > i)
      return (Genotype) seq_.get(i);
    return null;
  }
  public boolean equalGroupIds(PartitionSequence comp) {
    Object[] genos = mapGenotypeToGroup_.keySet().toArray();
    for (int genoIdx = 0; genoIdx < genos.length; genoIdx++) {
      Genotype geno = (Genotype) genos[genoIdx];
      if (!getGroup(geno).equals(comp.getGroup(geno)))
        return false;
    }
    return true;
  }
  public GenotypeGroup getGroup(Genotype gen) {
    GenotypeGroup group = (GenotypeGroup) mapGenotypeToGroup_.get(gen);
    if (group != null)
      return group;
    loadGenotypeToGroupMap();
    return (GenotypeGroup) mapGenotypeToGroup_.get(gen);
  }
  public GenotypeGroup removeGenotype(Genotype g) {
    GenotypeGroup group = getGroup(g);
    if (group == null)
      return null;
    mapGenotypeToGroup_.remove(g);
    return removeGenotype(g, group);
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append("Sequence=");
    for (int s = 0; s < seq_.size(); s++) {
      buff.append(getSequence(s).getId());
      if (s != seq_.size() - 1)
        buff.append(", ");
    }
    buff.append(SysProp.EOL);
    buff.append(super.toString());
    return buff.toString();
  }
  private void loadGenotypeToGroupMap() {
    for (int ig = 0; ig < size(); ig++) {
      GenotypeGroup group = getGenotypeGroup(ig);
      for (int genoIdx = 0; genoIdx < group.size(); genoIdx++) {
        Genotype geno = group.getGenotype(genoIdx);
//            LOG.trace(this, "mapGenotypeToGroup_[" + geno.toString()
//                    + "]=\n" + group.toString());
        mapGenotypeToGroup_.put(geno, group);
        if (group == null) {
          int dbg = 1;
        }
      }
    }
  }
}
