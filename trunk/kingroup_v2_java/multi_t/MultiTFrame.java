package multi_t;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.ucm.cervus.UCCervusShowAlleleAnalysis;
import kingroup_v2.ucm.import_sample.UCImportPop;
import kingroup_v2.ucm.pop.UCKonHegFreqAlgShowOpt;
import kingroup_v2.ucm.save_results.UCSaveResultsFileUI;
import kingroup_v2.ucm.tools.UCRunGC;
import multi_t.ucm.UCShowHelpAboutMultiT;
import multi_t.ucm.project.UCOpenProject;
import multi_t.ucm.project.UCSaveProject;
import pattern.ucm.AdapterUCCToALThread;
import tsvlib.project.Project;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2006, Time: 16:45:32
 */
public class MultiTFrame extends ProjectFrame {
  protected MultiTFrame(Project model) {
    super(model);
    setInstance(this);
    init();

    setupToolBar();               // TOOLBAR

    JMenu menu = addMenuFile();   // MENU | FILE
    setupMenuFile(menu);

    menu = addMenuSettings();      // MENU | SETTINGS
    addMenuLookFeel(menu, model);

    menu = addMenuTools();         // MENU | TOOLS
    addMenuRunGarbageCollector(menu);
    addMenuViewErrors(menu);

    menu = addMenuHelp();          // MENU | HELP
    JMenuItem helpAbout = addMenuAbout(menu);
    helpAbout.addActionListener(new UCShowHelpAboutMultiT(this));
  }

  private void setupMenuFile(JMenu menuFile) {
    menuFile.removeAll();
    ImageIcon icon = null;

    String help = "Open File ...";
    JMenuItem item = new JMenuItem(help);
    item.addActionListener(new AdapterUCCToALThread(new UCImportPop()));
    item.setMnemonic(KeyEvent.VK_I);
    menuFile.add(item);

//    String fileSaveHelp = "Save";
//    JMenuItem fileSave = new JMenuItem(fileSaveHelp);
//    icon = loadSaveFileIcon();
//    if (icon != null)
//      fileSave = new JMenuItem(fileSaveHelp, icon);
//    fileSave.addActionListener(new AdapterUCCToALThread(new UCSelectAndSaveResultsFile()));
//    fileSave.setMnemonic(KeyEvent.VK_S);
//    menuFile.add(fileSave);
    help = "Save Results As ...";
    item = new JMenuItem(help);
    item.addActionListener(new AdapterUCCToALThread(new UCSaveResultsFileUI()));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    item.setMnemonic(KeyEvent.VK_A);
    menuFile.add(item);
    menuFile.addSeparator();

    help = "Open Project ...";
    item = new JMenuItem(help);
    item.addActionListener(new AdapterUCCToALThread(new UCOpenProject()));
    item.setMnemonic(KeyEvent.VK_O);
    menuFile.add(item);

    help = "Save Project As ...";
    item = new JMenuItem(help);
    item.addActionListener(new AdapterUCCToALThread(new UCSaveProject()));
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
  private void init() {
//    ImageIcon icon = loadImageIcon("images/kingroup_logo.gif");
//    ImageIcon icon = loadImageIcon("/tsvlib/project/images/kingroup_logo.gif");
    ImageIcon icon = loadImageIcon("/kingroup/images/Tomsk_icon_16.gif");
    if (icon != null)
      this.setIconImage(icon.getImage());

    // from http://www.j3d.org/tutorials/quick_fix/swing.html
    // Swing is lightweight and J3D is heavy weight.
    // This means that a Canvas3D will draw on top of Swing objects no matter what order Swing thinks it should draw in.
//    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    MultiTMainUI ui = MultiTMainUI.getInstance();
//    ui.setParentFrame(this);
    setLayout(new BorderLayout());
    add(ui, BorderLayout.CENTER);
  }
  protected JMenu addMenuPopulation() {
    JMenu menu = new JMenu("Population");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_P);
    return menu;
  }
  protected JMenu addMenuRELATEDNESS() {
    JMenu menu = new JMenu("Relatedness");
//    menu.setToolTipText("Test pedigree relationships");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_R);
    return menu;
  }
  protected JMenu addMenuKINSHIP(JMenu menu) {
    JMenu res = new JMenu("KINSHIP");
    res.setToolTipText("as per the KINSHIP program of " + Kinship.REFERENCE);
    res.setMnemonic(KeyEvent.VK_K);
    menu.add(res);
    return res;
  }
  protected JMenu addMenuRatio(JMenu menu) {
    JMenu res = new JMenu("Likelihood");
    res.setToolTipText("Assign pedigree relationships via likelihood ratios");
    res.setMnemonic(KeyEvent.VK_L);
    menu.add(res);
    return res;
  }
  protected void addMenuFreqAnalysis(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Analyse ...");
//    menuItem.setToolTipText("as per the CERVUS program of " + Cervus.REFERENCE);
    menuItem.setToolTipText("Analyse Alleles");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCCervusShowAlleleAnalysis()));
    menuItem.setMnemonic(KeyEvent.VK_A);
    pop.add(menuItem);
  }
  protected void addMenuFreqEstimateHW(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Estimate Hardy-Weinberg [WORK IN PROGRESS]...");
    menuItem.setToolTipText("WORK IN PROGRESS: Estimate Hardy-Weinberg allele frequencies");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCKonHegFreqAlgShowOpt()));
    menuItem.setMnemonic(KeyEvent.VK_N);
    pop.add(menuItem);
  }
  protected JMenu addMenuFSR() {
    JMenu menu = new JMenu("FSR");
    menu.setToolTipText("Full sibship reconstruction");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_R);
    return menu;
  }
  private void addMenuRunGarbageCollector(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Garbage");
    menuItem.setToolTipText("Run garbage collector");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCRunGC()));
//    menuItem.setMnemonic(KeyEvent.VK_G);
    menu.add(menuItem);
  }
  private void setupToolBar() {
    JToolBar toolBar = new JToolBar();
//    JButton button = null;

    //button = addToolButton(fileNameLayout_, "Layout", false); // Apply
    //button.addActionListener(new KCtrDisplayKinshipFileFormatGui(mainView_) ); // open and display
    //button.setToolTipText(kinshipLayoutHelp_);

    // IMPORT
    JButton button = new JButton("Import");
    ImageIcon icon = loadOpenFileIcon();
    if (icon != null) {
      button = new JButton(icon);
    }
    button = makeToolButton(button); // OPEN
    button.addActionListener(new AdapterUCCToALThread(new UCImportPop()));
//    button.addActionListener(new AdapterUCCToALThread(new UCSelectAndReadKinshipFile()));
    button.setToolTipText("Import population sample ...");
    toolBar.add(button);

    // SAVE
    button = new JButton("Save");
    icon = loadSaveFileIcon();
    if (icon != null) {
      button = new JButton(icon);
    }
    button = makeToolButton(button); // SAVE
    button.addActionListener(new AdapterUCCToALThread(new UCSaveResultsFileUI()));
    button.setToolTipText("Save results ...");
    toolBar.add(button);

//    toolBar.addSeparator();
//    button = addToolButton(fileNamePrimary_, "Primary", true);
//    button.addActionListener(new UCSelectKinshipLikeOptionsV1(true) );
//    button.setToolTipText("Calculate Primary Hypothesis");
//    button.setEnabled(false);
//    primaryHypo_ = button;
//
//    button = addToolButton(fileNameNull_, "Null", true);
//    button.addActionListener(new UCSelectKinshipLikeOptionsV1(false) );
//    button.setToolTipText("Calculate Null Hypothesis");
//    button.setEnabled(false);
//    nullHypo_ = button;
//
//    button = addToolButton(fileNameRatio_, "Ratio", true);
//    button.addActionListener(new UCCalcRatioTable() );
//    button.setToolTipText("Calculate Primary/Null Like Ratio");
//    button.setEnabled(false);
//    ratio_ = button;
//
//    toolBar.addSeparator();
//    button = addToolButton(fileNameGroups_, "Kin groups", true);
//    button.addActionListener(new DisplayPartitionGuiHnd() );
//    button.setToolTipText("PartitionSequence into kin groups");
//    button.setEnabled(false);
//    kingroups_ = button;
    add(toolBar, BorderLayout.NORTH);
  }
}

