package kingroup_v2.ucm;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.PopView;
import kingroup_v2.pop.sample.builder.groups.PopBuilderFullSibView;
import kingroup_v2.pop.sample.builder.view.PopBuilderView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.pop.sample.usr.UsrPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.pop.sample.usr.UsrPopView;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swingx.ApplyDialogUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/09/2005, Time: 08:06:39
 */
public class UCPopBuilderFullSibs implements UCController
{
  private final static ProjectLogger log = ProjectLogger.getLogger(UCPopBuilderFullSibs.class.getName());

  public PopBuilderView makeBuilderView(PopBuilderModel builder)
  {
    return new PopBuilderFullSibView(builder);
  }

  public boolean run()
  {
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    SysPop sysPop = ui.getSysPop();
    if (sysPop == null
      || sysPop.getFreq() == null
      || sysPop.getFreq().getNumLoci() == 0
      || sysPop.getFreq().getMaxNumAlleles() == 0  ) {
      ui.showMessageLoadAlleleFreqFirst();
      return false;
    }

    Kingroup project = KinGroupV2Project.getInstance();
//    KinshipFileFormat kinship = project.getKinshipFileFormat();
    PopBuilderModel model = project.getPopBuilder();
    PopBuilderView builderView = makeBuilderView(model);

    ApplyDialogUI gui = new ApplyDialogUI(builderView, KingroupFrame.getInstance(), true);
    gui.setTitle("Generate population sample");
    gui.center();
    gui.setFocusOnApply();
    gui.setVisible(true);
    if (!gui.apply())
      return false;

    builderView.loadTo(model);
    project.saveProjectToDefaultLocation();

    SysAlleleFreq sysFreq = sysPop.getFreq();
    sysPop = SysPopFactory.makeSysPopFrom(model, sysFreq);
//    log.info("\nsysPop=\n" + sysPop);

    UsrPopSLOW oldPop = ui.getUsrPop();
//    log.info("\noldPop=\n" + oldPop);
    UsrPopSLOW usrPop = UsrPopFactory.makeUsrPopFrom(sysPop, oldPop.getFreq(), model);
//    log.info("\nusrPop=\n" + usrPop);

    // POP VIEWS
    PopView popView = ui.getPopView();
    if (popView == null)
      popView = new PopView();
    SysPopView sysPopView = new SysPopView(sysPop);
    popView.setSysPopView(sysPopView);
    UsrPopView userPopView = new UsrPopView(usrPop);
    popView.setUserPopView(userPopView);

    // FREQ VIEWS
//    String delim = kinship.getColumnDelimStr() + " ";
//    AlleleFreqView freqView = new AlleleFreqView(sysPop.getFreq(), usrPop.getFreq(), delim);

    ui.resetAll();
//    popView.setAlleleFreqView(freqView);
    ui.setPopView(popView);
    return true;
  }

}
