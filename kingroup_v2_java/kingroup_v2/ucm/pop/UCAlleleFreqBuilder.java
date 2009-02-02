package kingroup_v2.ucm.pop;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.KinshipFileFormat;
import kingroup_v2.pop.allele.freq.*;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.PopView;
import kingroup_v2.pop.sample.builder.AlleleFreqBuilderView;
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
 * User: jc138691, Date: 4/05/2006, Time: 16:15:03
 */
public class UCAlleleFreqBuilder   implements UCController
{
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  public boolean run()
  {
    Kingroup project = KinGroupV2Project.getInstance();
    PopBuilderModel builder = project.getPopBuilder();
    AlleleFreqBuilderView panel = new AlleleFreqBuilderView(builder);

    ApplyDialogUI gui = new ApplyDialogUI(panel, KingroupFrame.getInstance(), true);
    gui.setTitle("Generate Allele Frequencies");
    gui.center();
    gui.setFocusOnApply();
    gui.setVisible(true);
    if (!gui.apply())
      return false;

    panel.loadTo(builder);
    project.saveProjectToDefaultLocation();
    if (builder.getParentAlleleFreqType() == builder.FREQ_CALC_KON_HEG_2008) {
      return new UCKonHegFreqAlgShowOpt().run();
    }

    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW usrPop = ui.getUsrPop();
    SysAlleleFreq sysFreq;
    PopView popView;
    if (builder.getParentAlleleFreqType() == builder.FREQ_CALC_SAMPLE_FREQ) {
      if (usrPop == null || usrPop.size() == 0) {
        ui.showMessageLoadPopFirst();
        return false;
      }

      //log.info("\n builder.FREQ_CALC_SAMPLE_FREQ \n old usrPop= \n" + usrPop);
      UsrAlleleFreq usrFreq = UsrAlleleFreqFactory.calcFrom(usrPop);        //log.info("\n new usrFreq= \n" + usrFreq);
      usrPop.setFreq(usrFreq);

      SysPop sysPop = SysPopFactory.makeSysPopFrom(usrPop);        //log.info("\n new sysPop= \n" + sysPop);
      sysFreq = sysPop.getFreq();                                  //log.info("\n new sysFreq= \n" + sysFreq);
      SysAlleleFreqFactory.copyToUserFreq(usrFreq, sysFreq);        //log.info("\n new usrFreq= \n" + usrFreq);

      popView = ui.getPopView(); // KEEP usrPopView
      popView.setSysPopView(new SysPopView(sysPop));
    }
    else {
      sysFreq = SysAlleleFreqFactory.makeSysAlleleFreq(builder, usrPop);

      SysPop sysPop = new SysPop(1, sysFreq.getNumLoci());
      sysPop.setFreq(sysFreq);
      usrPop = UsrPopFactory.makeUsrPopFrom(sysPop);

      popView = new PopView();
      SysPopView sysPopView = new SysPopView(sysPop);
      popView.setSysPopView(sysPopView);
      UsrPopView userPopView = new UsrPopView(usrPop);
      popView.setUserPopView(userPopView);
    }

    // FREQ VIEWS
    KinshipFileFormat kinship = project.getKinshipFileFormat();
    String delim = kinship.getColumnDelimStr() + " ";
    AlleleFreqView freqView = new AlleleFreqView(sysFreq, usrPop.getFreq(), delim);

    ui.resetAll();
    popView.setAlleleFreqView(freqView);
    ui.setPopView(popView);
    return true;
  }
}

