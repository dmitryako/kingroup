package kingroup_v2.pop.sample.builder.groups;
import kingroup_v2.pop.sample.PopBuilderModel;

import javax.swing.*;
import javax.swingx.text_fieldx.IntTextField;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/11/2005, Time: 14:27:54
 */
public class SampleBuilderGroupsSkewedView   extends PopBuilderEqualGroupsView
{
  protected IntTextField skew;
  protected JLabel skewLbl;
  public SampleBuilderGroupsSkewedView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }

  protected void init() {
    super.init();
    groupSizeLbl = new JLabel("g_1");
    groupSizeLbl.setToolTipText("individuals in the first group");

    skew = new IntTextField(FIELD_SIZE, 1, 100);
    skew.setMinimumSize(skew.getPreferredSize());

    skewLbl = new JLabel("q");
    skewLbl.setToolTipText("skewing factor: k-group size is g_k = g_1 + q * (k-1)");
  }
  private void assemble() {
    startRow(nGroups);
    endRow(nGroupsLbl);
    startRow(groupSize);
    endRow(groupSizeLbl);
    startRow(skew);
    endRow(skewLbl);
  }
  public void loadFrom(PopBuilderModel bean) {
    super.loadFrom(bean);
    skew.setValue(bean.getGroupSizeSkew());
  }
  public void loadTo(PopBuilderModel bean) {
    super.loadTo(bean);
    bean.setGroupSizeSkew(skew.getInput());
  }
}