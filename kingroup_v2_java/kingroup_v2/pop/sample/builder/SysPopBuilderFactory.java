package kingroup_v2.pop.sample.builder;
import kingroup_v2.pop.sample.PopBuilderModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/11/2005, Time: 07:40:57
 */
public class SysPopBuilderFactory
{
  public static PopBuilder makeBuilder(PopBuilderModel model)
  {
    switch (model.getBuilderType()) {
      case PopBuilderModel.KINSHIP_IBD_BUILDER:
        return new SysPopBuilderKinshipPairs();
      case PopBuilderModel.HALF_SIB_BUILDER:
        return new SysPopBuilderHalfSib();
      case PopBuilderModel.PEDIGREE_BUILDER:
        return new SysPopBuilderPedigree();
      case PopBuilderModel.SINGLE_FULL_SIB_FAMILY:
        return new SysPopBuilderSignleFSFamily();
      case PopBuilderModel.SINGLE_FULL_SIB_FAMILY_BY_SIZE:
        return new SysPopBuilderSignleFSFamily_bySize();
      case PopBuilderModel.PEDIGREE_TRIANG_BUILDER:
        return new SysPopBuilderPedigreeTriang();
      default :
    }
    int idx = model.getGroupsBuilderIdx();
    if (idx == PopBuilderModel.MANUAL_GROUPS)
      return new SysPopBuilderFullSibManual();
    return new SysPopBuilderFullSibEqual();
  }
}
