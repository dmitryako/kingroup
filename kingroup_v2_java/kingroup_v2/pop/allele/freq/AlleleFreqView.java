package kingroup_v2.pop.allele.freq;
import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 09:32:19
 */
public class AlleleFreqView extends JPanel {
  private int usrIdx = 0;
  private int sysIdx = 1;
  private JTabbedPaneX ui;
  private UsrAlleleFreqView usrView;
  private SysAlleleFreqView sysView;
  public AlleleFreqView() {
    init();
  }

  public AlleleFreqView(SysAlleleFreq sysFreq, UsrAlleleFreq usrFreq, String delim)
  {
    init();
    SysAlleleFreqView sysFreqView = new SysAlleleFreqView(sysFreq);
    setSysAlleleFreqView(sysFreqView);

    UsrAlleleFreqView usrFreqView = new UsrAlleleFreqView(usrFreq, delim);
    setUserAlleleFreqView(usrFreqView);
  }

  public void setUserAlleleFreqView(UsrAlleleFreqView view) {
    this.usrView = view;
    ui.setComponentAt(usrIdx, view);
    rebuild(usrIdx);
  }
  public void setSysAlleleFreqView(SysAlleleFreqView view) {
    this.sysView = view;
    ui.setComponentAt(sysIdx, view);
    rebuild(sysIdx);
  }
  public void reset() {
    ui.removeAll();
    usrView = null;
    sysView = null;
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    ui = new JTabbedPaneX(JTabbedPane.TOP);
    ui.addTab("Frequencies", null, new JPanel(), "Allele frequencies");
    ui.addTab("FreqSys", null, new JPanel(), "System view of frequencies");
    add(ui);
  }
  private void rebuild(int focusIdx) {
    ui.setSelectedIndex(focusIdx);
    validate();
    repaint();
  }
  public UsrAlleleFreq getUserAlleleFreq() {
    if (usrView == null)
      return null;
    return usrView.getUserAlleleFreq();
  }
  public SysAlleleFreq getSysAlleleFreq() {
    if (sysView == null)
      return null;
    return sysView.getSysAlleleFreq();
  }
  public UsrAlleleFreqView getUserAlleleFreqView() {
    return usrView;
  }

  public void setUsrFocus() {
    ui.setSelectedIndex(usrIdx);
  }
}

