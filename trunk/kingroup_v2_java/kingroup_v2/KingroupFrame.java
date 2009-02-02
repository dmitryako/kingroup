package kingroup_v2;
import kingroup_v2.heterozygosity.GuoThompson1992;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.like.milligan.Milligan;
import kingroup_v2.like.thompson.Thompson;
import kingroup_v2.relatedness.qg.QGRelatedness;
import kingroup_v2.ucm.UCPopBuilderFullSibs;
import kingroup_v2.ucm.UCShowHelpAboutKinGroupV2;
import kingroup_v2.ucm.cervus.UCCervusShowAlleleAnalysis;
import kingroup_v2.ucm.fsr.UCFsrSelectAlgUI;
import kingroup_v2.ucm.fsr.UCFsrShowLBoundUI;
import kingroup_v2.ucm.fsr.accuracy.UCFsrAccuracySelectAlgUI;
import kingroup_v2.ucm.import_sample.UCImportPop;
import kingroup_v2.ucm.kinship.UCKinshipShowLikeOpt;
import kingroup_v2.ucm.kinship.UCKinshipShowRatioOpt;
import kingroup_v2.ucm.kinship.UCShowPairwiseROpt;
import kingroup_v2.ucm.milligan.UCMilliganShowRatioOpt;
import kingroup_v2.ucm.pedigree.UCPedigreeShowRatioOpt;
import kingroup_v2.ucm.pop.*;
import kingroup_v2.ucm.project.UCOpenProject;
import kingroup_v2.ucm.project.UCSaveProject;
import kingroup_v2.ucm.save_results.UCSaveResultsFile;
import kingroup_v2.ucm.save_results.UCSaveResultsFileUI;
import kingroup_v2.ucm.thompson.UCThompsonShowRatioOpt;
import kingroup_v2.ucm.tools.UCCountCervusErrors;
import kingroup_v2.ucm.tools.UCRunGC;
import pattern.ucm.AdapterUCCToALThread;
import references.ThomasHill2002;
import tsvlib.project.Project;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import javax.swingx.textx.TextView;
import javax.utilx.log.PStreamToTextView;
import javax.iox.SplitPStream;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/09/2005, Time: 15:21:02
 */
public class KingroupFrame extends ProjectFrame {
  protected KingroupFrame(Project model) {
    super(model);
    setInstance(this);
    init();

    setupToolBar();   // TOOLBAR

    JMenu menu = addMenuFile(); // MENU | FILE
    setupMenuFile(menu);

    // MENU | POPULATION
    menu = addMenuPopulation();
    addMenuImportPop(menu);
    addMenuGenerateFullSibs(menu);
    addMenuGenerateHalfSibs(menu);
    addMenuGeneratePairs(menu);
    addMenuGenerateNullHypothesis(menu);

    // MENU | ALLELES
    menu = addMenuAlleles();
    addMenuImportPop(menu);
    addMenuFreqGenerate(menu);
    addMenuFreqAnalysis(menu);
    addMenuFreqEstimateHW(menu);

    // MENU | RELATEDNESS
    menu = addMenuRELATEDNESS();
    addMenuPairwiseR(menu);
//    addMenuMilligan2(menu);

//    // MENU | PARENTAGE
//    menu = addMenuParentage();
//    addMenuImportPop(menu);

    // MENU | PEDIGREE
    menu = addMenuPedigree();
    JMenu kinship = addMenuKINSHIP(menu);
    addMenuLike(kinship);
    addMenuRelated2(kinship);

    JMenu like = addMenuRatio(menu);
    addMenuPedigree(like);
    addMenuThompson(like);
//    addMenuMilligan(like);

    // MENU | FSR
    menu = addMenuFSR();
    addMenuFSRPartition(menu);
//    addMenuFSRAccuracy(menu);
//    addMenuFSRLowerBound(menu);

    // MENU | SETTINGS
//    menu = addMenuSettings();
//    addMenuLookFeel(menu, model);

    // MENU | TOOLS
//    menu = addMenuTools();
//    addMenuCountCervusErrors(menu);
//    addMenuRunGarbageCollector(menu);
//    addMenuViewErrors(menu);
//    addMenuFreqEstimateHW2(menu);

    // MENU | HELP
    menu = addMenuHelp();
    JMenuItem helpAbout = addMenuAbout(menu);
    helpAbout.addActionListener(new UCShowHelpAboutKinGroupV2(this));
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
    item.addActionListener(new AdapterUCCToALThread(new UCSaveResultsFileUI(new UCSaveResultsFile())));
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
    ImageIcon icon = loadImageIcon("/kingroup/images/kingroup_logo.gif");
    if (icon != null)
      this.setIconImage(icon.getImage());

    // from http://www.j3d.org/tutorials/quick_fix/swing.html
    // Swing is lightweight and J3D is heavy weight.
    // This means that a Canvas3D will draw on top of Swing objects no matter what order Swing thinks it should draw in.
//    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
//    ui.setParentFrame(this);
    setLayout(new BorderLayout());
//    getContentPane().add(ui, BorderLayout.CENTER);
    add(ui, BorderLayout.CENTER);

    TextView view = new TextView();
    ui.setOutView(view);
    PrintStream out = new PStreamToTextView(view);

    SplitPStream split = new SplitPStream(System.out);
    split.add(out);
    System.setOut(split);

    split = new SplitPStream(System.err);
    split.add(out);
    System.setErr(split);

    System.out.println("This console is the 'standard' output of KINGROUP: used for debugging and system-wide messages.");
    System.err.println("This console is the error output of KINGROUP: check this window for errors when something goes wrong.");

    String welcome = "Welcome to KINGROUP.";
    System.out.println(welcome);
    ui.setStatusWithTime(welcome);
  }
  protected JMenu addMenuPopulation() {
    JMenu menu = new JMenu("Population");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_P);
    return menu;
  }
  protected JMenu addMenuAlleles() {
    JMenu menu = new JMenu("Alleles");
    getJMenuBar().add(menu);
    menu.setToolTipText("Allele Frequencies");
    menu.setMnemonic(KeyEvent.VK_A);
    return menu;
  }
  protected JMenu addMenuPedigree() {
    JMenu menu = new JMenu("Pedigree");
    menu.setToolTipText("Test pedigree relationships");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_E);
    return menu;
  }
  protected JMenu addMenuParentage() {
    JMenu menu = new JMenu("Parentage");
    menu.setToolTipText("Parentage assignment");
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
//  protected JMenu addMenuKINSHIP(JMenu menu) {
//    JMenu res = new JMenu("KINSHIP");
//    res.setToolTipText("as per the KINSHIP program of " + Kinship.REFERENCE);
//    res.setMnemonic(KeyEvent.VK_K);
//    menu.add(res);
//    return res;
  //  }
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
    JMenuItem menuItem = new JMenuItem("Hardy-Weinberg");
    menuItem.setToolTipText("Estimate Hardy-Weinberg allele frequencies");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCKonHegFreqAlgShowOpt()));
    menuItem.setMnemonic(KeyEvent.VK_H);
    pop.add(menuItem);
  }
//  protected void addMenuFreqEstimateHW2(JMenu pop) {
//    JMenuItem menuItem = new JMenuItem("Work in progress");
////    menuItem.setToolTipText("Estimate Hardy-Weinberg allele frequencies");
//    menuItem.addActionListener(new AdapterUCCToALThread(new UCKonHegFreqAlgShowOpt()));
//    menuItem.setMnemonic(KeyEvent.VK_N);
//    pop.add(menuItem);
  //  }
  protected JMenu addMenuFSR() {
    JMenu menu = new JMenu("FSR");
    menu.setToolTipText("Full sibship reconstruction");
    getJMenuBar().add(menu);
    menu.setMnemonic(KeyEvent.VK_R);
    return menu;
  }
  protected void addMenuImportPop(JMenu pop) {
    String importStr = "Import ...";
    JMenuItem menuItem = new JMenuItem(importStr);
    ImageIcon icon = loadOpenFileIcon();
    if (icon != null)
      menuItem = new JMenuItem(importStr, icon);

//    JRadioButtonMenuItem importKinship = new JRadioButtonMenuItem("Kinship file");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCImportPop()));
    menuItem.setMnemonic(KeyEvent.VK_I);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
    pop.add(menuItem);
  }
  protected void addMenuFreqGenerate(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Generate ...");
    menuItem.setToolTipText("Generate allele frequencies");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCAlleleFreqBuilder()));
    menuItem.setMnemonic(KeyEvent.VK_G);
//    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
    pop.add(menuItem);
  }
  protected void addMenuGenerateFullSibs(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Generate Full-sibs ...");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCPopBuilderFullSibs()));
    menuItem.setMnemonic(KeyEvent.VK_F);
    pop.add(menuItem);
  }
  protected void addMenuGenerateNullHypothesis(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Generate Null hypothesis ...");
    menuItem.setToolTipText("random reshuffle sample alleles, see " + GuoThompson1992.REFERENCE);
    menuItem.addActionListener(new AdapterUCCToALThread(new UCGenerateNullHypothesisPop()));
    menuItem.setMnemonic(KeyEvent.VK_N);
    pop.add(menuItem);
  }
  protected void addMenuGenerateHalfSibs(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Generate Half-sibs ...");
    menuItem.setToolTipText("as per " + ThomasHill2002.REFERENCE);
    menuItem.addActionListener(new AdapterUCCToALThread(new UCPopBuilderHalfSibs()));
    menuItem.setMnemonic(KeyEvent.VK_H);
    pop.add(menuItem);
  }
  protected void addMenuGeneratePairs(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Generate KINSHIP Pairs ...");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCPopBuilderKinshipIBD()));
    menuItem.setMnemonic(KeyEvent.VK_K);
    pop.add(menuItem);
  }
  protected void addMenuPedigree(JMenu menu) {
    JMenuItem res = new JMenuItem("(Rm,Rp)-coefficients");
    res.setToolTipText("Likelihoods are from " + Kinship.REFERENCE);
    res.addActionListener(new AdapterUCCToALThread(new UCPedigreeShowRatioOpt()));
    res.setMnemonic(KeyEvent.VK_L);
    menu.add(res);
  }
  protected void addMenuMilligan(JMenu menu) {
    JMenuItem res = new JMenuItem("s-coefficients");
    res.setToolTipText("Likelihoods are from " + Milligan.REFERENCE);
    res.addActionListener(new AdapterUCCToALThread(new UCMilliganShowRatioOpt()));
    res.setMnemonic(KeyEvent.VK_S);
    menu.add(res);
  }
  protected void addMenuThompson(JMenu menu) {
    JMenuItem res = new JMenuItem("k-coefficients");
    res.setToolTipText("Likelihoods are from " + Thompson.REFERENCE);
    res.addActionListener(new AdapterUCCToALThread(new UCThompsonShowRatioOpt()));
    res.setMnemonic(KeyEvent.VK_K);
    menu.add(res);
  }
  protected void addMenuLike(JMenu pop) {
//    JMenuItem menuItem = new JMenuItem("Ratio & p-values");
////    menuItem.setToolTipText("not in KINSHIP");
//    menuItem.addActionListener(new AdapterUCCToALThread(new UCCalcKinshipPValue()));
//    menuItem.setMnemonic(KeyEvent.VK_P);
//    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P
//      , InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
//    pop.add(menuItem);

    JMenuItem menuItem = new JMenuItem("Likelihood ratio");
    menuItem.setToolTipText("Primary/Null like");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCKinshipShowRatioOpt()));
    menuItem.setMnemonic(KeyEvent.VK_L);
//    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
    pop.add(menuItem);

    menuItem = new JMenuItem("Primary likelihood");
    menuItem.setToolTipText("Primary hypothesis only");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCKinshipShowLikeOpt(true)));
    menuItem.setMnemonic(KeyEvent.VK_P);
//    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
    pop.add(menuItem);

    menuItem = new JMenuItem("Null likelihood");
    menuItem.setToolTipText("Null hypothesis only");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCKinshipShowLikeOpt(false)));
    menuItem.setMnemonic(KeyEvent.VK_N);
//    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
    pop.add(menuItem);
  }
  protected void addMenuRelated2(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Relatedness");
    menuItem.setToolTipText(QGRelatedness.REFERENCE);
    menuItem.addActionListener(new AdapterUCCToALThread(new UCShowPairwiseROpt()));
    menuItem.setMnemonic(KeyEvent.VK_R);
//    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
    pop.add(menuItem);
  }
  protected void addMenuPairwiseR(JMenu pop) {
    JMenuItem menuItem = new JMenuItem("Pairwise");
    menuItem.setToolTipText("Pairwise relatedness");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCShowPairwiseROpt()));
    menuItem.setMnemonic(KeyEvent.VK_P);
    pop.add(menuItem);
  }
  private void addMenuFSRPartition(JMenu fsr) {
    JMenuItem menuItem = new JMenuItem("Partition ...");
//    menuItem.setToolTipText("Perform FSR on the loaded population sample");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCFsrSelectAlgUI()));
    menuItem.setMnemonic(KeyEvent.VK_P);
//    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
    fsr.add(menuItem);
  }
  private void addMenuFSRLowerBound(JMenu fsr) {
    JMenuItem menuItem = new JMenuItem("Lower Bound ...");
//    menuItem.setToolTipText("Konovalov et al. (2006) Theor. Popul. Biol. submitted");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCFsrShowLBoundUI()));
    menuItem.setMnemonic(KeyEvent.VK_L);
    fsr.add(menuItem);
  }
  private void addMenuRunGarbageCollector(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("Garbage");
    menuItem.setToolTipText("Run garbage collector");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCRunGC()));
//    menuItem.setMnemonic(KeyEvent.VK_G);
    menu.add(menuItem);
  }
  private void addMenuCountCervusErrors(JMenu menu) {
    JMenuItem menuItem = new JMenuItem("CERVUS errors");
    menuItem.setToolTipText("Count CERVUS errors");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCCountCervusErrors()));
//    menuItem.setMnemonic(KeyEvent.VK_C);
    menu.add(menuItem);
  }
  private void addMenuFSRAccuracy(JMenu fsr) {
    JMenuItem menuItem = new JMenuItem("Accuracy ...");
    menuItem.setToolTipText("Determine accuracy of an FSR algorithm by simulation");
    menuItem.addActionListener(new AdapterUCCToALThread(new UCFsrAccuracySelectAlgUI()));
    menuItem.setMnemonic(KeyEvent.VK_A);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
    fsr.add(menuItem);
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
    button.addActionListener(new AdapterUCCToALThread(new UCSaveResultsFileUI(new UCSaveResultsFile())));
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
