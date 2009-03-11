package multi_t.project;
import multi_t.MultiT;
import multi_t.pcr.Primer;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2006, Time: 17:40:14
 */
public class ProjectSettingsUI extends GridBagView//JPanel
{
  //INPUT
  private JTextField refFileName;
  private JTextField primerFileName;
  private JTextField enzymeFileName;
  private JTextField profileFileName;
  private JButton refBttn;
  private JButton primerBttn;
  private JButton enzymeBttn;
  private JButton profileBttn;

  // PCR Primers
  private int FIELD_SIZE = 2;
  private JTextField fwdPrimSeq;
  private IntField fwdMaxNumErrs;
  private JTextField revPrimSeq;
  private IntField revMaxNumErrs;

  // Enzymes
//  private int FIELD_SIZE = 2;
//  private JTextField fwdPrimSeq;
//  private IntField fwdMaxNumErrs;
//  private JTextField revPrimSeq;
//  private IntField revMaxNumErrs;

  private JButton refreshBttn;
  private JButton saveBttn;

  public ProjectSettingsUI(MultiT model)
  {
    init();
    loadFrom(model);
    assemble();
  }

  private void init()
  {
//    setLayout(new BorderLayout());
    getEndRow().weightx = 1.0;

    // INPUT
    refFileName = new JTextField("Locate file");
    refFileName.setEditable(false);
    refBttn = ProjectFrame.makeLocateFileButton();

    primerFileName = new JTextField("Locate file");
    primerFileName.setEditable(false);
    primerBttn = ProjectFrame.makeLocateFileButton();

    enzymeFileName = new JTextField("Locate file");
    enzymeFileName.setEditable(false);
    enzymeBttn = ProjectFrame.makeLocateFileButton();

    profileFileName = new JTextField("Locate file");
    profileFileName.setEditable(false);
    profileBttn = ProjectFrame.makeLocateFileButton();

    // PCR PRIMERS
    fwdPrimSeq = new JTextField(" ");
    fwdPrimSeq.setEditable(false);
    revPrimSeq = new JTextField(" ");
    revPrimSeq.setEditable(false);

    fwdMaxNumErrs = new IntField(FIELD_SIZE, 0, 10);
    revMaxNumErrs = new IntField(FIELD_SIZE, 0, 10);

    // Controls
    refreshBttn = new JButton("Refresh");
    refreshBttn.setToolTipText("Reload from files");

    saveBttn = new JButton("Save");
    saveBttn.setToolTipText("Save settings");
  }

  public void loadFrom(MultiT model)
  {
    setFieldText(refFileName, model.getReferenceFileName());
    setFieldText(primerFileName, model.getPrimerFileName());
    setFieldText(enzymeFileName, model.getEnzymeFileName());
    setFieldText(profileFileName, model.getProfileFileName());

    Primer p = model.getFwdPrimer();
    fwdPrimSeq.setText(p.getSeq());
    fwdPrimSeq.setToolTipText(p.getLbl());
    fwdMaxNumErrs.setValue(p.getMaxNumErrs());

    p = model.getRevPrimer();
    if (p.getSeq().length() == 0) {
      revPrimSeq.setText("Missing");
      revPrimSeq.setToolTipText(null);
    }
    else {
      revPrimSeq.setText(p.getSeq());
      revPrimSeq.setToolTipText(p.getLbl());
      revMaxNumErrs.setValue(p.getMaxNumErrs());
    }
  }
  private void setFieldText(JTextField field, String name)
  {
    if (name != null  && name.length() > 0)
      field.setText(name);
  }

  public void runOnRefreshBttn(UCController uc) {
    refreshBttn.addActionListener(new AdapterUCCToALThread(uc));
  }
  public void runOnSaveBttn(UCController uc) {
    saveBttn.addActionListener(new AdapterUCCToALThread(uc));
  }
  public void runOnReferenceBttn(UCController uc) {
    refBttn.addActionListener(new AdapterUCCToALThread(uc));
  }
  public void runOnPrimerBttn(UCController uc) {
    primerBttn.addActionListener(new AdapterUCCToALThread(uc));
  }
  public void runOnEnzymeBttn(UCController uc) {
    enzymeBttn.addActionListener(new AdapterUCCToALThread(uc));
  }
  public void runOnProfileBttn(UCController uc) {
    profileBttn.addActionListener(new AdapterUCCToALThread(uc));
  }
  private void assemble() {
    endRow(assembleInput());
    middleRow(assemblePCR());
    endRow(assembleEnzymes());

    middleRow(new JPanel());
    getEndRow().anchor = GridBagConstraints.NORTHEAST;
    getEndRow().fill = GridBagConstraints.NONE;
    JPanel pnl = new JPanel();
    pnl.add(saveBttn);
    pnl.add(refreshBttn);
    endRow(pnl);

    getEndRow().weighty = 1.0;
    endRow(new JPanel());
  }
  private GridBagView assembleInput() {
    GridBagView gb = new GridBagView("Input Files");

    JLabel lbl = new JLabel("Reference");
    lbl.setToolTipText("File with reference species");
    gb.startRow(lbl);
    gb.middleRow(refFileName);
    gb.endRow(refBttn);

    lbl = new JLabel("Primers");
    lbl.setToolTipText("File with two primers");
    gb.startRow(lbl);
    gb.middleRow(primerFileName);
    primerBttn = ProjectFrame.makeLocateFileButton();
    gb.endRow(primerBttn);

    lbl = new JLabel("Enzymes");
    lbl.setToolTipText("File with restriction enzymes");
    gb.startRow(lbl);
    gb.middleRow(enzymeFileName);
    gb.endRow(enzymeBttn);

    lbl = new JLabel("Sample");
    lbl.setToolTipText("File with sample tRFPL profile");
    gb.startRow(lbl);
    gb.middleRow(profileFileName);
    gb.endRow(profileBttn);
    return gb;
  }
  private GridBagView assemblePCR() {
    GridBagView gb = new GridBagView("PCR Primers");
    gb.getEndRow().weightx = 1.;

    JLabel lbl = new JLabel("Errs");
    lbl.setToolTipText("Maximum number of allowed mismatches");
    gb.startRow(new JLabel());
    gb.startRow(lbl);
    lbl = new JLabel("Sequence (in 5'-3' order)");
    gb.endRow(lbl);

    lbl = new JLabel("Forward");
    lbl.setToolTipText("Forward primer");
    gb.startRow(lbl);
    gb.startRow(fwdMaxNumErrs);
    gb.endRow(fwdPrimSeq);
    //gb.endRow(refBttn);

    lbl = new JLabel("Reverse");
    lbl.setToolTipText("Reverse primer");
    gb.startRow(lbl);
    gb.startRow(revMaxNumErrs);
    gb.endRow(revPrimSeq);
    return gb;
  }

  private GridBagView assembleEnzymes() {
    GridBagView gb = new GridBagView("Enzymes");
//    gb.getEndRow().weightx = 1.;
//
//    JLabel lbl = new JLabel("Errs");
//    lbl.setToolTipText("Maximum number of allowed mismatches");
//    gb.startRow(new JLabel());
//    gb.startRow(lbl);
//    lbl = new JLabel("Sequence (in 5'-3' order)");
//    gb.endRow(lbl);
//
//    lbl = new JLabel("Forward");
//    lbl.setToolTipText("Forward primer");
//    gb.startRow(lbl);
//    gb.startRow(fwdMaxNumErrs);
//    gb.endRow(fwdPrimSeq);
//    //gb.endRow(refBttn);
//
//    lbl = new JLabel("Reverse");
//    lbl.setToolTipText("Reverse primer");
//    gb.startRow(lbl);
//    gb.startRow(revMaxNumErrs);
//    gb.endRow(revPrimSeq);
    return gb;
  }

  public void loadTo(MultiT project)
  {
    Primer p = project.getFwdPrimer();
    p.setMaxNumErrs(fwdMaxNumErrs.getInput());

    p = project.getRevPrimer();
    p.setMaxNumErrs(revMaxNumErrs.getInput());
  }
}
