package kingroup_v2.ucm.pop;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.PopBuilderKinshipIBDView;
import kingroup_v2.pop.sample.builder.view.PopBuilderView;
import kingroup_v2.ucm.UCPopBuilderFullSibs;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/05/2006, Time: 16:05:08
 */
public class UCPopBuilderKinshipIBD extends UCPopBuilderFullSibs
{
  public PopBuilderView makeBuilderView(PopBuilderModel builder)
  {
    return new PopBuilderKinshipIBDView(builder);
  }
}

