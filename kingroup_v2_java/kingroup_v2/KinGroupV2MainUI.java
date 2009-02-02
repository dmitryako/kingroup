package kingroup_v2;
import kingroup_v2.cervus.view.CervusMainUI;
import kingroup_v2.fsr.FsrAlgUI;
import kingroup_v2.fsr.accuracy.FsrAccuracyUI;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.view.KinshipRatioView;
import kingroup_v2.kinship.view.KinshipLikeView;
import kingroup_v2.kinship.view.KinshipMainUI;
import kingroup_v2.kinship.view.PairwiseRView;
import kingroup_v2.like.thompson.view.ThompsonRatioView;
import kingroup_v2.pedigree.ratio.view.PedigreeRatioView;
import kingroup_v2.pedigree.view.PedigreeMainUI;
import kingroup_v2.pop.allele.freq.KonHegFreqView;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.UsrAlleleFreq;
import kingroup_v2.pop.allele.freq.UsrAlleleFreqView;
import kingroup_v2.pop.sample.PopGroupView;
import kingroup_v2.pop.sample.PopView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.pop.sample.usr.UsrPopView;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import javax.swingx.textx.TextView;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/09/2005, Time: 08:52:16
 */
public class KinGroupV2MainUI extends JPanel {
//  private static KingroupFrame frame;
  private static KinGroupV2MainUI instance;
  private JTabbedPaneX tabbedPane;
  private JLabel status;

  private TextView outView;
  private int outIdx = -1;
  private JRadioButton outFocus;

  private Component fileView;
  private int fileIdx = -1;
  private JRadioButton fileFocus;

  private PopView popView;
  private int popIdx = -1;
  private JRadioButton popFocus;

  private KonHegFreqView popFreqView;
  private int popFreqIdx = -1;
  private JRadioButton popFreqFocus;

  private PopGroupView groupView;
  private int groupIdx = -1;
  private JRadioButton groupFocus;

  private KinshipMainUI kinshipUI;
  private int kinshipIdx = -1;
  private JRadioButton kinshipFocus;

  private PedigreeMainUI pedigreeUI;
  private int pedigreeIdx = -1;
  private JRadioButton pedigreeFocus;

  private CervusMainUI cervusUI;
  private int cervusIdx = -1;
  private JRadioButton cervusFocus;

  private PairwiseRView rView;
  private int rIdx = -1;
  private JRadioButton rFocus;

  private FsrAlgUI fsrView;
  private int fsrIdx = -1;
  private JRadioButton fsrFocus;

  private FsrAccuracyUI accuracyView;
  private int accuracyIdx = -1;
  private JRadioButton accuracyFocus;

  private KinGroupV2MainUI() {
    init();
  }
//  public JFrame getFrame() {return frame;}
//  public void setParentFrame(KingroupFrame frame) { this.frame = frame;}
  public static KinGroupV2MainUI getInstance() {
    if (instance == null) {
      instance = new KinGroupV2MainUI();
    }
    return instance;
  }

//  synchronized public void repaint()
//  {
//    super.repaint();
//  }

//  synchronized public void validate()
//  {
//    super.validate();    //To change body of overridden methods use File | Settings | File Templates.
//  }

  public void resetAll() {
    tabbedPane.removeAll();
    outIdx = -1;
    fileView = null;     fileIdx = -1;
    popView = null;      popIdx = -1;
    popFreqView = null;      popFreqIdx = -1;
    groupView = null;    groupIdx = -1;
    kinshipUI = null;    kinshipIdx = -1;
    cervusUI = null;     cervusIdx = -1;
    pedigreeUI = null;   pedigreeIdx = -1;
    rView = null;      rIdx = -1;
    fsrView = null;      fsrIdx = -1;
    accuracyView = null; accuracyIdx = -1;
  }
  public void keepFileFreqViews() {
    Component file = fileView;
    PopView pop = popView;
    PopGroupView groups = groupView;
    KonHegFreqView freqView = popFreqView;
    resetAll();
    fileView = file;
    popView = pop;
    groupView = groups;
    popFreqView = freqView;
  }
  private void init()
  {
    outFocus = new JRadioButton();
    fileFocus = new JRadioButton();
    popFocus = new JRadioButton();
    popFreqFocus = new JRadioButton();
    groupFocus = new JRadioButton();
    kinshipFocus = new JRadioButton();
    pedigreeFocus = new JRadioButton();
    cervusFocus = new JRadioButton();
    rFocus = new JRadioButton();
    fsrFocus = new JRadioButton();
    accuracyFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(outFocus);
    group.add(fileFocus);
    group.add(popFocus);
    group.add(popFreqFocus);
    group.add(groupFocus);
    group.add(kinshipFocus);
    group.add(pedigreeFocus);
    group.add(cervusFocus);
    group.add(rFocus);
    group.add(fsrFocus);
    group.add(accuracyFocus);
    fileFocus.setSelected(true);
    tabbedPane = new JTabbedPaneX(JTabbedPane.TOP);
    setLayout(new BorderLayout());

    outIdx = tabbedPane.processView(new JPanel(), outIdx, outFocus.isSelected()
      , "Console", "Console output for KINGROUP messages.");

    add(tabbedPane, BorderLayout.CENTER);

    status = new JLabel(" ", SwingConstants.LEFT);
    add(status, BorderLayout.SOUTH);
  }
  public void setStatus(String msg) {
    status.setText(msg);
  }
  public void setStatusWithTime(String msg) {
    SimpleDateFormat date = new SimpleDateFormat();
    try {
      date.applyPattern("HH:mm:ss");
    }
    catch (IllegalArgumentException e) {
    }
    status.setText(date.format(new Date()) + " : " + msg);
  }

  private void rebuild() {
    outIdx = tabbedPane.processView(outView, outIdx, outFocus.isSelected()
      , "Console", "Console output for KINGROUP messages.");
    fileIdx = tabbedPane.processView(fileView, fileIdx, fileFocus.isSelected()
      , "File", null);
    popIdx = tabbedPane.processView(popView, popIdx, popFocus.isSelected()
      , "Population", "Population sample");
    popFreqIdx = tabbedPane.processView(popFreqView, popFreqIdx, popFreqFocus.isSelected()
      , "Hardy-Weinberg", "Estimate Hardy-Weinberg Allele Frequencies from Population sample");
    groupIdx = tabbedPane.processView(groupView, groupIdx, groupFocus.isSelected()
      , "Groups", "View by groups");
    kinshipIdx = tabbedPane.processView(kinshipUI, kinshipIdx, kinshipFocus.isSelected()
      , "KINSHIP", Kinship.REFERENCE);
    pedigreeIdx = tabbedPane.processView(pedigreeUI, pedigreeIdx, pedigreeFocus.isSelected()
      , "Pedigree", "Test pedigree relationships");
    cervusIdx = tabbedPane.processView(cervusUI, cervusIdx, cervusFocus.isSelected()
      , "Analysis", "Allele Analysis");
//    , "Analysis", Cervus.REFERENCE);
    rIdx = tabbedPane.processView(rView, rIdx, rFocus.isSelected()
      , "Relatedness", null);
    fsrIdx = tabbedPane.processView(fsrView, fsrIdx, fsrFocus.isSelected()
      , "FSR", "Full Sibship Reconstruction");
    accuracyIdx = tabbedPane.processView(accuracyView, accuracyIdx, accuracyFocus.isSelected()
      , "Accuracy", "Accuracy of Full Sibship Reconstruction algorithm");
//    if (frame != null) {
//      frame.validate();
//      frame.repaint();
//    }

//    validate();
//    repaint();
  }
  public UsrPopView getUsrPopView() {
    if (popView == null)
      return null;
    return popView.getUserPopSampleView();
  }
  public UsrAlleleFreqView getUsrAlleleFreqView() {
    if (popView == null)
      return null;
    return popView.getUserAlleleFreqView();
  }
  public SysAlleleFreq getSysAlleleFreq() {
    if (popView == null)
      return null;
    return popView.getSysAlleleFreq();
  }
  public UsrAlleleFreq getUserAlleleFreq() {
    if (popView == null)
      return null;
    return popView.getUserAlleleFreq();
  }
  public UsrPopSLOW getUsrPop() {
    if (popView == null)
      return null;
    return popView.getUserPop();
  }
  public SysPop getSysPop() {
    if (popView == null)
      return null;
    return popView.getSysPop();
  }
  public void showMessageLoadPopFirst() {
    JOptionPane.showMessageDialog(this
      , "First you need to import or generate a population sample via MENU | POPULATION | ...");
  }
  public void showMessageLoadAlleleFreqFirst() {
    JOptionPane.showMessageDialog(this
      , "First you need to import or generate allele frequencies via MENU | ALLELES | ...");
  }
  public KinshipMainUI getKinshipMainUI() {
    return kinshipUI;
  }
  public PedigreeMainUI getPedigreeMainUI() {
    return pedigreeUI;
  }
  public CervusMainUI getCervusMainUI() {
    return cervusUI;
  }
  public boolean getHasResults() {
    return (fileView != null
      || popView != null
      || kinshipUI != null
      || cervusUI != null
      || rView != null
    );
  }

  public void setPopGroupView(PopGroupView view) {
    this.groupView = view;
    groupFocus.setSelected(true);
    rebuild();
  }


  public void setImportFileView(Component view) {
    resetAll();
    this.fileView = view;
    fileFocus.setSelected(true);
    rebuild();
  }
  public void setPopView(PopView view) {
    keepFileFreqViews();
    this.popView = view;
    popFocus.setSelected(true);
    rebuild();
  }
  public void setKinshipMainUI(KinshipMainUI v) {
    this.kinshipUI = v;
    kinshipFocus.setSelected(true);
    rebuild();
  }
  public void setPedigreeMainUI(PedigreeMainUI v) {
    this.pedigreeUI = v;
    pedigreeFocus.setSelected(true);
    rebuild();
  }
  public void setCervusMainUI(CervusMainUI v) {
    this.cervusUI = v;
    cervusFocus.setSelected(true);
    rebuild();
  }
  public void setPairwiseRView(PairwiseRView v) {
    this.rView = v;
    rFocus.setSelected(true);
    rebuild();
  }
  public void setFsrView(FsrAlgUI view) {
    fsrView = view;
    fsrFocus.setSelected(true);
    rebuild();
  }
  public void setFsrAccuracyView(FsrAccuracyUI view) {
    accuracyView = view;
    accuracyFocus.setSelected(true);
    rebuild();
  }


  public KinshipLikeView getKinshipPrimView() {
    if (kinshipUI == null)
      return null;
    return kinshipUI.getKinshipPrimView();
  }
  public KinshipLikeView getKinshipNullView() {
    if (kinshipUI == null)
      return null;
    return kinshipUI.getKinshipNullView();
  }
  public KinshipRatioView getKinshipRatioView() {
    if (kinshipUI == null)
      return null;
    return kinshipUI.getKinshipRatioView();
  }
  public PedigreeRatioView getPedigreeRatioView() {
    if (pedigreeUI == null)
      return null;
    return pedigreeUI.getPedigreeRatioView();
  }
  public ThompsonRatioView getThompsonRatioView() {
    if (pedigreeUI == null)
      return null;
    return pedigreeUI.getThompsonRatioView();
  }
  public FsrAlgUI getFsrView() {
    return fsrView;
  }
  public FsrAccuracyUI getFsrAccuracyView() {
    return accuracyView;
  }

  public PairwiseRView getPairwiseRView() {
    return rView;
  }

  public PopView getPopView() {
    return popView;
  }

  public SysPop[] getSysGroups() {
    if (groupView != null)
      return groupView.getSysGroups();
    return SysPopFactory.makeGroupsFrom(getSysPop());
  }
  public UsrPopSLOW[] getUsrGroups() {
    if (groupView == null)
      return null;
    return groupView.getUsrGroups();
  }

  public KonHegFreqView getKonHegFreqView() {
    return popFreqView;
  }

  public void setKonHegFreqView(KonHegFreqView popFreqView) {
    this.popFreqView = popFreqView;
    popFreqFocus.setSelected(true);
    rebuild();
  }

  public void setOutView(TextView view) {
    this.outView = view;
    outFocus.setSelected(true);
    rebuild();
  }
  public TextView getOutView() {
    return outView;
  }
}
