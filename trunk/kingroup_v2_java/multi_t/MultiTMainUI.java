package multi_t;

import multi_t.project.ProjectSettingsUI;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2006, Time: 17:25:29
 */
public class MultiTMainUI  extends JPanel {
  private static MultiTMainUI instance;
  private JTabbedPaneX tabbedPane;

  private ProjectSettingsUI settingsView;
  private int settingsIdx = -1;
  private JRadioButton settingsFocus;

//  private PcrUI pcrView;
  private int pcrIdx = -1;
  private JRadioButton pcrFocus;

  private MultiTMainUI() {
    init();
  }
  public static MultiTMainUI getInstance() {
    if (instance == null) {
      instance = new MultiTMainUI();
    }
    return instance;
  }

  synchronized public void repaint()
  {
    super.repaint();
  }

  synchronized public void validate()
  {
    super.validate();
  }

  public void resetAll() {
    tabbedPane.removeAll();
    settingsView = null;      settingsIdx = -1;
    settingsView = null;      settingsIdx = -1;
  }
  private void init()
  {
    settingsFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(settingsFocus);
    settingsFocus.setSelected(true);
    tabbedPane = new JTabbedPaneX(JTabbedPane.TOP);
    LayoutManager mgr = tabbedPane.getLayout();

//    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
//    add(tabbedPane);

    setLayout(new BorderLayout());
    add(tabbedPane, BorderLayout.CENTER);
  }
  private void rebuild() {
    settingsIdx = tabbedPane.processView(settingsView, settingsIdx, settingsFocus.isSelected()
      , "Settings", "Project settings");
//    if (frame != null) {
//      frame.validate();
//      frame.repaint();
//    }
    validate();
    repaint();
  }
  public void showMessageLoadPopFirst() {
    JOptionPane.showMessageDialog(this
      , "First you need to import or generate a population sample via MENU | POPULATION | ...");
  }
//  public boolean getHasResults() {
//    return (fileView != null
//      || projectView != null
//    );
//  }

  public void setSettingsView(ProjectSettingsUI view) {
    this.settingsView = view;
    settingsFocus.setSelected(true);
    rebuild();
  }


  public ProjectSettingsUI getSettingsView() {
    return settingsView;
  }

}

