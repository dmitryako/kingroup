package kingroup_v2.fsr;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.Kingroup;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopByGroup;
import kingroup_v2.pop.sample.sys.SysPopPart;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.pop.sample.usr.UsrPopByGroup;
import kingroup_v2.pop.sample.usr.UsrPopPart;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.pop.sample.usr.UsrPopView;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.tablex.TableViewWithOpt;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/10/2005, Time: 17:54:14
 */
public class DisplayOptView extends GridBagView {
  private JRadioButton user;
  private JRadioButton sys;
  private JCheckBox byGroup;
  public DisplayOptView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  public static void updateView(TableViewWithOpt updateView, Partition partB, SysPop sysPop, UsrPopSLOW usrPop, Kingroup project) {
//    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    JTable table;
    if (project.getShowUserView()) {
//      UsrPopSLOW usrPop = ui.getUsrPop();
      UsrPopSLOW usrPart = new UsrPopPart(partB, usrPop);
      if (project.getSortByGroup())
        usrPart = new UsrPopByGroup(usrPart);
      UsrPopView userView = new UsrPopView(usrPart);
      table = userView.getTableView();
    }else{
      SysPop sysPart = new SysPopPart(partB, sysPop);
      if (project.getSortByGroup())
        sysPart = new SysPopByGroup(sysPart);
      SysPopView sysView = new SysPopView(sysPart);
      table = sysView.getTableView();
    }
    if (updateView != null) {
      updateView.setTableView(table);
      updateView.assembleWithOpt();
    }
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Display");
    setBorder(titled);
    user = new JRadioButton("User");
    sys = new JRadioButton("System");
    ButtonGroup bGroup = new ButtonGroup();
    bGroup.add(user);
    bGroup.add(sys);

    byGroup = new JCheckBox("sort");
    byGroup.setToolTipText("sort by group");
  }
  public void loadTo(Kingroup model) {
    model.setShowUserView(user.isSelected());
    model.setSortByGroup(byGroup.isSelected());
  }
  protected void loadFrom(Kingroup model) {
    user.setSelected(model.getShowUserView());
    sys.setSelected(!model.getShowUserView());
    byGroup.setSelected(model.getSortByGroup());
  }
  protected void assemble() {
    endRow(byGroup);
    endRow(user);
    endRow(sys);
  }
  public void onViewTypeChange(UCController uc) {
    user.addActionListener(new AdapterUCCToALThread(uc));
    sys.addActionListener(new AdapterUCCToALThread(uc));
  }
  public void onSortByGroupChange(UCController uc) {
    byGroup.addActionListener(new AdapterUCCToALThread(uc));
  }
}
