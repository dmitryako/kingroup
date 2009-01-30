package tomsk;
import pattern.ucm.AdapterUCCToAL;
import pattern.ucm.AdapterUCCToALThread;
import tomsk.project.Tomsk;
import tomsk.ucm.DisplayHelpAboutTomskHnd;
import tomsk.ucm.world.BuildWorldHnd;
import tomsk.ucm.world.UCOpenPDBFile;
import tomsk.ucm.world.tests.*;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 26, 2004, Time: 10:56:57 AM
 */
public class TomskFrame extends ProjectFrame {
  public TomskFrame(Tomsk model) {
    super(model);
    setInstance(this);
    init();

//    setupToolBar();               // TOOLBAR

    JMenu menu = addMenuFile();   // MENU | FILE
    JMenu menu2 = addOpenMenu(menu);
    addMenuOpenPDBFile(menu2);
    setupMenuFile(menu);


    // Menu | World
    JMenu world = addMenuWorld();
    JMenuItem realityBuild = addMenuBuild(world);
    realityBuild.addActionListener(new BuildWorldHnd());

    // Menu | World | Test
    JMenu test = addMenuTest(world);
    JMenuItem atoms = addMenuTestAtoms(test);  // ATOMS
    atoms.addActionListener(new TestAtomsHnd());
    JMenuItem spheres = addMenuTestSpheres(test);  // SPHERES
    //spheres.addActionListener(new TestLODHnd());

//     dev.tristan.MenuFactory.addMenuItems(test);
    tomsk.old.shannon.MenuFactory.addMenuItems(test);
    JMenuItem particles = addMenuTestParticles(test);  // PARTICLES
    particles.addActionListener(new TestParticlesHnd());

    addMenuTestBrownian(test);  // BROWNIAN
    addMenuTestBrownianByRef(test);  // BROWNIAN-BY-REF

    JMenuItem colorCube = addMenuTestColorCube(test);  // COLOR CUBE
    colorCube.addActionListener(new TestColorCubeHnd());

    JMenuItem rotatedCube = addMenuTestRotatedCube(test); // ROTATED CUBE
    rotatedCube.addActionListener(new TestRotatedCubeHnd());

    JMenuItem spinCube = addMenuTestSpinCube(test); // SPINNING CUBE
    spinCube.addActionListener(new TestSpinCubeHnd());

    JMenu settings = addMenuSettings();
    addMenuLookFeel(settings, model);
    JMenu help = addMenuHelp();
    JMenuItem helpAbout = addMenuAbout(help);
    helpAbout.addActionListener(new DisplayHelpAboutTomskHnd());
  }
  private void addMenuOpenPDBFile(JMenu menu) {
    String help = "PDB File ...";     // DeepView /Swiss PDB Viewer example
    JMenuItem item = new JMenuItem(help);
//    item.addActionListener(new AdapterUCCToALThread(new UCOpenTrainTable()));
    item.addActionListener(new AdapterUCCToAL(new UCOpenPDBFile()));
    item.setMnemonic(KeyEvent.VK_P);
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)); // as in DeepView
    menu.add(item);
  }

  private JMenuItem addMenuTestSpheres(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("LOD Spheres");
    menuItem.setMnemonic(KeyEvent.VK_S);
    menu.add(menuItem);
    return menuItem;
  }
  private JMenuItem addMenuTestAtoms(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Atoms");
    menuItem.setMnemonic(KeyEvent.VK_A);
    menu.add(menuItem);
    return menuItem;
  }
  private void init() {
    ImageIcon icon = loadImageIcon("/tsvlib/project/images/dmitry_logo_16.gif");
//    ImageIcon icon = loadImageIcon("/kingroup/images/dmitry_logo_16.gif");
    if (icon != null)
      this.setIconImage(icon.getImage());

    // from http://www.j3d.org/tutorials/quick_fix/swing.html
    // Swing is lightweight and J3D is heavy weight.
    // This means that a Canvas3D will draw on top of Swing objects no matter what order Swing thinks it should draw in.
    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

    TomskMainUI ui = TomskMainUI.getInstance();
    setLayout(new BorderLayout());
    add(ui, BorderLayout.CENTER);
  }
  protected JMenu addMenuWorld() {
    JMenu menu = new JMenu("World");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_M);
    return menu;
  }
  protected JMenuItem addMenuBuild(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Build");
    menuItem.setMnemonic(KeyEvent.VK_B);
    menu.add(menuItem);
    return menuItem;
  }
  protected JMenu addMenuTest(JMenu menu) {
    JMenu menuItem = new JMenu("Test");
    menuItem.setMnemonic(KeyEvent.VK_T);
    menu.add(menuItem);
    return menuItem;
  }
  protected JMenuItem addMenuTestParticles(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Particles");
    menuItem.setMnemonic(KeyEvent.VK_P);
    menu.add(menuItem);
    return menuItem;
  }
  protected JMenuItem addMenuTestBrownian(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Brownian Motion");
    menuItem.setMnemonic(KeyEvent.VK_B);
    menu.add(menuItem);
    menuItem.addActionListener(new AdapterUCCToALThread(new UCRunBrownianMotionTest()));

    return menuItem;
  }
  protected JMenuItem addMenuTestBrownianByRef(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Brownian Motion by-ref");
    menuItem.setMnemonic(KeyEvent.VK_R);
    menu.add(menuItem);
    menuItem.addActionListener(new AdapterUCCToALThread(new UCRunBrownianByRef()));
    return menuItem;
  }
  protected JMenuItem addMenuTestColorCube(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Color Cube");
    menuItem.setMnemonic(KeyEvent.VK_C);
    menu.add(menuItem);
    return menuItem;
  }
  protected JMenuItem addMenuTestRotatedCube(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Rotated Cube");
    menuItem.setMnemonic(KeyEvent.VK_R);
    menu.add(menuItem);
    return menuItem;
  }
  protected JMenuItem addMenuTestSpinCube(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Spinning Cube");
    menuItem.setMnemonic(KeyEvent.VK_S);
    menu.add(menuItem);
    return menuItem;
  }
//  protected JMenuItem addMenuImport(JMenu menu) {
//    JMenuItem menuItem = new JMenuItem("PDB file ...");
//    menuItem.setMnemonic(KeyEvent.VK_I);
//    menu.add(menuItem);
//    menuItem.addActionListener(new AdapterUCCToALThread(new UCOpenPDBFile()));
//    return menuItem;
//  }

  private void setupMenuFile(JMenu menuFile) {
//    menuFile.removeAll();
    ImageIcon icon = null;

//    String help = "Import Data Set/Table ...";
//    JMenuItem item = new JMenuItem(help);
//    item.addActionListener(new AdapterUCCToALThread(new UCOpenTable()));
//    item.setMnemonic(KeyEvent.VK_I);
//    menuFile.add(item);

//    String fileSaveHelp = "Save";
//    JMenuItem fileSave = new JMenuItem(fileSaveHelp);
//    icon = loadSaveFileIcon();
//    if (icon != null)
//      fileSave = new JMenuItem(fileSaveHelp, icon);
//    fileSave.addActionListener(new AdapterUCCToALThread(new UCSelectAndSaveResultsFile()));
//    fileSave.setMnemonic(KeyEvent.VK_S);
//    menuFile.add(fileSave);
    String help = "Save Results As ...";
    JMenuItem item = new JMenuItem(help);
//    item.addActionListener(new AdapterUCCToALThread(new UCSaveResultsUI()));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    item.setMnemonic(KeyEvent.VK_A);
    menuFile.add(item);
    menuFile.addSeparator();

    help = "Open Project ...";
    item = new JMenuItem(help);
//    item.addActionListener(new AdapterUCCToALThread(new UCOpenProject()));
    item.setMnemonic(KeyEvent.VK_O);
    menuFile.add(item);

    help = "Save Project As ...";
    item = new JMenuItem(help);
//    item.addActionListener(new AdapterUCCToALThread(new UCSaveProject()));
    item.setMnemonic(KeyEvent.VK_P);
    menuFile.add(item);
    menuFile.addSeparator();

    JMenuItem fileExit = new JMenuItem("Exit");
    fileExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.exit(0);
      }
    });

    //fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
    fileExit.setMnemonic(KeyEvent.VK_X);
    menuFile.add(fileExit);
    menuFile.setMnemonic(KeyEvent.VK_F);
  }
}
