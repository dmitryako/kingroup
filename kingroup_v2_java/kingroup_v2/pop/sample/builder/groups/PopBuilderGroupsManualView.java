package kingroup_v2.pop.sample.builder.groups;
import kingroup_v2.pop.sample.PopBuilderModel;

import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntegerList;
import javax.swingx.text_fieldx.IntegerListModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/11/2005, Time: 14:28:09
 */
public class PopBuilderGroupsManualView
  extends GridBagView
  implements PopBuilderViewI
{
  private IntegerList list;
  private static final int FIELD_SIZE = 2;
  private static final int MIN_VAL = 1;
  private static final int MAX_VAL = 100;
  private static final int LIST_WIDTH = 50;
  private static final int LIST_HEIGHT = 50;

  public PopBuilderGroupsManualView(PopBuilderModel bean)
  {
    init();
    loadFrom(bean);
    assemble();
  }

  private void assemble()
  {
    this.endRow(list);
  }

  public void loadFrom(PopBuilderModel bean)
  {
    list.loadFrom(bean.getGroupSizes());
    list.setIntField(bean.getGroupSize());
  }

  protected void init()
  {
    IntegerListModel model = new IntegerListModel();
    model.setFieldSize(FIELD_SIZE);
    model.setMinValue(MIN_VAL);
    model.setMaxValue(MAX_VAL);
    model.setWidth(LIST_WIDTH);
    model.setHeight(LIST_HEIGHT);
    list = new IntegerList(model);
    list.setColumnName(0, "group");
    list.setColumnName(1, "size");
  }

  public void loadTo(PopBuilderModel bean)
  {
    bean.setGroupSizes(list.getIntArray());
    bean.setGroupSize(list.getIntField());
  }
}
