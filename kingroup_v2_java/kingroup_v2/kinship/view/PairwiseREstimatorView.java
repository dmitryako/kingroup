package kingroup_v2.kinship.view;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.like.milligan.Milligan;
import kingroup_v2.lynch.Lynch;
import kingroup_v2.refs.KonovalovHeg2008_AlleleFreq;
import kingroup_v2.refs.KonovalovHeg2008_ML;
import kingroup_v2.refs.wang2002.Wang2002;
import kingroup_v2.relatedness.qg.QGRelatedness;
import pattern.ucm.AdapterUCCToALThread;
import pattern.ucm.UCShowHelpMssg;
import pattern.ucm.UCShowImage;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 11:02:27
 */
public class PairwiseREstimatorView
  extends GridBagView
{
  private JRadioButton kinship;
  private JRadioButton konHeg;
  private JRadioButton wang;
  private JRadioButton lynch;
  private JRadioButton test;
  private JRadioButton kinshipML;
  private JRadioButton milligan;

  public PairwiseREstimatorView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  public void loadTo(Kingroup model) {
    if (kinshipML.isSelected())
      model.setPairwiseRType(Kingroup.PAIRWISE_R_KINSHIP_ML);
    else if (konHeg.isSelected())
      model.setPairwiseRType(Kingroup.PAIRWISE_R_KH_OUTBRED);
    else if (lynch.isSelected())
      model.setPairwiseRType(Kingroup.PAIRWISE_R_LR);
    else if (wang.isSelected())
      model.setPairwiseRType(Kingroup.PAIRWISE_R_WANG);
    else if (milligan.isSelected())
      model.setPairwiseRType(Kingroup.PAIRWISE_R_MILLIGAN);
    else if (test.isSelected()) {
      //model.setPairwiseRType(Kingroup.PAIRWISE_R_IDENTIX);
      model.setPairwiseRType(Kingroup.PAIRWISE_R_TEST);
    }
    else
      model.setPairwiseRType(Kingroup.PAIRWISE_R_QG);
  }
  public String getReference() {
    if (konHeg.isSelected())
      return  "[Under review] bias corrected for sample kin structure and size";
    else if (kinshipML.isSelected())
      return  "RELATEDNESS estimator via Maximum Likelihoods of " + Kinship.REFERENCE;
    else if (milligan.isSelected())
      return  "RELATEDNESS estimator of " + Milligan.REFERENCE;
    else if (lynch.isSelected())
      return  "RELATEDNESS estimator of " + Lynch.REFERENCE;
    else if (wang.isSelected())
      return  "RELATEDNESS estimator of " + Wang2002.REFERENCE
        + " bias corrected for sample size via Eqs 12-14.";
    else if (test.isSelected()) {
      return  "TESTING";
      //return  "RELATEDNESS estimator from "
      //+ Identix.REFERENCE + ". NOTE! Different from IDENTIX for two of more loci. Asked IDENTIX corresponding author - no reply.";
    }
    else
      return  "RELATEDNESS estimator of " + QGRelatedness.REFERENCE + " in its KINSHIP ("
      + Kinship.REFERENCE + ") variation.";
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Pairwise");
    setBorder(titled);
    setToolTipText("Pairwise relatedness estimator");

    konHeg = new JRadioButton("Konovalov&Heg(2008)");
    konHeg.setToolTipText("Does not use allele frequencies, see "
      + KonovalovHeg2008_AlleleFreq.REFERENCE);

    kinship = new JRadioButton("KINSHIP");
    kinship.setToolTipText("as per the KINSHIP program of " + Kinship.REFERENCE);

    wang = new JRadioButton("Wang (2002)");
    wang.setToolTipText(Wang2002.REFERENCE);

    lynch = new JRadioButton("Lynch & Ritland (1999)");
    lynch.setToolTipText(Lynch.REFERENCE);

    //test = new JRadioButton("Identity");
    //test.setToolTipText("[NOTE! different from IDENTIXfor multiple loci] as per the IDENTIX program of " + Identix.REFERENCE);
    test = new JRadioButton("Milligan");
    test.setToolTipText("[Work in progress] DO NOT USE!");

    milligan = new JRadioButton("Milligan (2003)");
    milligan.setToolTipText(Milligan.REFERENCE);

    kinshipML = new JRadioButton("Maximum Likelihood");
    kinshipML.setToolTipText("via Likelihoods of " + Kinship.REFERENCE);

    ButtonGroup bGroup = new ButtonGroup();
    bGroup.add(kinship);
    bGroup.add(konHeg);
    bGroup.add(kinshipML);
    bGroup.add(milligan);
    bGroup.add(wang);
    bGroup.add(lynch);
    bGroup.add(test);
  }
  public void loadFrom(Kingroup model) {
    kinship.setSelected(true);
    if (model.getPairwiseRType() == Kingroup.PAIRWISE_R_KH_OUTBRED)
      konHeg.setSelected(true);
    if (model.getPairwiseRType() == Kingroup.PAIRWISE_R_KINSHIP_ML)
      kinshipML.setSelected(true);
    if (model.getPairwiseRType() == Kingroup.PAIRWISE_R_MILLIGAN)
      milligan.setSelected(true);
    //if (model.getPairwiseRType() == Kingroup.PAIRWISE_R_IDENTIX)
    if (model.getPairwiseRType() == Kingroup.PAIRWISE_R_TEST)
      test.setSelected(true);
    if (model.getPairwiseRType() == Kingroup.PAIRWISE_R_LR)
      lynch.setSelected(true);
    if (model.getPairwiseRType() == Kingroup.PAIRWISE_R_WANG)
      wang.setSelected(true);
  }
  private void assemble() {
    startRow(kinship);
    JButton bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowImage(
      "Help", "/kingroup/images/help_r_kinship.gif"
      , KingroupFrame.getInstance())));
    endRow(bttn);

    startRow(konHeg);
    bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      KonovalovHeg2008_AlleleFreq.REFERENCE_LONG
      , KingroupFrame.getInstance())));
    endRow(bttn);

    startRow(wang);
    bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      "The estimator of " + Wang2002.REFERENCE
        + " bias corrected for sample size via Eqs 12-14."
      , KingroupFrame.getInstance())));
    endRow(bttn);

    startRow(lynch);
    bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      "The estimator of "+Lynch.REFERENCE
      , KingroupFrame.getInstance())));
    endRow(bttn);

    startRow(kinshipML);
    bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      "The estimator of "+ KonovalovHeg2008_ML.REFERENCE
      , KingroupFrame.getInstance())));
    endRow(bttn);

//    startRow(milligan);
//    bttn = ProjectFrame.makeHelpButton();
//    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
//      "The pairwise relatedness estimator of " + Milligan.REFERENCE
//      , KingroupFrame.getInstance())));
//    endRow(bttn);

//    startRow(test);
//    bttn = ProjectFrame.makeHelpButton();
//    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
//      "[Work in progress] DO NOT USE!"
//    //"Still not getting the same values as IDENTIX for multiple loci."
//      , KingroupFrame.getInstance())));
//    endRow(bttn);
  }
}
