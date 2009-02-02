package kingroup_v2.ucm.relatedness;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.*;
import kingroup_v2.kinship.view.KinshipRMtrxArrView;
import kingroup_v2.kinship.view.KinshipRMtrxView;
import kingroup_v2.kinship.view.KinshipRelatedSortedArrayView;
import kingroup_v2.kinship.view.KinshipSortedMtrxView;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.usr.UsrPopView;
import kingroup_v2.refs.wang2002.RMtrx_W;
import kingroup_v2.relatedness.PairwiseRMtrx;
import kingroup_v2.relatedness.RMtrxOutbredKonHeg;
import pattern.mvc.MVCTableView;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2006, Time: 12:27:44
 */
public class PairwiseRMtrxCalculator {
  private static ProjectLogger log = ProjectLogger.getLogger(PairwiseRMtrxCalculator.class.getName());
  protected MVCTableView calcView(Kinship kinship, SysPop sysPop
    , SysAlleleFreq observedFreq,  SysPop[] sysGroups, UsrPopView usrPopView, String tag
  ) {
    MVCTableView view = null;
    if (kinship.getDisplayByGroup())   {  // BY GROUPS
      PairwiseRMtrx[] mtrxArr = calcMtrxArr(kinship, sysPop, observedFreq, sysGroups, usrPopView);
      if (kinship.getDisplaySorted()) {
        view = new KinshipRelatedSortedArrayView(mtrxArr, kinship, tag);
      }
      else {
        view = new KinshipRMtrxArrView(mtrxArr, kinship, tag);
      }
    }
    else { // WHOLE POP
      PairwiseRMtrx m = calcMtrx(kinship, sysPop, observedFreq, sysGroups);
      if (kinship.getDisplaySorted()) {
        view = new KinshipSortedMtrxView(m, kinship, tag);
      }
      else {
        view = new KinshipRMtrxView(m, kinship, tag);
      }
    }
    return view;
  }


  protected PairwiseRMtrx[] calcMtrxArr(Kinship kinship
    , SysPop sysPop
    , SysAlleleFreq observedFreq
    , SysPop[] sysGroups
    , UsrPopView usrPopView) {
    KinshipAlleleFreqOpt opt = kinship.getAlleleFreqOpt();

    PairwiseRMtrx[] mtrxArr = new PairwiseRMtrx[sysGroups.length];
    for (int i = 0; i < sysGroups.length; i++) {
      SysAlleleFreq savedGroupFreq = sysGroups[i].getFreq();
      if (opt.getCalcFreq()) {
        if (opt.getExclBias()) {  // BIAS-CORRECTED
          if (opt.getExclGroup()) {
            mtrxArr[i] = makeBiasGroup(sysGroups[i], sysPop, kinship, i);
          }
          else {
            mtrxArr[i] = makeBiasPair(sysGroups[i], sysPop, kinship, i);
          }
        }
        else {  // CALC FROM SAMPLE
          sysGroups[i].setFreq(observedFreq);
          sysPop.setFreq(observedFreq);
          mtrxArr[i] = makeRMtrx(sysGroups[i], sysPop);
        }
      }
      else {
        mtrxArr[i] = makeRMtrx(sysGroups[i], sysPop);
      }
      sysGroups[i].setFreq(savedGroupFreq);
      int idx = sysGroups[i].getIdIdx(0);
      mtrxArr[i].setName(usrPopView.getGroupId(idx));
    }
    return mtrxArr;
  }

  protected PairwiseRMtrx calcMtrx(Kinship kinship
    , SysPop sysPop
    , SysAlleleFreq observedFreq
    ,  SysPop[] sysGroups
  ) {
    KinshipAlleleFreqOpt opt = kinship.getAlleleFreqOpt();
    PairwiseRMtrx m;
    if (opt.getCalcFreq()) {
      if (opt.getExclBias()) {  // BIAS-CORRECTED
        if (opt.getExclGroup())
          m = makeBiasGroupArr(sysGroups, sysPop, kinship);
        else
          m = makeBiasPair(sysPop, sysPop, kinship, -1);
      }
      else {  // CALC FROM SAMPLE
        sysPop.setFreq(observedFreq);
        m = makeRMtrx(sysPop, sysPop);
      }
    }
    else {
      m = makeRMtrx(sysPop, sysPop);
    }
//      log.info("\n sysPop= \n" + sysPop);
//      log.info("\n sysPop.getFreq()= \n" + sysPop.getFreq());
//    m.calc(); // NOTE!!!!! moved to make-functions
    return m;
  }


  protected PairwiseRMtrx makeBiasGroup(SysPop group, SysPop refPop, Kinship kinship
    , int id) {
    PairwiseRMtrx mtrx;
    Kingroup project = KinGroupV2Project.getInstance();
    if (project.getPairwiseRType() == Kingroup.PAIRWISE_R_KH_OUTBRED)  {
      if (id == 0)
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance()
          , RMtrxOutbredKonHeg.makeMessageAboutBias());
      RMtrxOutbredKonHeg mtrx2 = new RMtrxOutbredKonHeg(group);
      mtrx2.setReferencePop(refPop); // needed for heteroz
      mtrx = mtrx2;
    }
    else if (project.getPairwiseRType() == Kingroup.PAIRWISE_R_KH_INBRED)  {
      if (id == 0)
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance()
          , RMtrxOutbredKonHeg.makeMessageAboutBias());
      RMtrxOutbredKonHeg mtrx2 = new RMtrxOutbredKonHeg(group);
      mtrx2.setReferencePop(refPop); // needed for heteroz
      mtrx = mtrx2;
    }
    else if (project.getPairwiseRType() == Kingroup.PAIRWISE_R_WANG)  {
      if (id == 0)
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance()
          , RMtrx_W.makeMessageAboutBias());
      mtrx = new RMtrx_W(group, refPop);
    }
    else {
      mtrx = new KinshipRMtrxBiasGroup(group, refPop, kinship);
      mtrx.setEstimator(PairwiseREstimatorFactory.makeREstimator(group, project.getPairwiseRType()));
    }
    mtrx.calc();    // REMEMBER TO CALC!!!
    return mtrx;
  }
  protected PairwiseRMtrx makeBiasGroupArr(SysPop[] groups, SysPop pop, Kinship kinship) {
    Kingroup project = KinGroupV2Project.getInstance();

    if (project.getPairwiseRType() == Kingroup.PAIRWISE_R_KH_OUTBRED)  {
      JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance()
        , RMtrxOutbredKonHeg.makeMessageAboutBias());
      PairwiseRMtrx mtrx = new RMtrxOutbredKonHeg(pop);
      mtrx.calc();
      return mtrx;
    }
    if (project.getPairwiseRType() == Kingroup.PAIRWISE_R_WANG)  {
      JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance()
        , RMtrx_W.makeMessageAboutBias());
      PairwiseRMtrx mtrx = new RMtrx_W(pop, pop);
      mtrx.calc();
      return mtrx;
    }

    PairwiseRMtrx mtrx = new KinshipRMtrxBiasGroupArr(groups, pop, kinship);
    mtrx.setEstimator(PairwiseREstimatorFactory.makeREstimator(pop, project.getPairwiseRType()));
    mtrx.calc();
    return mtrx;
  }
  protected PairwiseRMtrx makeRMtrx(SysPop group, SysPop refPop) {
    Kingroup project = KinGroupV2Project.getInstance();
    return PairwiseRMtrxFactory.makeRMtrx(group, refPop, project.getPairwiseRType());
  }
  protected PairwiseRMtrx makeBiasPair(SysPop group
    , SysPop refPop, Kinship kinship, int id) {
    PairwiseRMtrx mtrx;
    Kingroup project = KinGroupV2Project.getInstance();
    if (project.getPairwiseRType() == Kingroup.PAIRWISE_R_KH_OUTBRED)  {
      if (id == 0) {
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance()
          , RMtrxOutbredKonHeg.makeMessageAboutBias());
      }
      RMtrxOutbredKonHeg mtrx2 = new RMtrxOutbredKonHeg(group);
      mtrx2.setReferencePop(refPop); // needed for heteroz
      mtrx = mtrx2;
    }
    else if (project.getPairwiseRType() == Kingroup.PAIRWISE_R_WANG)  {
      if (id == 0) {
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance()
          , RMtrx_W.makeMessageAboutBias());
      }
      mtrx = new RMtrx_W(group, refPop);
    }
    else {
      mtrx = new KinshipRMtrxBiasPair(group, refPop, kinship);
      mtrx.setEstimator(PairwiseREstimatorFactory.makeREstimator(group, project.getPairwiseRType()));
    }
    mtrx.calc();
    return mtrx;
  }

}

