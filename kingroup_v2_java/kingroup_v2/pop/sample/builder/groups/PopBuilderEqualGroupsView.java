package kingroup_v2.pop.sample.builder.groups;
import kingroup_v2.pop.sample.PopBuilderModel;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/11/2005, Time: 14:27:39
 */
public class PopBuilderEqualGroupsView
  extends GridBagView
  implements PopBuilderViewI
{
  protected IntTextField nGroups;
  protected JLabel nGroupsLbl;
  protected IntTextField groupSize;
  protected JLabel groupSizeLbl;
  protected final static int FIELD_SIZE = 2;

  public PopBuilderEqualGroupsView() {

  }
  public PopBuilderEqualGroupsView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }


  protected void init() {
    nGroups = new IntTextField(FIELD_SIZE, 1, 50);
    nGroups.setMinimumSize(nGroups.getPreferredSize());
    nGroupsLbl = new JLabel("groups");
    nGroupsLbl.setToolTipText("number of groups");
    groupSize = new IntTextField(FIELD_SIZE, 1, 50);
    groupSize.setMinimumSize(groupSize.getPreferredSize());
    groupSizeLbl = new JLabel("group size");
    groupSizeLbl.setToolTipText("individuals in each group");
  }
  private void assemble() {
    startRow(nGroups);
    endRow(nGroupsLbl);
    startRow(groupSize);
    endRow(groupSizeLbl);
  }
  public void loadFrom(PopBuilderModel model) {
    nGroups.setValue(model.getNumGroups());
    groupSize.setValue(model.getGroupSize());
  }
  public void loadTo(PopBuilderModel model) {
    model.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
    model.setNumGroups(nGroups.getInput());
    model.setGroupSize(groupSize.getInput());
  }
}
