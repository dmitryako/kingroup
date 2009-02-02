package qsar.bench.descriptors.LFER;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 30/03/2007, 14:08:48
 */
public class LferAlgUI  extends JPanel {
  private JTabbedPaneX ui;

  private LferPlattsAlgView groupView;
  private int groupIdx = -1;
  private JRadioButton groupFocus;

  public LferAlgUI() {
    init();
  }
  public void reset() {
    ui.removeAll();
    groupView = null;
    groupIdx = -1;
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    groupFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(groupFocus);
    ui = new JTabbedPaneX(JTabbedPane.TOP);
    add(ui);
  }
  private void rebuild() {
    groupIdx = ui.processView(groupView, groupIdx, groupFocus.isSelected()
      , "Platts(1999)", "Group contribution approach of " + LferPlatts.REFERENCE);
    validate();
    repaint();
  }

  public void setPlattsView(LferPlattsAlgView view)
  {
    this.groupView = view;
    groupFocus.setSelected(true);
    rebuild();
  }

  public JTable getSelectedResultsTable()
  {
    int idx = ui.getSelectedIndex();
    if (idx == -1)
      return null;
    if (groupIdx == idx) {
      return groupView.getTableView();
    }
    return null;
  }
}


