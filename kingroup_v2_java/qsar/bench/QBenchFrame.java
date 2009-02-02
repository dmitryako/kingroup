package qsar.bench;
import kingroup_v2.ucm.tools.UCRunGC;
import pattern.ucm.AdapterUCCToAL;
import pattern.ucm.AdapterUCCToALThread;
import qsar.bench.descriptors.LFER.LferPlatts;
import qsar.bench.descriptors.dragon.EDragon;
import qsar.bench.descriptors.vcclab.Vcclab;
import qsar.bench.ref.Abraham_1993_LFER;
import qsar.bench.ref.Weininger_1988_SMILES;
import qsar.bench.ucm.LFER.UCShowLferPlattsUI;
import qsar.bench.ucm.UCSaveResults;
import qsar.bench.ucm.UCShowHelpAboutQBench;
import qsar.bench.ucm.alg.MLR.UCSelectMlrAlgUI;
import qsar.bench.ucm.alg.MLR.UCSelectRobMlrAlgUI;
import qsar.bench.ucm.project.UCOpenProject;
import qsar.bench.ucm.project.UCSaveProject;
import qsar.bench.ucm.smiles.UCOpenSmilesTable;
import qsar.bench.ucm.table.*;
import qsar.bench.ucm.table.merge_tables.UCAppendXToY;
import qsar.bench.ucm.table.merge_tables.UCAppendXToZ;
import qsar.bench.ucm.table.merge_tables.UCAppendZvToZ;
import qsar.bench.ucm.table.open.UCOpenPredictTable;
import qsar.bench.ucm.table.open.UCOpenTestTable;
import qsar.bench.ucm.table.open.UCOpenTrainTable;
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
 * User: jc138691, Date: 6/03/2007, Time: 11:06:35
 */
public class QBenchFrame extends ProjectFrame {
  protected QBenchFrame(Project model) {
    super(model);
    setInstance(this);
    init();

    setupToolBar();               // TOOLBAR

    JMenu menu = addMenuFile();   // MENU | FILE
    JMenu menu2 = addImportMenu(menu);
    menu2.setToolTipText("Import data set");
//    addMenuImportData(menu2);
    addMenuImportZ(menu2);
    addMenuImportX(menu2);
    addMenuImportTestZ(menu2);
//    addMenuImportSMILES(menu2);
    setupMenuFile(menu);

    menu = addEditMenu();   // MENU | EDIT
    
    menu2 = addDeleteMenu(menu); // MENU | EDIT | DELETE
    addMenuDeleteConstCols(menu2);
    addMenuDeleteSameCols(menu2);
    addMenuDeleteCols(menu2);
    addMenuDeleteCorrCols(menu2, "|r|>0.9", 0.9);
    addMenuDeleteCorrCols(menu2, "|r|>0.8", 0.8);
    addMenuDeleteCorrCols(menu2, "|r|>0.7", 0.7);
    addMenuDeleteCorrCols(menu2, "|r|>0.6", 0.6);
    addMenuDeleteCorrCols(menu2, "|r|>0.5", 0.5);

    menu2 = addMergeMenu(menu); // MENU | EDIT | MERGE
    addMenuMergeYX(menu2);
    addMenuMergeTrainAndPred(menu2);
    addMenuMergeTrainAndTest(menu2);

    menu2 = addModifyMenu(menu); // MENU | EDIT | Modify
    addMenuTranspose(menu2);
    addMenuInvertCols(menu2);
    addMenuRandomCols(menu2);

    menu2 = addNormMenu(menu); // MENU | EDIT | NORM
    addMenuMeanZeroVarOne(menu2);
    addMenuMinZeroMaxOne(menu2);

    menu2 = addSortMenu(menu); // MENU | EDIT | SORT
    addMenuSortZRowsByY(menu2);
    addMenuSortXColsByCorrToY(menu2);
    addMenuSortXColsByAbsCorrToY(menu2);

    menu2 = addSplitMenu(menu); // MENU | EDIT | SPLIT
    addMenuSplitRandom(menu2);

    menu2 = addEDragonMenu(menu); // MENU | EDIT | E-DRAGON
    addMenuRemoveErrorCode(menu2);
    addMenuDeleteDrgnErrCols(menu2);
    addMenuDelVcclabErrCols(menu2);


    menu = addMenuQSAR();      // MENU | QSAR
//    menu.addSeparator();
    addMenuRobustMLR(menu);     // MENU | QSAR | Robust MLR
    addMenuMLR(menu);     // MENU | QSAR | MLR

//    menu = addMenuStructure();      // MENU | Structure Descriptors
//    menu.add(new JMenu("DO NOT USE. WORK IN PROGRESS!!!"));
//    menu2 = addImportMenu(menu);
//    addMenuImportSMILES(menu2);
//    menu.addSeparator();
//    menu2 = addCalcMenu(menu);

//    menu2 = addMenuLFER(menu2);
//    addMenuLFER_Platts1999(menu2);

//    menu = addMenu3D();      // MENU | 3D
//    importMenu = addImportMenu(menu);
//    addMenuImportPDB(importMenu);
//    menu.addSeparator();
//
    menu = addMenuSettings();      // MENU | SETTINGS
    addMenuLookFeel(menu, model);

    menu = addMenuTools();         // MENU | TOOLS
//    menu2 = addMenuTables(menu);
    addMenuRunGarbageCollector(menu);
    addMenuViewErrors(menu);

    menu = addMenuHelp();          // MENU | HELP
    JMenuItem helpAbout = addMenuAbout(menu);
    helpAbout.addActionListener(new UCShowHelpAboutQBench(this));
  }

  protected JMenu addMenuTables(JMenu to) {
    JMenu menu = new JMenu("Large Tables");
    menu.setToolTipText("Tools for working with large QSAR tables");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_L);
    to.add(menu);
    return menu;
  }

  private void setupMenuFile(JMenu menuFile) {
//    menuFile.removeAll();
    ImageIcon icon = null;

    String help = "Save Results As ...";
    JMenuItem item = new JMenuItem(help);
    item.addActionListener(new AdapterUCCToALThread(new UCSaveResults()));
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
    ImageIcon icon = loadImageIcon("/tsvlib/project/images/dmitry_logo_16.gif");
//    ImageIcon icon = loadImageIcon("/kingroup/images/dmitry_logo_16.gif");
    if (icon != null)
      this.setIconImage(icon.getImage());

    // from http://www.j3d.org/tutorials/quick_fix/swing.html
    // Swing is lightweight and J3D is heavy weight.
    // This means that a Canvas3D will draw on top of Swing objects no matter what order Swing thinks it should draw in.
//    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    QBenchMainUI ui = QBenchMainUI.getInstance();
    setLayout(new BorderLayout());
    add(ui, BorderLayout.CENTER);

//    LineStreamView logView = ProjectLogger.getInstance().getLineStreamView();
//    logView.setOutputStream(ProjectLogger.getInstance().getOutputStream());
//    add(logView.getView(), BorderLayout.SOUTH);
  }
  private void addMenuTables2(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Large Tables");
    menuItem.setToolTipText("Working with large tables");
//    menuItem.addActionListener(new AdapterUCCToALThread(new UCRunGC()));
//    menuItem.setMnemonic(KeyEvent.VK_G);
    menu.add(menuItem);
  }

  private void addMenuRunGarbageCollector(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Garbage");
    menuItem.setToolTipText("Run garbage collector");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCRunGC()));
//    menuItem.setMnemonic(KeyEvent.VK_G);
    menu.add(menuItem);
  }

  private void addMenuImportZ(JMenu menu) {
    String help = "Z, eg for CALIBRATION/TRAINING...";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Z=(Y,X) matrix/table");
    item.addActionListener(new AdapterUCCToALThread(new UCOpenTrainTable()));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
    item.setMnemonic(KeyEvent.VK_T);
    menu.add(item);
  }
  private void addMenuMergeYX(JMenu menu) {
    String help = "X and Y ...";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Append columns of X-matrix to the first (Y) column of Z-matrix");
    item.addActionListener(new AdapterUCCToALThread(new UCAppendXToY()));
    item.setMnemonic(KeyEvent.VK_Y);
    menu.add(item);
  }
  private void addMenuMergeTrainAndPred(JMenu menu) {
    String help = "X and Z (by columns) ...";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Append columns of X-matrix to columns of Z-matrix");
    item.addActionListener(new AdapterUCCToALThread(new UCAppendXToZ()));
    item.setMnemonic(KeyEvent.VK_X);
    menu.add(item);
  }
  private void addMenuMergeTrainAndTest(JMenu menu) {
    String help = "Z and Z_v (by rows) ...";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Append rows of VALIDATION Z-matrix to rows of CALIBRATION Z-matrix (based on column names)");
    item.addActionListener(new AdapterUCCToALThread(new UCAppendZvToZ()));
    item.setMnemonic(KeyEvent.VK_V);
    menu.add(item);
  }

  private void addMenuDeleteConstCols(JMenu menu) {
    String help = "Constant columns in Z";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Delete constant columns in Z-matrix");
    item.addActionListener(new AdapterUCCToALThread(new UCDelConstCols()));
    item.setMnemonic(KeyEvent.VK_C);
    menu.add(item);
  }
  private void addMenuDeleteSameCols(JMenu menu) {
    String help = "Duplicate columns in Z";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Delete duplicate columns in Z-matrix");
    item.addActionListener(new AdapterUCCToALThread(new UCDelDuplicateCols()));
    item.setMnemonic(KeyEvent.VK_D);
    menu.add(item);
  }
  private void addMenuDeleteCols(JMenu menu) {
    String help = "Columns in Z";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Delete columns in Z-matrix");
    item.addActionListener(new AdapterUCCToALThread(new UCDelCols()));
    item.setMnemonic(KeyEvent.VK_Z);
    menu.add(item);
  }
  private void addMenuRandomCols(JMenu menu) {
    String help = "Randomize columns in Z";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Randomize columns in Z-matrix");
    item.addActionListener(new AdapterUCCToALThread(new UCRandomizeCols()));
    item.setMnemonic(KeyEvent.VK_R);
    menu.add(item);
  }
  private void addMenuInvertCols(JMenu menu) {
    String help = "Invert columns in Z";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Invert columns in Z-matrix");
    item.addActionListener(new AdapterUCCToALThread(new UCInvertCols()));
    item.setMnemonic(KeyEvent.VK_I);
    menu.add(item);
  }
  private void addMenuTranspose(JMenu menu) {
    String help = "Transpose Z";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Transpose Z-matrix");
    item.addActionListener(new AdapterUCCToALThread(new UCTranspose()));
    item.setMnemonic(KeyEvent.VK_T);
    menu.add(item);
  }
  private void addMenuDeleteCorrCols(JMenu menu, String tag, double corr) {
    String help = tag+" correlated columns in X";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Delete columns in X-matrix correlated with r>"+tag+" to the remainding columns");
    item.addActionListener(new AdapterUCCToALThread(new UCDelCorrCols(corr)));
    item.setMnemonic(KeyEvent.VK_D);
    menu.add(item);
  }
  private void addMenuMeanZeroVarOne(JMenu menu) {
    String help = "Standartize X in Z";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Standartize X columns in the Z-matrix to mean zero and variance one");
    item.addActionListener(new AdapterUCCToALThread(new UCMeanZeroVarOne()));
    item.setMnemonic(KeyEvent.VK_S);
    menu.add(item);
  }
  private void addMenuMinZeroMaxOne(JMenu menu) {
    String help = "To gray image";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Normalize columns in the Z-matrix to [0,1] range");
    item.addActionListener(new AdapterUCCToALThread(new UCMinZeroMaxOne()));
    item.setMnemonic(KeyEvent.VK_S);
    menu.add(item);
  }
  private void addMenuSortZRowsByY(JMenu menu) {
    String help = "Z rows by Y";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Sort rows in Z-matrix by Y-values [Toggle Ascending/Descending]");
    item.addActionListener(new AdapterUCCToALThread(new UCSortZRowsByY()));
    item.setMnemonic(KeyEvent.VK_R);
    menu.add(item);
  }
  private void addMenuSplitRandom(JMenu menu) {
    String help = "Z into Z and Z_v";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Randomly split Z into VALIDATION and CALIBRATION subsets");
    item.addActionListener(new AdapterUCCToALThread(new UCSplitZRandom()));
    item.setMnemonic(KeyEvent.VK_S);
    menu.add(item);
  }
  private void addMenuSortXColsByCorrToY(JMenu menu) {
    String help = "Z columns by correlation to Y";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Sort columns in Z-matrix by their correlation to Y-column [Toggle Ascending/Descending]");
    item.addActionListener(new AdapterUCCToALThread(new UCSortZColsByCorrToY()));
    item.setMnemonic(KeyEvent.VK_C);
    menu.add(item);
  }
  private void addMenuSortXColsByAbsCorrToY(JMenu menu) {
    String help = "Z columns by ABS correlation to Y";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Sort columns in Z by their absolute values of correlation to Y-column [Toggle Ascending/Descending]");
    item.addActionListener(new AdapterUCCToALThread(new UCSortZColsByAbsCorrToY()));
    item.setMnemonic(KeyEvent.VK_A);
    menu.add(item);
  }
  private void addMenuRemoveErrorCode(JMenu menu) {
    String help = "Replace '-999' with 0";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Replace error code '-999' with 0");
    item.addActionListener(new AdapterUCCToALThread(new UCRemoveDrgnErrCode()));
    item.setMnemonic(KeyEvent.VK_R);
    menu.add(item);
  }
  private void addMenuDeleteDrgnErrCols(JMenu menu) {
    String help = "Remove EDRAGON errors";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Remove descriptor columns containing error code '-999'");
    item.addActionListener(new AdapterUCCToALThread(new UCDelErrCols(EDragon.ERROR_CODE)));
    item.setMnemonic(KeyEvent.VK_E);
    menu.add(item);
  }
  private void addMenuDelVcclabErrCols(JMenu menu) {
    String help = "Remove VCCLAB error";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Remove descriptor columns containing error code '999'");
    item.addActionListener(new AdapterUCCToALThread(new UCDelErrCols(Vcclab.ERROR_CODE)));
    item.setMnemonic(KeyEvent.VK_V);
    menu.add(item);
  }
//  private void addMenuImportData(JMenu menu) {
//    String help = "Data set ...";
//    JMenuItem item = new JMenuItem(help);
//    item.addActionListener(new AdapterUCCToALThread(new UCOpenTrainTable()));
//    item.setMnemonic(KeyEvent.VK_T);
//    menu.add(item);
//  }
  private void addMenuImportTestZ(JMenu menu) {
    String help = "Z_v for VALIDATION/TESTING ...";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("Z-matrix/table");
    item.addActionListener(new AdapterUCCToALThread(new UCOpenTestTable()));
    item.setMnemonic(KeyEvent.VK_E);
    menu.add(item);
  }
  private void addMenuImportX(JMenu menu) {
    String help = "X, eg for PREDICTION ...";
    JMenuItem item = new JMenuItem(help);
    item.setToolTipText("X-matrix/table");
    item.addActionListener(new AdapterUCCToALThread(new UCOpenPredictTable()));
    item.setMnemonic(KeyEvent.VK_P);
    menu.add(item);
  }
  private void addMenuMLR(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("MLR");
    menuItem.setToolTipText("Multiple Linear Regression");
    menuItem.addActionListener(new AdapterUCCToAL(new UCSelectMlrAlgUI()));
    menuItem.setMnemonic(KeyEvent.VK_M);
    menu.add(menuItem);
  }
  private void addMenuRobustMLR(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Robust MLR");
    menuItem.setToolTipText("Robust Multiple Linear Regression");
    menuItem.addActionListener(new AdapterUCCToAL(new UCSelectRobMlrAlgUI()));
    menuItem.setMnemonic(KeyEvent.VK_R);
    menu.add(menuItem);
  }
  private void addMenuImportPDB(JMenu menu) {
    String help = "PDB ...";
    JMenuItem item = new JMenuItem(help);
//    item.addActionListener(new AdapterUCCToALThread(new UCOpenTrainTable()));
    item.setMnemonic(KeyEvent.VK_P);
    menu.add(item);
  }
  private void addMenuImportSMILES(JMenu menu) {
    String help = "SMILES ...";
    JMenuItem item = new JMenuItem(help);
    item.addActionListener(new AdapterUCCToALThread(new UCOpenSmilesTable()));
    item.setToolTipText(Weininger_1988_SMILES.REFERENCE);
    item.setMnemonic(KeyEvent.VK_S);
    menu.add(item);
  }
  protected JMenu addMenuLFER(JMenu to) {
    JMenu menu = new JMenu("LFER");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_L);
    menu.setToolTipText("LFER descriptors of " + Abraham_1993_LFER.REFERENCE);
    return menu;
  }
  protected JMenu addMenuStructure() {
    JMenu menu = new JMenu("Descriptors");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_D);
    menu.setToolTipText("Structure Descriptors [DO NOT USE. WORK IN PROGRESS!!!]");
    return menu;
  }
  protected JMenu addMenuQSAR() {
    JMenu menu = new JMenu("QSAR");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_Q);
    return menu;
  }
  protected JMenu addMenu3D() {
    JMenu menu = new JMenu("3D");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_3);
    return menu;
  }
  private void addMenuLFER_Platts1999(JMenu menu) {
    String help = "via method of Plats et al (1999) ...";
    JMenuItem item = new JMenuItem(help);
    item.addActionListener(new AdapterUCCToALThread(new UCShowLferPlattsUI()));
    item.setMnemonic(KeyEvent.VK_P);
    item.setToolTipText(LferPlatts.REFERENCE);
    menu.add(item);
  }
  private void setupToolBar() {
    JToolBar toolBar = new JToolBar();
    JButton button = null;
    ImageIcon icon = null;

    //button = addToolButton(fileNameLayout_, "Layout", false); // Apply
    //button.addActionListener(new KCtrDisplayKinshipFileFormatGui(mainView_) ); // open and display
    //button.setToolTipText(kinshipLayoutHelp_);

    // IMPORT
    button = new JButton("Import");
    icon = loadOpenFileIcon();
    if (icon != null) {
      button = new JButton(icon);
    }
    button = makeToolButton(button); // OPEN
    button.addActionListener(new AdapterUCCToALThread(new UCOpenTrainTable()));
    button.setToolTipText("Import Data Set/Table ...");
    toolBar.add(button);

    // SAVE
    button = new JButton("Save");
    icon = loadSaveFileIcon();
    if (icon != null) {
      button = new JButton(icon);
    }
    button = makeToolButton(button); // SAVE
    button.addActionListener(new AdapterUCCToALThread(new UCSaveResults()));
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
  protected JMenu addMergeMenu(JMenu to) {
    JMenu menu = new JMenu("Merge");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_M);
    return menu;
  }
  protected JMenu addDeleteMenu(JMenu to) {
    JMenu menu = new JMenu("Delete");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_D);
    return menu;
  }
  protected JMenu addEDragonMenu(JMenu to) {
    JMenu menu = new JMenu("VCCLAB");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_V);
    return menu;
  }
  protected JMenu addSortMenu(JMenu to) {
    JMenu menu = new JMenu("Sort");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_S);
    return menu;
  }
  protected JMenu addSplitMenu(JMenu to) {
    JMenu menu = new JMenu("Split");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_P);
    return menu;
  }
  protected JMenu addModifyMenu(JMenu to) {
    JMenu menu = new JMenu("Modify");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_M);
    return menu;
  }
  protected JMenu addNormMenu(JMenu to) {
    JMenu menu = new JMenu("Normalize");
    to.add(menu);
    menu.setMnemonic(KeyEvent.VK_N);
    return menu;
  }
  protected JMenu addEditMenu() {
    JMenu menu = new JMenu("Edit");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_E);
    return menu;
  }
}

