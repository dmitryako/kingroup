package kingroup_v2.pop.sample.builder.view;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.groups.PopBuilderViewI;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/05/2006, Time: 15:27:41
 */
public abstract class PopBuilderView extends JPanel
  implements PopBuilderViewI
{
  public abstract void loadTo(PopBuilderModel bean);
}
