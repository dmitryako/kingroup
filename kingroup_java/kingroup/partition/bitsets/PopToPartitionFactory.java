package kingroup.partition.bitsets;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeFactory;
import kingroup.population.OldPop;
import kingroup.population.PopGroup;

import javax.iox.LOG;
import javax.utilx.bitset.CompBitSet;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 15, 2004, Time: 8:27:50 AM
 */
public class PopToPartitionFactory extends Genotype {
  private final OldPop pop_;
  static private PopToPartitionFactory instance_;
  static public PopToPartitionFactory getInstance() {
    return instance_;
  }
  static public PopToPartitionFactory makeInstance(OldPop pop) {
    instance_ = new PopToPartitionFactory(pop);
    return instance_;
  }
  private PopToPartitionFactory(OldPop pop) {
    super();
    pop_ = pop;
    loadKeyToIdxMap(pop); // Genotypes are identified by their unique keys.
  }
  private void loadKeyToIdxMap(OldPop pop) {
    // Using unique keys as index into the bitset array.
    // The other way, we would need a map (e.g. keyToIdxMap)
    GenotypeFactory.init();
    for (int i = 0; i < pop.size(); i++) {
      GenotypeFactory.getInstance().setUniqueKey(pop.getGenotype(i), i);
    }
  }
  public Partition makeFromPopulation(OldPop pop) {
    if (pop.size() != pop_.size()) {
      LOG.throwError(this, "Incompatible populations", "");
    }
    Partition res = new Partition(pop.size());
    for (int groupIdx = 0; groupIdx < pop.getGroupCount(); groupIdx++) {
      PopGroup group = pop.getGroupByIdx(groupIdx);
      CompBitSet set = new CompBitSet();
      for (int i = 0; i < group.size(); i++) {
        Genotype geno = group.getGenotype(i);
        set.set(geno.getUniqueKey());
      }
      res.add(set);
    }
    return res;
  }
}
