package tsvlib.project;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 26, 2004, Time: 10:53:38 AM
 */
public class ProjectFrame extends JFrame {
  static private ProjectFrame instance = null;
  private final int MIN_WIDTH = 640;
  private final int MIN_HEIGHT = 480;
  static public ProjectFrame getInstance() {
    return instance;
  }
  static public void setInstance(ProjectFrame v) {
    instance = v;
  }
  protected ProjectFrame(Project model) {
    init();
    setTitle(model.getAppName() + " " + model.getAppVersion());
    setJMenuBar(new JMenuBar());
  }
  public void showSmallScreen() {
    pack();
    setSize(new Dimension(300, 300));
    setVisible(true);
  }
  protected JMenu addMenuFile() {
    JMenu menu = new JMenu("File");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_F);
    return menu;
  }
  protected JMenu addMenuHelp() {
    JMenu menu = new JMenu("Help");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_H);
    return menu;
  }
  protected JMenu addMenuSettings() {
    JMenu menu = new JMenu("Settings");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_S);
    return menu;
  }
  protected JMenu addOpenMenu(JMenu to) {
    JMenu menu = new JMenu("Open");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_O);
    return menu;
  }
  protected JMenu addImportMenu(JMenu to) {
    JMenu menu = new JMenu("Import");
//    menu.setToolTipText("Import data set/table");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_I);
    return menu;
  }
  protected JMenu addCalcMenu(JMenu to) {
    JMenu menu = new JMenu("Calculate");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_C);
    return menu;
  }
  protected JMenu addMenuTools() {
    JMenu menu = new JMenu("Admin");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_T);
    return menu;
  }
  private void init() {
    // http://java.sun.com/docs/books/tutorial/uiswing/examples/misc/InputVerificationDemoProject/src/misc/InputVerificationDemo.java
    // Turn off metal's use of bold fonts
    UIManager.put("swing.boldMetal", Boolean.FALSE);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponentListener(new ComponentListener() {
      public void componentResized(ComponentEvent e) {
        int width = getWidth();
        int height = getHeight();
        boolean resize = false;
        if (width < MIN_WIDTH) {
          resize = true;
          width = MIN_WIDTH;
        }
        if (height < MIN_HEIGHT) {
          resize = true;
          height = MIN_HEIGHT;
        }
        if (resize) {
          setSize(width, height);
        }
      }
      public void componentMoved(ComponentEvent e) {
      }
      public void componentShown(ComponentEvent e) {
      }
      public void componentHidden(ComponentEvent e) {
      }
    });
  }
  protected void addMenuLookFeel(JMenu menu, Project model) {
    JRadioButtonMenuItem defaultLook = new JRadioButtonMenuItem("System default");
    JRadioButtonMenuItem crossLook = new JRadioButtonMenuItem("Cross platform");
    defaultLook.addActionListener(new AdapterUCCToALThread(
      new UCSelectLookFeel(this, model, model.SYSTEM_LOOK)));
    crossLook.addActionListener(new AdapterUCCToALThread(
      new UCSelectLookFeel(this, model, model.CROSS_PLATFORM_LOOK)));
    defaultLook.setMnemonic(KeyEvent.VK_S);
    JMenu look = new JMenu("Look and Feel");
    look.add(defaultLook);
    ButtonGroup lookGroup = new ButtonGroup();
    lookGroup.add(defaultLook);
    crossLook.setMnemonic(KeyEvent.VK_C);
    look.add(crossLook);
    lookGroup.add(crossLook);

//    windows.setMnemonic(KeyEvent.VK_W);
//    look.addLine(windows);
//    lookGroup.addLine(windows);

//      motif.setMnemonic(KeyEvent.VK_T);
//      look.addLine(motif);
//      lookGroup.addLine(motif);

//    metal.setMnemonic(KeyEvent.VK_M);
//    look.addLine(metal);
//    lookGroup.addLine(metal);
    look.setMnemonic(KeyEvent.VK_L);
    menu.add(look);

    if (model.getLookFeel().equals(ProjectModel.CROSS_PLATFORM_LOOK)) {
      crossLook.setSelected(true);
      crossLook.doClick();
    } else {
      defaultLook.setSelected(true);
      defaultLook.doClick();
    }
  }
  protected JMenuItem addMenuAbout(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("About");
    menuItem.setMnemonic(KeyEvent.VK_A);
    menu.add(menuItem);
    return menuItem;
  }
  public static ImageIcon loadSaveFileIcon() {
    return loadImageIcon("images/save.gif"); // "images/save.gif" <- correct formatLog; DO NOT use File.separator
  }
  public static ImageIcon loadOpenFileIcon() {
    return loadImageIcon("images/open.gif");
  }
  public static ImageIcon loadOpenHelpIcon() {
    return loadImageIcon("images/help.gif");
  }
  public static ImageIcon loadImageIcon(String fileName) {
    ImageIcon icon = null;
    File file = new File(fileName);
    if (file.exists())
      icon = new ImageIcon(fileName);
    else {
      // try loading from a jar file
      URL url = ProjectFrame.class.getResource(fileName);
      Image img = null;
      if (url != null)
        img = Toolkit.getDefaultToolkit().getImage(url);
      if (img != null)
        icon = new ImageIcon(img);
    }
    return icon;
  }


  public static JButton makeLocateFileButton() {
    ImageIcon icon = loadOpenFileIcon();
    JButton bttn;
    if (icon != null)
      bttn = new JButton(icon);
    else
      bttn = new JButton("...");
    bttn = makeToolButton(bttn);
    bttn.setToolTipText("Locate file ...");
    bttn.setPreferredSize(new Dimension(25, 20));
    return bttn;
  }
  public static JButton makeHelpButton() {
    ImageIcon icon = loadOpenHelpIcon();
    JButton bttn;
    if (icon != null) {
      bttn = new JButton(icon);
    }
    else
      bttn = new JButton("help");
    bttn = makeToolButton(bttn);
    bttn.setPreferredSize(new Dimension(25, 15));
    return bttn;
  }
  public static JButton makeToolButton(JButton button) {
    button.setBorderPainted(false);
    button.setFocusPainted(false);
//    button.setRolloverEnabled(true);
    button.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.isEnabled())
          button.setBorderPainted(true);
      }
      public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setBorderPainted(false);
      }
    });
    return button;
  }
  protected void addMenuViewErrors(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Error log");
    menuItem.setToolTipText("View error log");

//    menuItem.addActionListener(new AdapterUCCToALThread(
//      new UCViewOutputStream(ProjectLogger.getInstance().getLineStream())));
//    menuItem.setMnemonic(KeyEvent.VK_E);
    menu.add(menuItem);
  }
}