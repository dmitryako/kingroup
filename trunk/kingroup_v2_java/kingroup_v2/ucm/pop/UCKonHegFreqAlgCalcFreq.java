package kingroup_v2.ucm.pop;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.cervus.AlleleAnalysisFactory;
import kingroup_v2.fsr.DisplayOptView;
import kingroup_v2.kinship.KinshipFileFormat;
import kingroup_v2.pop.allele.freq.*;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.PopView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.view.KingroupViewI;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.vec.Vec;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/03/2008, Time: 15:56:35
 */
public class UCKonHegFreqAlgCalcFreq implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCKonHegFreqAlgCalcFreq.class.getName());
  private TableViewWithOpt updateView;
  protected KingroupViewI optView;

  public UCKonHegFreqAlgCalcFreq(KingroupViewI optView) {
    this.optView = optView;
  }
  public boolean run() {
    Kingroup project = KinGroupV2Project.getInstance();
    PopBuilderModel builder = project.getPopBuilder();
    builder.setParentAlleleFreqType(builder.FREQ_CALC_KON_HEG_2008);
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    UsrPopSLOW usrPop = ui.getUsrPop();
    if (usrPop == null || usrPop.size() == 0) {
      ui.showMessageLoadPopFirst();
      return false;
    }
    SysAlleleFreq sysFreq;
    PopView popView;

    UsrAlleleFreq usrFreq = UsrAlleleFreqFactory.calcFrom(usrPop);    //log.info("\n new usrFreq= \n" + usrFreq);
    usrPop.setFreq(usrFreq);

    SysPop sysPop = SysPopFactory.makeSysPopFrom(usrPop);    //log.info("\n new sysPop= \n" + sysPop);
    sysFreq = sysPop.getFreq();                              //log.info("\n new sysFreq= \n" + sysFreq);
    SysAlleleFreqFactory.copyToUserFreq(usrFreq, sysFreq);    //log.info("\n new usrFreq= \n" + usrFreq);

    ProgressWnd progress = new ProgressWnd(KingroupFrame.getInstance(), "Infering Hardy-Weinberg allele frequencies");
    KonHegFreqAlg alg = new KonHegFreqAlg();
    alg.setProgress(progress);
    SysAlleleFreq newFreq = alg.calc(sysPop, project.getKonHegAlgNumIter(), updateView);
    Partition part = alg.getPartition();
    DisplayOptView.updateView(updateView, part, sysPop, usrPop, project);
    
    if (newFreq == null) {
      JOptionPane.showMessageDialog(KingroupFrame.getInstance()
        , "Algorithm was interrupted or did not converge.\nSample allele frequencies will be loaded.");
    }
    else {
      sysFreq = newFreq;
      sysPop.setFreq(sysFreq);
      SysAlleleFreqFactory.copyToUserFreq(usrFreq, sysFreq);
    }

    popView = ui.getPopView(); // KEEP usrPopView
    popView.setSysPopView(new SysPopView(sysPop));

    // FREQ VIEWS
    KinshipFileFormat kinship = project.getKinshipFileFormat();
    String delim = kinship.getColumnDelimStr() + " ";
    AlleleFreqView freqView = new AlleleFreqView(sysFreq, usrPop.getFreq(), delim);

//    ui.resetAll();
    popView.setAlleleFreqView(freqView);
    ui.setPopView(popView);
    return true;
  }

  public boolean run2() {
//    ImageIcon img = ProjectFrame.loadImageIcon("images/matrix.jpg");
//    ImageIcon img = ProjectFrame.loadImageIcon("images/see_matrix.jpg");

    // LOAD FROM OPTIONS VIEW
    Kingroup project = KinGroupV2Project.getInstance();
    //Kinship kinship = project.getKinship();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
    SysPop givenPop = mainUI.getSysPop();

//    SysAlleleFreq givenFreq = givenPop.getFreq();

    SysPop pop = SysPopFactory.makeDeepCopy(givenPop);
    int n = pop.size();
//    UsrPopView popView = mainUI.getUsrPopView();

    //LinkedList<Double> bestList = new LinkedList<Double>();
    //int MAX_LIST_SIZE = n;
    int MAX_COUNT = 10;
    int GROUP_IDX = 1;
    BitSet bestConfig = new BitSet();
    BitSet currConfig = new BitSet();
    BitSet newConfig = new BitSet();
    double bestZ = 0;
    double currZ = 0;

    double[] obsHet = AlleleAnalysisFactory.calcObservHeteroz(givenPop);
    log.info("\nobsHet=" + Vec.toString(obsHet));
    double obsHetAvr = Vec.mean(obsHet);

    RandomSeed rand = RandomSeed.getInstance();
    for (int i = 0; i < MAX_COUNT; i++) {
      if (i == 0) {
        newConfig.set(0, n);   // Include all
      }
      else {
        newConfig.set(0, n, false);
        newConfig.or(currConfig);
        newConfig.flip(rand.nextInt(n));
      }
      log.info("\nnewConfig = " + newConfig);
      //TODO: Check that all allele are present

      SysPopFactory.setGroupIds(GROUP_IDX, newConfig, pop); // need ids for viewing
      SysPop newGroup = SysPopFactory.makeGroupFrom(GROUP_IDX, pop);

      SysAlleleFreq newGroupFreq = SysAlleleFreqFactory.makeFrom(newGroup, true);
      newGroup.setFreq(newGroupFreq);

      double[] newHet = AlleleAnalysisFactory.calcTrueHeteroz(newGroup.getFreq());
      double newHetAvr = Vec.mean(newHet);
      double newZ = calcCost(obsHetAvr, newHetAvr);

      if (i == 0  ||  newZ < bestZ) {
        bestZ = newZ;
        bestConfig.set(0, n, false);
        bestConfig.or(newConfig);

//        showTable(newGroup, pop);
      }
      if (i == 0  ||  newZ < currZ) {
        currZ = newZ;
        currConfig.set(0, n, false);
        currConfig.or(newConfig);
      }
      else {
        // TODO: Simulated annealing
        continue;
      }
    }
    return true;
  }

  private double calcCost(double s, double s2) {
    return (s - s2) * (s - s2);
  }

  public void setViewForUpdate(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}

