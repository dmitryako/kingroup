package kingroup.papers.butler2004;
import kingroup.genetics.OldAlleleFreq;
import kingroup.papers.butler2004.family.ButlerFamily;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.MendelPopBuilder;
import kingroup.population.OldPop;
import kingroup.population.SmithPopBuilderModel;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 17, 2004, Time: 5:14:47 PM
 */
public class ButlerPopBuilder extends MendelPopBuilder {
  public ButlerPopBuilder(OldAlleleFreq freq) {
    super(freq);
  }
  public OldPop buildButler(ButlerPopBuilderModel model) {
    ButlerFamilyData data = ButlerFamilyData.getInstance();
    ButlerFamily family = data.getFamily(model.getFamilyType());
    if (family == null)
      return null;
    OldPop res = new OldPop(getAlleleFreq());
    setUniqueId(true);
    setUniqueGroupId(true);
    for (int i = 0; i < family.size(); i++) {
      buildSibGroups(res, model, family.getFamilySizeAt(i));
    }
    return res;
  }
  public OldPop buildButler(boolean includeParents, ButlerFamily family) {
    if (family == null)
      return null;
    OldPop res = new OldPop(getAlleleFreq());
    setUniqueId(true);
    setUniqueGroupId(true);
    SmithPopBuilderModel builderModel = new SmithPopBuilderModel();
    builderModel.setNumFullSibGroups(1);
    builderModel.setIncParents(includeParents);
    for (int i = 0; i < family.size(); i++) {
      buildSibGroups(res, builderModel, family.getFamilySizeAt(i));
    }
    return res;
  }
}

