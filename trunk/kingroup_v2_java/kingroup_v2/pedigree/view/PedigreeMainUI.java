package kingroup_v2.pedigree.view;
import kingroup_v2.pedigree.ratio.view.PedigreeRatioView;
import kingroup_v2.like.milligan.Milligan;
import kingroup_v2.like.milligan.view.MilliganRatioView;
import kingroup_v2.like.thompson.Thompson;
import kingroup_v2.like.thompson.view.ThompsonRatioView;
import kingroup_v2.kinship.Kinship;

import javax.awtx.Colors;
import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 14:30:26
 */
public class PedigreeMainUI  extends JPanel {
  private JTabbedPaneX tabbedPane;

  private PedigreeRatioView kinView;
  private int kinIdx = -1;
  private JRadioButton kinFocus;

  private MilliganRatioView sView;
  private int sIdx = -1;
  private JRadioButton sFocus;

  private ThompsonRatioView kView;
  private int kIdx = -1;
  private JRadioButton kFocus;

  public PedigreeMainUI() {
    init();
  }
  public PedigreeRatioView getPedigreeRatioView() {
    return kinView;
  }
  public MilliganRatioView getMilliganRatioView() {
    return sView;
  }
  public ThompsonRatioView getThompsonRatioView() {
    return kView;
  }
  public void setPedigreeRatioView(PedigreeRatioView view) {
    this.kinView = view;
    kinFocus.setSelected(true);
    rebuild();
  }
  public void setMilliganRatioView(MilliganRatioView view) {
    this.sView = view;
    sFocus.setSelected(true);
    rebuild();
  }
  public void setThompsonRatioView(ThompsonRatioView view) {
    this.kView = view;
    kFocus.setSelected(true);
    rebuild();
  }
  public void reset() {
    tabbedPane.removeAll();
    kinView = null;
    kinIdx = -1;
    sView = null;
    sIdx = -1;
    kView = null;
    kIdx = -1;
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    kinFocus = new JRadioButton();
    sFocus = new JRadioButton();
    kFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(kinFocus);
    group.add(sFocus);
    group.add(kFocus);

    tabbedPane = new JTabbedPaneX(JTabbedPane.TOP);
//    tabbedPane.setBackground(Colors.TABBED_LIKELIHOODS);
    tabbedPane.setForeground(Colors.TABBED_LIKELIHOODS);
    add(tabbedPane);
  }
  private void rebuild() {
    kinIdx = tabbedPane.processView(kinView, kinIdx, kinFocus.isSelected()
      , "(Rm,Rp)-coefficients", "Likelihoods are from " + Kinship.REFERENCE);
    kIdx = tabbedPane.processView(kView, kIdx, kFocus.isSelected()
      , "k-coefficients", "Likelihoods are from " + Thompson.REFERENCE);
    sIdx = tabbedPane.processView(sView, sIdx, sFocus.isSelected()
      , "s-coefficients", "Likelihoods are from " + Milligan.REFERENCE);
    validate();
    repaint();
  }
}
