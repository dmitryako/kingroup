package kingroup_v2.pop.sample;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.kinship.KinshipFileFormat;
import kingroup_v2.pop.allele.freq.AlleleFreqView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.pop.sample.usr.UsrPopView;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/02/2006, Time: 10:50:01
 */
public class PopGroupView extends JPanel
{
  private JTabbedPaneX ui;
  private SysPop[] sysGroups;
  private UsrPopSLOW[] usrGroups;

  public PopGroupView() {
    init();
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    ui = new JTabbedPaneX(JTabbedPane.TOP);
    add(ui);
  }

  public void addPopView(PopView popView) {
    ui.processView(popView, -1, true, popView.getPopName(), "");
    validate();
    repaint();
  }

  public void load(UsrPopSLOW[] usrGroups, SysPop[] sysGroups, KinshipFileFormat format) {
    setUsrGroups(usrGroups);
    setSysGroups(sysGroups);
    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
    String delim = format.getColumnDelimStr() + " ";
    for (int g = 0; g < usrGroups.length; g++)
    {
      UsrPopSLOW usrPop = usrGroups[g];
      PopView popView = new PopView();
      popView.setPopName(usrPop.getName());

      SysPop sysPop = sysGroups[g];
      SysPopView sysView = new SysPopView(sysPop);
      popView.setSysPopView(sysView);

      UsrPopView usrView = new UsrPopView(usrPop);
      popView.setUserPopView(usrView);

      addPopView(popView);

      AlleleFreqView freqView;
      if (format.getFreqSource() == format.FREQ_SOURCE_BIAS) {
        freqView = new AlleleFreqView(sysPop.getFreq(), usrPop.getFreq(), delim);
        popView.setAlleleFreqView(freqView);
      }
      else {
//        freqView = new AlleleFreqView(mainUI.getSysAlleleFreq()
//          , mainUI.getUserAlleleFreq(), delim);
//        popView.setAlleleFreqView(freqView);
      }
      popView.setPopFocus();
    }
  }

  public SysPop[] getSysGroups() {
    return sysGroups;
  }

  public void setSysGroups(SysPop[] sysGroups) {
    this.sysGroups = sysGroups;
  }

  public UsrPopSLOW[] getUsrGroups() {
    return usrGroups;
  }

  public void setUsrGroups(UsrPopSLOW[] usrGroups) {
    this.usrGroups = usrGroups;
  }
}
