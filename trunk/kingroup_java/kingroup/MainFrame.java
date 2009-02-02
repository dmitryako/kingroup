package kingroup;
import kingroup.gui.MainGui;
import kingroup.gui.MainPopGui;
import kingroup.population.PopGroup;
import kingroup.project.KinGroupProjectFacade;
import kingroup.project.KinGroupProjectModel;
import kingroup.project.KinGroupProjectV1;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import javax.swingx.ProgressWnd;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
public class MainFrame extends ProjectFrame {
  private final int MIN_WIDTH = 640; // TBD: store into project
  private final int MIN_HEIGHT = 480;
  private String[] args_ = null;
  private String fileNameOpen_ = "";
  private String saveIconFileName = "";
  private String fileNamePrimary_ = "";
  private String fileNameNull_ = "";
  private String fileNameRatio_ = "";
  private String fileNameGroups_ = "";
  private String fileOpenHelp_ = "Import Kinship File ...";
  private String fileSaveHelp_ = "Save Results As ...";
  private String recentFileHelp_ = "Last Imported File";
  // Menues
  // TOOL BAR
  private JButton primaryHypo_;
  private JButton nullHypo_;
  private JButton ratio_;
  private JButton kingroups_;
  // MENU BAR
  private JMenu menuBarFile_;
  private JMenuItem recentFileName_ = null;
  private File recent_ = null;
  private JCheckBoxMenuItem trace_ = new JCheckBoxMenuItem("Trace execution");
  private JCheckBoxMenuItem error_ = new JCheckBoxMenuItem("Report errors");
  private JRadioButtonMenuItem default_ = new JRadioButtonMenuItem("System default");
  private JMenuItem helpAbout_ = new JMenuItem("About");
  public MainFrame(KinGroupProjectFacade model) {
    super(model);
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
    initialiseImages();
    ImageIcon icon = ProjectFrame.loadImageIcon("/kingroup/images/logo.gif");
    if (icon != null)
      this.setIconImage(icon.getImage());
    MainGui mainView = MainGui.getInstance();
    mainView.setParentFrame(this);
    MainPopGui popView = new MainPopGui(null);
    mainView.setMainPopulationGui(popView);
    getContentPane().add(mainView);
    setupToolBar();
    setupMenuBar(model);
    default_.setSelected(true);
    default_.doClick();
    ProgressWnd.getInstance().makeInstance(this);
  }
  private void initialiseImages() {
    //fileNameNew_     = "images/new.gif";
    fileNameOpen_ = "images/open.gif";
//    saveIconFileName    = "images/save.gif";
    fileNamePrimary_ = "images/primary.gif";
    fileNameNull_ = "images/null.gif";
    fileNameRatio_ = "images/like.gif";
    fileNameGroups_ = "images/groups.gif";
  }
  protected void setupMenuBar(KinGroupProjectFacade model) {
//    JMenuBar menuBar = new JMenuBar();
//
//    JMenu file = new JMenu("File");
//    menuBar.addLine(file);
//    setupMenuBarFile(file);
//
//    JMenu settings = new JMenu("Settings");
//    menuBar.addLine(settings);
//    addMenuLookFeel(settings, model);
//
//    JMenu tools = new JMenu("Tools");
//    menuBar.addLine(tools);
//    setupMenuBarTools(tools);
//
//    JMenu help = new JMenu("Help");
//    menuBar.addLine(help);
//    setupMenuBarHelp(help);
//
//    setJMenuBar(menuBar);
  }
  private void setupToolBar() {
    JToolBar toolBar = new JToolBar();
//    JButton button = null;

    //button = addToolButton(fileNameLayout_, "Layout", false); // Apply
    //button.addActionListener(new KCtrDisplayKinshipFileFormatGui(mainView_) ); // open and display
    //button.setToolTipText(kinshipLayoutHelp_);

    // IMPORT
    JButton button = new JButton("Import");
    ImageIcon icon = ProjectFrame.loadOpenFileIcon();
    if (icon != null) {
      button = new JButton(icon);
    }
    button = ProjectFrame.makeToolButton(button); // OPEN
//    button.addActionListener(new AdapterUCCToALThread(new UCSelectAndReadKinshipFile()));
    button.setToolTipText("Import population sample ...");
    toolBar.add(button);

    // SAVE
    button = new JButton("Save");
    icon = ProjectFrame.loadSaveFileIcon();
    if (icon != null) {
      button = new JButton(icon);
    }
    button = ProjectFrame.makeToolButton(button); // SAVE
//    button.addActionListener(new AdapterUCCToALThread(new UCSelectAndSaveResultsFile()));
    button.setToolTipText("Save results into a file ...");
    toolBar.add(button);
    toolBar.addSeparator();
    button = makeToolButton(fileNamePrimary_, "Primary", true);
//    button.addActionListener(new UCSelectKinshipLikeOptionsV1(true));
    button.setToolTipText("Calculate Primary Hypothesis");
    button.setEnabled(false);
    primaryHypo_ = button;
    toolBar.add(button);
    button = makeToolButton(fileNameNull_, "Null", true);
//    button.addActionListener(new UCSelectKinshipLikeOptionsV1(false));
    button.setToolTipText("Calculate Null Hypothesis");
    button.setEnabled(false);
    nullHypo_ = button;
    toolBar.add(button);
    button = makeToolButton(fileNameRatio_, "Ratio", true);
//    button.addActionListener(new UCCalcRatioTable());
    button.setToolTipText("Calculate Primary/Null Like Ratio");
    button.setEnabled(false);
    ratio_ = button;
    toolBar.add(button);
    toolBar.addSeparator();
    button = makeToolButton(fileNameGroups_, "Kin groups", true);
//    button.addActionListener(new DisplayPartitionGuiHnd());
    button.setToolTipText("PartitionSequence into kin groups");
    button.setEnabled(false);
    kingroups_ = button;
    toolBar.add(button);
    getContentPane().add(toolBar, BorderLayout.NORTH);
  }
  public JButton makeToolButton(String fileName, String text, final boolean activeOnValidData) {
    JButton button = new JButton(text);
    ImageIcon icon = ProjectFrame.loadImageIcon(fileName);
    if (icon != null)
      button = new JButton(icon);
    button.setToolTipText(text);
    button.setBorderPainted(false);
    button.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (activeOnValidData)
          activateHypoButtons();
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
  public void activateHypoButtons() {
    MainGui mainView = MainGui.getInstance();
    PopGroup data = mainView.getPopulation();
    boolean flag = true;
    if (data == null || !data.isValid())
      flag = false;
    if (primaryHypo_ != null && primaryHypo_.isEnabled() != flag)
      primaryHypo_.setEnabled(flag);
    if (nullHypo_ != null && nullHypo_.isEnabled() != flag)
      nullHypo_.setEnabled(flag);
    if (kingroups_ != null && kingroups_.isEnabled() != flag)
      kingroups_.setEnabled(flag);
    if (ratio_ != null && ratio_.isEnabled() != flag)
      ratio_.setEnabled(flag);
  }
  public void setupMenuBarFile() {
    setupMenuBarFile(menuBarFile_);
  }
  protected void setupMenuBarFile(JMenu file) {
    menuBarFile_ = file;
    file.removeAll();
    ImageIcon icon = null;

    // Saving a file menu item
    JMenuItem fileSave = new JMenuItem(fileSaveHelp_);
    icon = ProjectFrame.loadImageIcon(saveIconFileName);
    if (icon != null)
      fileSave = new JMenuItem(fileSaveHelp_, icon);
//    fileSave.addActionListener(new AdapterUCCToALThread(new UCSelectAndSaveResultsFile()));
    fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    fileSave.setMnemonic(KeyEvent.VK_S);
    file.add(fileSave);
    file.addSeparator();
    setUpRecentFileMenu(file);
    file.addSeparator();
    JMenuItem fileExit = new JMenuItem("Exit");
    fileExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.exit(0);
      }
    });
    //fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
    fileExit.setMnemonic(KeyEvent.VK_X);
    file.add(fileExit);
    file.setMnemonic(KeyEvent.VK_F);
  }
  public JMenuItem getRecentFileNameMenuItem() {
    return recentFileName_;
  }
  protected void setUpRecentFileMenu(JMenu file) {
    recent_ = KinGroupProjectV1.getInstance().getLastImportedKinshipFile();
    if (recent_ != null && recent_.exists()) {
      JMenu recentFiles = new JMenu(recentFileHelp_);
      recentFileName_ = new JMenuItem(recent_.toString());
      recentFileName_.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
//          new UCReadKinshipFile().run();
        }
      }); // open and display
      recentFileName_.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
      recentFiles.add(recentFileName_);
      recentFiles.setMnemonic(KeyEvent.VK_R);
      file.add(recentFiles);
    }
  }
  protected void setupMenuBarTools(JMenu menu) {
    KinGroupProjectModel project = KinGroupProjectV1.getInstance();
    trace_.setSelected(project.getDebugTrace());
    error_.setSelected(project.getDebugError());
    JMenu debug = new JMenu("debug");
    //debug.setMnemonic(KeyEvent.VK_D);
    debug.add(trace_);
    debug.add(error_);
    menu.add(debug);
    menu.setMnemonic(KeyEvent.VK_T);
  }
  public void setHelpAboutListener(ActionListener listener) {
    if (listener != null)
      helpAbout_.addActionListener(listener);
  }
  protected void setupMenuBarHelp(JMenu help) {
    helpAbout_.setMnemonic(KeyEvent.VK_A);
    help.add(helpAbout_);
    help.setMnemonic(KeyEvent.VK_H);
  }
  public void repaint() {
    activateHypoButtons();
    super.repaint();
  }
}