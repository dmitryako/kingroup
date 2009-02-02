package kingroup_v2.cervus.view;
import javax.awtx.Colors;
import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/03/2006, Time: 09:43:42
 */
public class CervusMainUI extends JPanel {
  private JTabbedPaneX tabbedPane;

  private CervusAlleleAnalysisView alleleView;
  private int alleleIdx = -1;
  private JRadioButton alleleFocus;

  public CervusMainUI() {
    init();
  }
  public CervusAlleleAnalysisView getAlleleAnalysisView() {
    return alleleView;
  }
  public void setAlleleAnalysisView(CervusAlleleAnalysisView view) {
    this.alleleView = view;
    alleleFocus.setSelected(true);
    rebuild();
  }
  public void reset() {
    tabbedPane.removeAll();
    alleleView = null;
    alleleIdx = -1;
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    alleleFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(alleleFocus);
//    group.add(nullFocus);
//    group.add(ratioFocus);
    tabbedPane = new JTabbedPaneX(JTabbedPane.TOP);
//    tabbedPane.setBackground(Colors.TABBED_LIKELIHOODS);
    tabbedPane.setForeground(Colors.TABBED_LIKELIHOODS);
    add(tabbedPane);
  }
  private void rebuild() {
    // if primIdx == -1, it will be added as a new pane
    alleleIdx = tabbedPane.processView(alleleView, alleleIdx, alleleFocus.isSelected()
      , "Allele Analysis", "Allele Frequency Analysis");
    validate();
    repaint();
  }
}