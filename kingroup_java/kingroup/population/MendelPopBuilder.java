package kingroup.population;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
import kingroup.genetics.OldAlleleFreq;
import kingroup.genotype.Genotype;
import kingroup.genotype.Locus;
import kingroup.model.KinshipIBDModelV1;
import kingroup.model.KnownPedigreeModel;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.view.KnownPedigreeView;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;

public class MendelPopBuilder extends PopBuilderOLD
{
  private final static ProjectLogger log = ProjectLogger.getLogger(MendelPopBuilder.class.getName());

  public MendelPopBuilder(OldAlleleFreq freq) {
    super(freq);
  }
  public OldPop buildSkewed(ButlerPopBuilderModel model) {
    int[] groups = buildGroupSizes(model);
    OldPop res = new OldPop(getAlleleFreq());
    setUniqueId(true);
    setUniqueGroupId(true);
    for (int i = 0; i < model.getNumFullSibGroups(); i++) {
      res = buildSibGroups(res, model, groups[i]);
    }
//    LOG.report(this, "res="+ res);
    return res;
  }
  public OldPop buildSmith(SmithPopBuilderModel model) {
    OldPop pop = new OldPop(getAlleleFreq());
    KinshipIBDModelV1 inside = model.getPrimIdentity();
    KinshipIBDModelV1 between = model.getNullIdentity();
    KnownPedigreeView insideView = new KnownPedigreeView(inside);
    KnownPedigreeView betweenView = new KnownPedigreeView(between);
    if (insideView.getType() == KnownPedigreeModel.FULL_SIBLINGS
      && betweenView.getType() == KnownPedigreeModel.UNRELATED)
      return buildSibGroups(pop, model, model.getSize());
//    else if (insideView.getType() == KnownPedigreeModel.HALF_SIBLINGS
//        && betweenView.getType() == KnownPedigreeModel.UNRELATED)
//      return buildHalfSiblings(pop, model);
    else if (insideView.getType() == KnownPedigreeModel.UNRELATED
      && betweenView.getType() == KnownPedigreeModel.UNRELATED)
      return buildUnrelated(pop, model);
    return pop;
  }
  // unrelated parents
  public OldPop buildSibGroups(OldPop pop, SmithPopBuilderModel model, int groupSize) {
    // unrelated parents, freshly generated for each group
    Genotype parent = makeRandomGenotypeInOrder(Locus.DIPLOID);
//    log.info("parent =" + parent);
    Genotype parent2 = makeRandomGenotypeInOrder(Locus.DIPLOID);
//    log.info("parent2=" + parent2);
    pop = buildFullSibs(pop, groupSize, model.getIncParents(), parent, parent2);
    for (int h = 0; h < model.getNumHalfSibGroups(); h++) {
      Genotype halfSibParent = makeRandomGenotypeInOrder(Locus.DIPLOID);
      pop = buildFullSibs(pop, groupSize, model.getIncParents()
        , parent, halfSibParent);
    }
    pop.setValid(pop.size() > 0, "Zero size population");
    return pop;
  }

// unrelated parents
//  public OldPop buildHalfSiblings(OldPop pop, SmithPopBuilderModel model
//      , int halfsib, int halfsib2
//  ) {
//    for (int g = 0; g < model.getNumFullSibGroups(); g++) { // g - a group
//      String groupId = GROUP_PREFIX + makeGroupId(g + 1);
//      String parentsId = PARENTAL_GROUP_PREFIX + makeGroupId(g + 1);
//
//      PopGroup parents = null;
//      if (model.getIncParents()) {
//        parents = new PopGroup();
//        parents.setId(parentsId);
//      }
//
//      // unrelated parents, freshly generated for each group
//      Genotype parent = makeRandomGenotypeInOrder(Locus.DIPLOID);
//      if (parents != null) {
//        parent.setId("c" + makeId(1));
//        parent.setGroupId(parentsId);
//        parents.addLine(parent);
//      }
//
//      PopGroup group = makeHalfSibGroup(model.getGroupSize(), groupId
//          , parents, parent);
//      if (parents != null)
//        pop.addGroup(parents);
//      pop.addGroup(group);
//    }
//    pop.setValid(pop.size() > 0, "Zero size population");
//    return pop;
//  }
  public OldPop buildFullAndHalfMix(OldPop pop, SmithPopBuilderModel model) {
    for (int g = 0; g < model.getNumFullSibGroups(); g++) { // g - a group
      String groupId = GROUP_PREFIX + (g + 1);
      String parentsId = PARENTAL_GROUP_PREFIX + (g + 1);
      PopGroup parents = null;
      if (model.getIncParents()) {
        parents = new PopGroup();
        parents.setId(parentsId);
      }

      // unrelated parents, freshly generated for each group
      Genotype parent = makeRandomGenotypeInOrder(Locus.DIPLOID);
      Genotype parent2 = makeRandomGenotypeInOrder(Locus.DIPLOID);
      if (parents != null) {
        parent.setId("c" + makeId(1));
        parent.setGroupId(parentsId);
        parents.addGenotype(parent);
        parent2.setId("c" + makeId(2));
        parent2.setGroupId(parentsId);
        parents.addGenotype(parent2);
      }
      PopGroup group = makeFullSibGroup(model.getGroupSize(), groupId
        , parent, parent2);
      pop.addGroup(group);
      group = makeHalfSibGroup(model.getNumHalfSibGroups(), groupId
        , parents, parent);
      pop.addGroup(group);
      group = makeHalfSibGroup(model.getNumHalfSibGroups(), groupId
        , parents, parent2); // NOTE: parent#2; from each parent
      pop.addGroup(group);
      if (parents != null)
        pop.addGroup(parents);
    }
    pop.setValid(pop.size() > 0, "Zero size population");
    return pop;
  }
  public OldPop buildUnrelated(OldPop res, SmithPopBuilderModel model) {
    GenotypeBuilder builder = new GenotypeBuilder(getAlleleFreq());
    for (int g = 0; g < model.getNumFullSibGroups(); g++) { // g - a group
      String groupId = GROUP_PREFIX + makeGroupId(g + 1);
      PopGroup group = new PopGroup();
      group.setId(groupId);
      for (int i = 0; i < model.getGroupSize(); i++) { // i - an individual in a group
        Genotype geno = builder.makeRandomGenotypeInOrder(Locus.DIPLOID);
        geno.setId("u" + makeId(i + 1));
        geno.setGroupId(groupId);
        group.addGenotype(geno);
      }
      res.addGroup(group); // NOTE!!! this will also setLikeMainView group members to this population
    }
    res.setValid(res.size() > 0, "Zero size population");
    return res;
  }

// returns non-empty nGroups
  public int[] buildRandom(int n, int nGroups) {
    RandomSeed rand = RandomSeed.getInstance();
    int[] res = new int[nGroups];
    int used = rand.nextInt(n - nGroups + 1) + 1; // first group
    res[0] = used;
    for (int i = 1; i < nGroups - 1; i++) {
      int size = rand.nextInt(n - used - nGroups + i + 1) + 1;
      used += size;
      res[i] = size;
    }
    res[nGroups - 1] = n - used;
    return res;
  }
  public OldPop buildRandom(boolean withParents, int n, int nGroups) {
    int[] groups = buildRandom(n, nGroups);
    LOG.report(this, "groups=" + IntVec.toString(groups));
    return buildRandom(withParents, groups);
  }
  public OldPop buildRandom(boolean withParents, int[] groups) {
    OldPop res = new OldPop(getAlleleFreq());
    setUniqueId(true);
    setUniqueGroupId(true);
    SmithPopBuilderModel builderModel = new SmithPopBuilderModel();
    builderModel.setNumFullSibGroups(1);
    builderModel.setIncParents(withParents);
    for (int i = 0; i < groups.length; i++) {
      buildSibGroups(res, builderModel, groups[i]);
    }
    return res;
  }
}
