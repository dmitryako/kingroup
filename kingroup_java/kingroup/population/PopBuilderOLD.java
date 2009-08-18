package kingroup.population;
import kingroup.KinGroupError;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genotype.Genotype;
import kingroup.genotype.Locus;
import kingroup.model.KinshipIBDModelV1;
import kingroup.papers.butler2004.ButlerPopBuilderModel;

import javax.utilx.arrays.IntVec;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Apr 29, 2004, Time: 3:57:33 PM
 */
public class PopBuilderOLD extends GenotypeBuilder
{
  // TODO: Is there any value in making this more generic, e.g. haploilds, haplodiploids, and diploids?
  public static String GROUP_PREFIX = "g";
  public static String PARENTAL_GROUP_PREFIX = "pg";
  private static String POPULATION_SEED = "population_seed";
  private static String GROUP_SEED = "group_seed";
  public PopBuilderOLD(OldAlleleFreq freq) {
    super(freq);
  }
  public int[] buildUniform(int n, int nGroups) {
    return IntVec.make(nGroups, n / nGroups);
  }
  public int[] buildGroupSizes(ButlerPopBuilderModel model) {
    int r = model.getNumFullSibGroups() + model.getNumHalfSibGroups();
    int n = model.getSize();
    int[] groups = buildUniform(n, r);
    int skew = model.getSkew();
    int tmp = skew * (groups.length - 1);
    if (tmp % 2 != 0) {
      throw new KinGroupError("(skew * (groups.length - 1)) %2 != 0");
    }
    for (int i = 0; i < groups.length; i++) {
      groups[i] += (i * skew - tmp / 2);
    }
//    LOG.report(this, "groups="+ IntVec.toString(groups));
    return groups;
  }
  public OldPop buildSkewed(ButlerPopBuilderModel model) {
    return null;
  }
  public OldPop build(SmithPopBuilderModel model) {
    OldPop pop = new OldPop(getAlleleFreq());
    KinshipIBDModelV1 insideGroup = model.getPrimIdentity();
    KinshipIBDModelV1 betweenGroups = model.getNullIdentity();
    Genotype popSeed = makeRandomGenotypeInOrder(Locus.DIPLOID); // generate diploid population seed
    popSeed.setId(POPULATION_SEED);
    for (int g = 0; g < model.getNumFullSibGroups(); g++) { // g - a group
      String groupId = GROUP_PREFIX + makeGroupId(g + 1);
      PopGroup group = new PopGroup();
      group.setId(groupId);
      Genotype groupSeed = popSeed;
      if (g != 0) {
        groupSeed = getRandomGenotypeFrom(groupSeed
          , betweenGroups.getRm()
          , betweenGroups.getRp(), true, Locus.DIPLOID);
        groupSeed.setId(GROUP_SEED);
      }
      for (int i = 0; i < model.getGroupSize(); i++) { // i - an individual in a group
        Genotype geno = groupSeed;
        if (i != 0) {
          geno = getRandomGenotypeFrom(groupSeed
            , insideGroup.getRm()
            , insideGroup.getRp(), true, Locus.DIPLOID); // 2-generate diploid
          geno.setId("i" + makeId(i + 1));
        }
        geno.setGroupId(groupId);
        group.addGenotype(geno);
      }
      pop.addGroup(group); // NOTE!!! this will also setLikeMainView group members to this population
    }
    pop.setValid(pop.size() > 0, "Zero size population");
    return pop;
  }
  public OldPop buildFullSibs(OldPop pop, int groupSize, boolean incParents
    , Genotype parent, Genotype parent2) {
    String groupId = GROUP_PREFIX + makeGroupId();
    if (incParents) {
      // NOTE: each parent must be stored in a separate group
      // If you store them in the same group, you are implying that they are full-sibs!
      String upGroupId = PARENTAL_GROUP_PREFIX + makeGroupId(); // unrelated parent group getId
      String upGroupId2 = PARENTAL_GROUP_PREFIX + makeGroupId();
      PopGroup upGroup = new PopGroup();
      upGroup.setId(upGroupId);
      PopGroup upGroup2 = new PopGroup();
      upGroup2.setId(upGroupId2);
      parent.setId("up" + makeId(1));
      parent.setGroupId(upGroupId);
      upGroup.addGenotype(parent);
      pop.addGroup(upGroup);
      parent2.setId("up" + makeId(2));
      parent2.setGroupId(upGroupId2);
      upGroup2.addGenotype(parent2);
      pop.addGroup(upGroup2);
    }
    PopGroup group = makeFullSibGroup(groupSize, groupId, parent, parent2);
    pop.addGroup(group);
    pop.setValid(pop.size() > 0, "Zero size population");
    return pop;
  }
}

