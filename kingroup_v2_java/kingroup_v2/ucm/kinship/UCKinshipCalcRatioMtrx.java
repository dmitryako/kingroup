package kingroup_v2.ucm.kinship;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.ucm.save_results.UCSaveResultsFileUI;
import kingroup_v2.kinship.*;
import kingroup_v2.kinship.like.KinshipLogMtrx;
import kingroup_v2.kinship.like.KinshipRatioMtrx;
import kingroup_v2.kinship.like.KinshipRatioSimTable;
import kingroup_v2.kinship.like.view.KinshipRatioOptView;
import kingroup_v2.kinship.view.KinshipRatioMtrxViewFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.usr.UsrPopView;
import kingroup_v2.view.KingroupViewI;
import pattern.mvc.MVCTableView;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.swingx.tablex.TableViewWithOpt;
import javax.iox.TextFile;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2005, Time: 15:28:37
 */
public class UCKinshipCalcRatioMtrx implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCKinshipCalcRatioMtrx.class.getName());
  private TableViewWithOpt updateView;
  protected KingroupViewI optView;
  protected UCKinshipCalcSigLevelsI sigTest;
  private ProgressWnd progress = null;

  public UCKinshipCalcRatioMtrx(KinshipRatioOptView optView) {
    this.optView = optView;
    sigTest = new UCKinshipCalcSigLevels(optView);
  }
  public UCKinshipCalcRatioMtrx() {
  }
  public KinshipLogMtrx makeRatioMtrx(SysPop sysPop) {
    KinshipRatioMtrx mtrx = new KinshipRatioMtrx(sysPop);
    return mtrx;
  }
  public boolean run() {
    // LOAD FROM OPTIONS VIEW
    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
    Kingroup project = KinGroupV2Project.getInstance();
    Kinship kinship = project.getKinship();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    KinshipIBDComplex primIBD = kinship.getComplexPrimIBD();
    KinshipIBD[] nullArr = kinship.getNullArr();
    if (KinshipIBDFactory.find(primIBD, nullArr)) {
      if (nullArr.length == 1) {
        String error = "Unable to proceed:"
          +"\nthe null hypothesis is the same as the primary hypothesis.";
        log.severe(error);
        JOptionPane.showMessageDialog(mainUI, error);
        return false;
      }
      else {
        String mssg = "This is NOT an error! Ignoring one of the null hypotheses which is the same as the primary hypothesis.";
        mainUI.setStatusWithTime(mssg);
      }
    }
    KinshipIBD[] savedNullArr = nullArr;              // this removes redundant null hypo
    nullArr = KinshipIBDFactory.remove(nullArr, primIBD);
    kinship.setNullArr(nullArr);

    if (!sigTest.run())  {
      return false;
    }
    KinshipRatioSimTable sigTable = sigTest.getRatioSimTable();

    if (!isValid()) {
      return false;
    }

    SysPop sysPop = mainUI.getSysPop();
    UsrPopView popView = mainUI.getUsrPopView();
    MVCTableView view = null;

    if (kinship.getDisplayByGroup()) {
      progress = new ProgressWnd(KingroupFrame.getInstance(), "Groups");
      SysPop[] arr = mainUI.getSysGroups();
      KinshipLogMtrx[] mtrxArr = new KinshipLogMtrx[arr.length];
      for (int i = 0; i < arr.length; i++) {
        if (progress != null
          && progress.isCanceled(i, 0, arr.length)) {
          cleanup();
          return false;
        }
//        // log.info("group =\n" + arr[i]);
        mtrxArr[i] = makeRatioMtrx(arr[i]);
        mtrxArr[i].calcComplexNull(project);
        int idx = arr[i].getIdIdx(0);
        mtrxArr[i].setName(popView.getGroupId(idx));
      }

      view = KinshipRatioMtrxViewFactory.makeArrView(mtrxArr, kinship, sigTable);
    }
    else
    {
      KinshipLogMtrx m = makeRatioMtrx(sysPop);
      m.calcComplexNull(project);

      view = KinshipRatioMtrxViewFactory.makeView(m, kinship, sigTable);
    }
    kinship.setNullArr(savedNullArr);  // restore

    if (progress != null)
      progress.close();
    
    if (updateView != null) {
      updateView.setTableView(view.getTableView());
      updateView.assembleWithOpt(JSplitPane.VERTICAL_SPLIT);
    }
    return true;
  }

  public boolean isValid()
  {
    Kingroup project = KinGroupV2Project.getInstance();
    Kinship kinship = project.getKinship();

    KinshipIBD primIBD = kinship.getPrimIBD();
    KinshipIBD[] nullArr = KinshipIBDFactory.makeAll(kinship.getComplexNullIBD());
    nullArr = KinshipIBDFactory.remove(nullArr, primIBD);
    if (nullArr == null  || nullArr.length == 0) {
      String error = "Unable to calculate likelihood:\n\nCheck if the primary and null hypotheses are the same.";
      log.severe(error);
      JOptionPane.showMessageDialog(KingroupFrame.getInstance(), error);
      return false;
    }
    return true;
  }

  public void setViewForUpdate(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
  private void cleanup() {
    if (progress != null)
      progress.close();
    progress = null;
//    System.gc();
  }

}
