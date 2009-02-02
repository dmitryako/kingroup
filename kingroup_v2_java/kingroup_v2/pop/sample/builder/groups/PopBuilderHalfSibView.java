package kingroup_v2.pop.sample.builder.groups;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.view.PopBuilderView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/05/2006, Time: 11:28:20
 */
public class PopBuilderHalfSibView extends PopBuilderView
{
  protected JCheckBox incParents;
  protected JCheckBox shuffled;
  private PopBuilderHalfSibOptView optView;

  public PopBuilderHalfSibView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }

  private void loadFrom(PopBuilderModel bean)
  {
    incParents.setSelected(bean.getIncParents());
    shuffled.setSelected(bean.getShuffled());
    optView = new PopBuilderHalfSibOptView(bean);
  }
  public void loadTo(PopBuilderModel model)
  {
    model.setIncParents(incParents.isSelected());
    model.setShuffled(shuffled.isSelected());
    optView.loadTo(model);
  }

  private void init() {
    setLayout(new BorderLayout());
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Groups");
    setBorder(titled);

    incParents = new JCheckBox("parents");
    incParents.setToolTipText("include parents");
    shuffled = new JCheckBox("shuffle");
    shuffled.setToolTipText("randomly reshuffle individuals");
  }
  private void assemble() {
    GridBagView panel = new GridBagView();
    panel.endRow(optView);
    panel.startRow(incParents);
    panel.endRow(shuffled);
    add(panel, BorderLayout.SOUTH);
  }
}
