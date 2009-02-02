package kingroup_v2.pop.sample;
import kingroup_v2.pop.allele.freq.AlleleFreqView;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.UsrAlleleFreq;
import kingroup_v2.pop.allele.freq.UsrAlleleFreqView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.pop.sample.usr.UsrPopView;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 09:17:27
 */
public class PopView extends JPanel {
  private int popIdx = 0;
  private int sysIdx = 1;
  private int freqIdx = 2;
//  private int groupIdx = 3;

  private JTabbedPaneX ui;

  private UsrPopView popView;
  private SysPopView sysView;
  private AlleleFreqView freqView;
  private String popName;

  public PopView() {
    init();
  }
  public void setUserPopView(UsrPopView view) {
    this.popView = view;
    ui.setComponentAt(popIdx, view);
    rebuild(popIdx);
  }
  public void setPopFocus() {
    ui.setSelectedIndex(popIdx);
  }
  public void setSysPopView(SysPopView view) {
    this.sysView = view;
    ui.setComponentAt(sysIdx, view);
    rebuild(sysIdx);
  }
  public void setAlleleFreqView(AlleleFreqView view) {
    if (freqView == null)
      ui.addTab("Alleles", null, new JPanel(), "Allele frequencies");
    
    this.freqView = view;
    ui.setComponentAt(freqIdx, view);
    rebuild(freqIdx);
  }

  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    ui = new JTabbedPaneX(JTabbedPane.TOP);
    ui.addTab("Data", null, new JPanel(), "User view");
    ui.addTab("DataSys", null, new JPanel(), "System view of data");
    add(ui);
  }
  private void rebuild(int focusIdx) {
    ui.setSelectedIndex(focusIdx);
//    validate();
    repaint();
  }
  public UsrPopView getUserPopSampleView() {
    return popView;
  }
  public UsrPopSLOW getUserPop() {
    if (popView == null)
      return null;
    return popView.getUserPop();
  }
  public SysPop getSysPop() {
    if (sysView == null)
      return null;
    return sysView.getSysPop();
  }

  public UsrAlleleFreqView getUserAlleleFreqView() {
    if (freqView == null)
      return null;
    return freqView.getUserAlleleFreqView();
  }
  public AlleleFreqView getAlleleFreqView() {
    return freqView;
  }
  public SysAlleleFreq getSysAlleleFreq() {
    if (freqView == null)
      return null;
    return freqView.getSysAlleleFreq();
  }
  public UsrAlleleFreq getUserAlleleFreq() {
    if (freqView == null)
      return null;
    return freqView.getUserAlleleFreq();
  }

  public String getPopName() {
    return popName;
  }

  public void setPopName(String popName) {
    this.popName = popName;
  }

}
