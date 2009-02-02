package kingroup.population.haplodip;
import kingroup.KinGroupError;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genotype.Genotype;
import kingroup.genotype.Locus;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.population.OldPop;
import kingroup.population.PopBuilderOLD;
import kingroup.population.PopGroup;
import kingroup.population.SmithPopBuilderModel;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 14:19:58
 */
public class HaplodipPopBuilder extends PopBuilderOLD
{
  private static ProjectLogger log = ProjectLogger.getLogger(HaplodipPopBuilder.class.getName());
  public HaplodipPopBuilder(OldAlleleFreq freq) {
    super(freq);
  }
  public OldPop buildSkewed(ButlerPopBuilderModel model) {
    int[] groups = buildGroupSizes(model);
    OldPop res = new OldPop(getAlleleFreq());
    setUniqueId(true);
    setUniqueGroupId(true);
    for (int i = 0; i < model.getNumFullSibGroups(); i++) {
      res = buildHaplodSibs(res, model, groups[i]);
    }
//    LOG.report(this, "res="+ res);
    return res;
  }
  public OldPop build(boolean withParents, int[] groups) {
    OldPop res = new OldPop(getAlleleFreq());
    setUniqueId(true);
    setUniqueGroupId(true);
    SmithPopBuilderModel builderModel = new SmithPopBuilderModel();
    builderModel.setNumFullSibGroups(1);
    builderModel.setIncParents(withParents);
    for (int i = 0; i < groups.length; i++) {
      builderModel.setGroupSize(groups[i]);
      buildHaplodSibs(res, builderModel);
    }
//      LOG.report(this, "groups="+ IntVec.toString(groups));
//      LOG.report(this, "res="+ res);
    return res;
  }
  public OldPop buildSkewed(boolean withParents, int[] groups, int skew) {
    int tmp = skew * (groups.length - 1);
    if (tmp % 2 != 0) {
      throw new KinGroupError("(skew * (groups.length - 1)) %2 != 0");
    }
    OldPop res = new OldPop(getAlleleFreq());
    setUniqueId(true);
    setUniqueGroupId(true);
    SmithPopBuilderModel builderModel = new SmithPopBuilderModel();
    builderModel.setNumFullSibGroups(1);
    builderModel.setIncParents(withParents);
    for (int i = 0; i < groups.length; i++) {
      groups[i] += (i * skew - tmp / 2);
      builderModel.setGroupSize(groups[i]);
//         if (halfsib) {
//            throw new KinGroupError("buildHaplodipSibsHALFSIB not ready!");
//         }
//         else
      buildHaplodSibs(res, builderModel);
    }
//      LOG.report(this, "groups="+ IntVec.toString(groups));
//      LOG.report(this, "res="+ res);
    return res;
  }
  public OldPop buildHaplodSibs(OldPop pop, SmithPopBuilderModel model, int groupSize) {
    // unrelated parents, freshly generated for each group
    Genotype hap = makeRandomGenotypeInOrder(Locus.HAPLOID);
//    LOG.trace(this, "hap=", hap);
    Genotype dip = makeRandomGenotypeInOrder(Locus.DIPLOID);
//    LOG.trace(this, "dip=", dip);
    pop = buildFullSibs(pop, groupSize, model.getIncParents(), hap, dip);
    for (int h = 0; h < model.getNumHalfSibGroups(); h++) {
      Genotype halfSibParent = makeRandomGenotypeInOrder(Locus.DIPLOID);
//      LOG.trace(this, "halfSibParent=", halfSibParent);
      pop = buildFullSibs(pop, groupSize, model.getIncParents()
        , hap, halfSibParent);
    }
    pop.setValid(pop.size() > 0, "Zero size population");
    return pop;
  }
  public OldPop buildHaplodSibs(OldPop pop, SmithPopBuilderModel model) {
    for (int g = 0; g < model.getNumFullSibGroups(); g++) { // g - a group
      String groupId = GROUP_PREFIX + makeGroupId(g + 1);

      // unrelated parents, freshly generated for each group
      Genotype hap = makeRandomGenotypeInOrder(Locus.HAPLOID);
//      log.info("hap parent = " + hap);

      Genotype dip = makeRandomGenotypeInOrder(Locus.DIPLOID);
//      log.info("dip parent = " + dip);

      if (model.getIncParents()) {
        // NOTE: each hap must be stored in a separate group
        // If you store them in the same group, you are implying that they are full-sibs!
        String upGroupId2 = PARENTAL_GROUP_PREFIX + makeGroupId(g + 1);
        PopGroup upGroup2 = new PopGroup();
        upGroup2.setId(upGroupId2);
        dip.setId("up" + makeId(2));
        dip.setGroupId(upGroupId2);
        upGroup2.addGenotype(dip);
        pop.addGroup(upGroup2);
      }
      PopGroup group = makeFullSibGroup(model.getGroupSize(), groupId, hap, dip);
      pop.addGroup(group);
    }
    pop.setValid(pop.size() > 0, "Zero size population");
    return pop;
  }
}
