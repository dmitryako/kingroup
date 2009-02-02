package kingroup.gui;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.MainFrame;
import kingroup.population.OldPop;
import kingroup.project.KinGroupProjectV1;

import javax.awtx.Colors;
import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;
import java.io.File;
public class MainGui extends JPanel {
  private static MainFrame frame_;
//  private KinshipMainLikeViewV1 likeView_;
  private int likeIdx_ = -1;
  private JRadioButton likeFocus_ = new JRadioButton();
  private MainPopGui popView_;
  private int popIdx_ = -1;
  private JRadioButton popFocus_ = new JRadioButton();
  private Component partitionView_;
  private int partitionIdx_ = -1;
  private JRadioButton partitonFocus_ = new JRadioButton();

//   private AccuracyGui    accuracyView_;
  private int accuracyIdx_ = -1;
  private JRadioButton accuracyFocus_ = new JRadioButton();
  private OldPop pop_ = null;
  private JTabbedPaneX tabbed_;
  public OldPop getPopulation() {
    return pop_;
  }
  private MainGui() {
    init();
  }
  public JFrame getFrame() {
    return frame_;
  }
  public void setParentFrame(MainFrame frame) {
    frame_ = frame;
  }
  private static MainGui instance_ = null;
  public static MainGui getInstance() {
    if (instance_ == null)
      instance_ = new MainGui();
    return instance_;
  }
  public void setPopulation(OldPop pop) {
    pop_ = pop;
  }
  public MainPopGui getMainPopulationGui() {
    return popView_;
  }
  public void setMainPopulationGui(MainPopGui view) {
    reset();
    popView_ = view;
    popFocus_.setSelected(true);
    rebuild();
  }
//  public KinshipMainLikeViewV1 getLikeMainView() {
//    return likeView_;
//  }
//  public void setLikeMainView(KinshipMainLikeViewV1 view) {
//    likeView_ = view;
//    likeFocus_.setSelected(true);
//    rebuild();
//  }
  public void addPartitionGui(Component view) {
    partitionView_ = view;
    partitonFocus_.setSelected(true);
    rebuild();
  }

//   public void addKinGroupAccuracyGui(AccuracyGui view) {
//      accuracyView_ = view;
//      accuracyFocus_.setSelected(true);
//      rebuild();
//   }
  private void reset() {
    tabbed_.removeAll();
//    likeView_ = null;
    likeIdx_ = -1;
    popView_ = null;
    popIdx_ = -1;
    partitionView_ = null;
    partitionIdx_ = -1;

//      accuracyView_ = null;
    accuracyIdx_ = -1;
  }
  public void rebuild() {
//    likeIdx_ = tabbed_.processView(likeView_, likeIdx_, likeFocus_.isSelected()
//      , "Likelihoods", null);
    popIdx_ = tabbed_.processView(popView_, popIdx_, popFocus_.isSelected()
      , "Population", "Loaded from a file or generated");
    partitionIdx_ = tabbed_.processView(partitionView_, partitionIdx_, partitonFocus_.isSelected()
      , "PartitionSequence", null);

//      accuracyIdx_ = tabbed_.processView(accuracyView_, accuracyIdx_, accuracyFocus_.isSelected()
//              , "Accuracy", null);
    if (frame_ != null) {
      frame_.validate();
      frame_.repaint();
    }
    validate();
    repaint();
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    ButtonGroup group = new ButtonGroup();
    group.add(popFocus_);
    group.add(likeFocus_);
    group.add(partitonFocus_);
    group.add(accuracyFocus_);
    tabbed_ = new JTabbedPaneX(JTabbedPane.TOP);
    tabbed_.setBackground(Colors.YELLOW);
    add(tabbed_);
  }
  public void updateImportedKinshipFile() {
    File recent = KinGroupProjectV1.getInstance().getLastImportedKinshipFile();
    if (recent != null && recent.exists()) {
      JMenuItem item = frame_.getRecentFileNameMenuItem();
      if (item == null)
        frame_.setupMenuBarFile();
      if (item != null)
        item.setText(recent.toString());
    }
  }
}