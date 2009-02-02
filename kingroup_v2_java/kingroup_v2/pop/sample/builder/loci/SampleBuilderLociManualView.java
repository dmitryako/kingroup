package kingroup_v2.pop.sample.builder.loci;
import kingroup_v2.pop.sample.PopBuilderModel;

import javax.swingx.text_fieldx.IntegerList;
import javax.swingx.text_fieldx.IntegerListModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/11/2005, Time: 15:31:38
 */
public class SampleBuilderLociManualView extends SampleBuilderLociCommonView
{
  private IntegerList list;
  private static final int FIELD_SIZE = 2;
  private static final int MIN_VAL = 1;
  private static final int MAX_VAL = 100;
  private static final int LIST_WIDTH = 50;
  private static final int LIST_HEIGHT = 80;

  public SampleBuilderLociManualView(PopBuilderModel bean)
  {
    init();
    loadFrom(bean);
    assemble();
  }

  private void assemble()
  {
    this.endRow(list);
  }

  private void loadFrom(PopBuilderModel bean)
  {
    list.loadFrom(bean.getLocusSizes());
    list.setIntField(bean.getNumAlleles());
  }

  private void init()
  {
    IntegerListModel model = new IntegerListModel();
    model.setFieldSize(FIELD_SIZE);
    model.setMinValue(MIN_VAL);
    model.setMaxValue(MAX_VAL);
    model.setWidth(LIST_WIDTH);
    model.setHeight(LIST_HEIGHT);
    list = new IntegerList(model);
    list.setColumnName(0, "locus");
    list.setColumnName(1, "size");
  }

  public void loadTo(PopBuilderModel bean)
  {
    bean.setLocusSizes(list.getIntArray());
    bean.setNumAlleles(list.getIntField());
  }
}
