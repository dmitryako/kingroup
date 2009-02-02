package kingroup_v2.kinship.view;
import kingroup_v2.kinship.like.view.KinshipRatioView;

import javax.awtx.Colors;
import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/09/2005, Time: 10:12:28
 */
public class KinshipMainUI extends JPanel {
  private JTabbedPaneX tabbedPane;
  private KinshipLikeView primView;
  private int primIdx = -1;
  private JRadioButton primFocus;
  private KinshipLikeView nullView;
  private int nullIdx = -1;
  private JRadioButton nullFocus;
  private KinshipRatioView ratioView;
  private int ratioIdx = -1;
  private JRadioButton ratioFocus;
  public KinshipMainUI() {
    init();
  }
  public KinshipLikeView getKinshipPrimView() {
    return primView;
  }
  public KinshipLikeView getKinshipNullView() {
    return nullView;
  }
  public KinshipRatioView getKinshipRatioView() {
    return ratioView;
  }
  public void setKinshipPrimView(KinshipLikeView view) {
    this.primView = view;
    primFocus.setSelected(true);
    rebuild();
  }
  public void setKinshipNullView(KinshipLikeView view) {
    this.nullView = view;
    nullFocus.setSelected(true);
    rebuild();
  }
  public void setKinshipRatioView(KinshipRatioView view) {
    this.ratioView = view;
    ratioFocus.setSelected(true);
    rebuild();
  }
  public void reset() {
    tabbedPane.removeAll();
    primView = null;
    primIdx = -1;
    nullView = null;
    nullIdx = -1;
    ratioView = null;
    ratioIdx = -1;
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    primFocus = new JRadioButton();
    nullFocus = new JRadioButton();
    ratioFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(primFocus);
    group.add(nullFocus);
    group.add(ratioFocus);
    tabbedPane = new JTabbedPaneX(JTabbedPane.TOP);
//    tabbedPane.setBackground(Colors.TABBED_LIKELIHOODS);
    tabbedPane.setForeground(Colors.TABBED_LIKELIHOODS);
    add(tabbedPane);
  }
  private void rebuild() {
    // if primIdx == -1, it will be added as a new pane
    primIdx = tabbedPane.processView(primView, primIdx, primFocus.isSelected()
      , "Primary", "Primary hypothesis");
    nullIdx = tabbedPane.processView(nullView, nullIdx, nullFocus.isSelected()
      , "Null", "Null hypothesis");
    ratioIdx = tabbedPane.processView(ratioView, ratioIdx, ratioFocus.isSelected()
      , "Ratio", "Primary/Null like");
    validate();
    repaint();
  }
}
