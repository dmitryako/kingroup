package kingroup_v2.pop.sample.builder.loci;
import kingroup_v2.pop.sample.PopBuilderModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.JTabbedPaneX;
import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/11/2005, Time: 15:28:11
 */
public class PopBuilderLociView
//  extends GridBagView
  extends JPanel
{
  private JTabbedPaneX tabbedPane;

  private SampleBuilderLociUniformView uniformView;
  private int uniformIdx = -1;
  private JRadioButton uniformFocus;

  private SampleBuilderLociManualView manualView;
  private int manualIdx = -1;
  private JRadioButton manualFocus;

  public PopBuilderLociView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }

  private void loadFrom(PopBuilderModel bean)
  {
    uniformView = new SampleBuilderLociUniformView(bean);
    manualView = new SampleBuilderLociManualView(bean);

    uniformIdx = tabbedPane.processView(uniformView, uniformIdx, uniformFocus.isSelected()
      , "Uniform", "");
    manualIdx = tabbedPane.processView(manualView, manualIdx, manualFocus.isSelected()
      , "Manual", "");

    tabbedPane.setSelectedIndex(bean.getLociBuilderIdx());
  }
  public void loadTo(PopBuilderModel bean)
  {
    ((SampleBuilderLociCommonView)tabbedPane.getSelectedComponent()).loadTo(bean);
    bean.setLociBuilderIdx(tabbedPane.getSelectedIndex());
  }

  private void init() {
    setLayout(new BorderLayout());
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Loci");
    setBorder(titled);

    uniformFocus = new JRadioButton();
    manualFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(uniformFocus);
    group.add(manualFocus);

    tabbedPane = new JTabbedPaneX(JTabbedPane.LEFT);
  }
  private void assemble() {
//    endRow(assembleGroupSizes());
    add(assembleGroupSizes(), BorderLayout.NORTH);
  }
  private JPanel assembleGroupSizes() {
    GridBagView panel = new GridBagView();
    panel.endRow(tabbedPane);
    return panel;
  }
}

