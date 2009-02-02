package kingroup_v2.ucm.relatedness;

import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipAlleleFreqOpt;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.usr.UsrPopView;
import kingroup_v2.view.KingroupViewI;
import pattern.mvc.MVCTableView;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.tablex.TableViewWithOpt;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/01/2006, Time: 16:55:23
 */
public class UCCalcPairwiseR extends PairwiseRMtrxCalculator
  implements UCController
{
  protected TableViewWithOpt updateView;
  protected final KingroupViewI optView;

  public UCCalcPairwiseR(KingroupViewI optView) {
    this.optView = optView;
  }
  public void setViewForUpdate(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }

  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    Kinship kinship = project.getKinship();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
    SysPop sysPop = mainUI.getSysPop();
    SysPop[] sysGroups = mainUI.getSysGroups();
    UsrPopView usrPopView = mainUI.getUsrPopView();

    SysAlleleFreq savedFreq = sysPop.getFreq();
    KinshipAlleleFreqOpt opt = kinship.getAlleleFreqOpt();
    SysAlleleFreq observedFreq = null;
    if (opt.getCalcFreq())
      observedFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);

    MVCTableView view = null;
    if (kinship.getDisplayPValues()) {
      view = new PairwiseRMtrxPValues(sysPop).calcView(kinship, sysPop, observedFreq,  sysGroups
        , usrPopView, "P-values");
    }
    else {
      view = calcView(kinship, sysPop, observedFreq,  sysGroups, usrPopView, "r");
    }
    System.gc();

    if (updateView != null) {
//      updateView.setTableView(view);
      updateView.setTableView(view.getTableView());
      updateView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    }
    sysPop.setFreq(savedFreq);
    return true;
  }
}
