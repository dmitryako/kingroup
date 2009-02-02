package kingroup_v2.pop.sample.builder.groups;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.view.PopBuilderView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.JTabbedPaneX;
import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/11/2005, Time: 15:20:54
 */
public class PopBuilderFullSibView extends PopBuilderView
{
  protected JCheckBox incParents;
  protected JCheckBox shuffled;
  private JTabbedPaneX tabbedPane;

  private PopBuilderFullSibOptView uniformView;
  private int uniformIdx = -1;
  private JRadioButton uniformFocus;

  private PopBuilderGroupsManualView manualView;
  private int manualIdx = -1;
  private JRadioButton manualFocus;


  public PopBuilderFullSibView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }

  private void loadFrom(PopBuilderModel model)
  {
    incParents.setSelected(model.getIncParents());
    shuffled.setSelected(model.getShuffled());

    uniformView = new PopBuilderFullSibOptView(model);
//    skewedView = new SampleBuilderGroupsSkewedView(bean);
    manualView = new PopBuilderGroupsManualView(model);

    uniformIdx = tabbedPane.processView(uniformView, uniformIdx, uniformFocus.isSelected()
      , "Uniform", "");
//    skewedIdx = tabbedPane.processView(skewedView, skewedIdx, skewedFocus.isSelected()
//      , "Skewed", "");
    manualIdx = tabbedPane.processView(manualView, manualIdx, manualFocus.isSelected()
      , "Manual", "");

    tabbedPane.setSelectedIndex(model.getGroupsBuilderIdx());
  }
  public void loadTo(PopBuilderModel bean)
  {
    bean.setIncParents(incParents.isSelected());
    bean.setShuffled(shuffled.isSelected());
    ((PopBuilderViewI)tabbedPane.getSelectedComponent()).loadTo(bean);
    bean.setGroupsBuilderIdx(tabbedPane.getSelectedIndex());
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

    uniformFocus = new JRadioButton();
//    skewedFocus = new JRadioButton();
    manualFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(uniformFocus);
//    group.add(skewedFocus);
    group.add(manualFocus);

    tabbedPane = new JTabbedPaneX(JTabbedPane.LEFT);
  }
  private void assemble() {
    GridBagView panel = new GridBagView();
    panel.startRow(incParents);
    panel.endRow(shuffled);
    add(panel, BorderLayout.SOUTH);
    add(assembleGroupSizes(), BorderLayout.WEST);
  }
  private JPanel assembleGroupSizes() {
    GridBagView panel = new GridBagView();
    panel.endRow(tabbedPane);
    return panel;
  }
}

