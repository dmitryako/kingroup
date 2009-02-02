package kingroup_v2.ucm.pop;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.groups.PopBuilderHalfSibView;
import kingroup_v2.pop.sample.builder.view.PopBuilderView;
import kingroup_v2.ucm.UCPopBuilderFullSibs;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/05/2006, Time: 11:26:40
 */
public class UCPopBuilderHalfSibs  extends UCPopBuilderFullSibs
{
  public PopBuilderView makeBuilderView(PopBuilderModel builder)
  {
    return new PopBuilderHalfSibView(builder);
  }
}

