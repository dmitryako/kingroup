package kingroup_v2.ucm.pop;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopNullHypothesisFactory;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.pop.sample.usr.UsrPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.pop.sample.usr.UsrPopView;
import pattern.ucm.UCController;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/11/2006, Time: 11:01:53
 */
public class UCGenerateNullHypothesisPop implements UCController
{
  public boolean run()
  {
    Kingroup project = KinGroupV2Project.getInstance();
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW pop = ui.getUsrPop();
    if (pop == null   ||  pop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }
    SysPop sysPop = ui.getSysPop();
    SysAlleleFreq sysFreq = sysPop.getFreq();
    sysPop = SysPopNullHypothesisFactory.makeNullHypothesis(sysPop);
    sysPop.setFreq(sysFreq);
//    log.info("\nsysPop=\n" + sysPop);

    UsrPopSLOW oldPop = ui.getUsrPop();
//    log.info("\noldPop=\n" + oldPop);
    UsrPopSLOW usrPop = UsrPopFactory.makeUsrPopFrom(sysPop);
//    log.info("\nusrPop=\n" + usrPop);

    // POP VIEWS
    PopView popView = ui.getPopView();
    if (popView == null)
      popView = new PopView();
    SysPopView sysPopView = new SysPopView(sysPop);
    popView.setSysPopView(sysPopView);
    UsrPopView userPopView = new UsrPopView(usrPop);
    popView.setUserPopView(userPopView);

    ui.resetAll();
    ui.setPopView(popView);
    return true;
  }
}
