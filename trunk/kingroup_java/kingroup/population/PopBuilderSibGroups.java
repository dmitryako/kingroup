package kingroup.population;
import kingroup.genetics.OldAlleleFreq;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/05/2005, Time: 10:33:15
 */
public class PopBuilderSibGroups extends MendelPopBuilder {
  public PopBuilderSibGroups(OldAlleleFreq freq) {
    super(freq);
  }
  public OldPop buildSibGroups(int[] groups) {
    assert (groups != null && groups.length != 0);
    OldPop res = new OldPop(getAlleleFreq());
    setUniqueId(true);
    setUniqueGroupId(true);
    SmithPopBuilderModel builderModel = new SmithPopBuilderModel();
    builderModel.setNumFullSibGroups(1);
    builderModel.setIncParents(false);
    for (int i = 0; i < groups.length; i++) {
      buildSibGroups(res, builderModel, groups[i]);
    }
    return res;
  }
}