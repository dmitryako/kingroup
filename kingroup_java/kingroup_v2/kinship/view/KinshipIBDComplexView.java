package kingroup_v2.kinship.view;
import kingroup.view.KinshipRatioSimViewV1;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBDComplex;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;
import pattern.ucm.UCShowHelpMssg;
import tsvlib.project.ProjectFrame;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.FloatTextField;
import javax.swingx.text_fieldx.IntTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KinshipIBDComplexView extends GridBagView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipIBDComplexView.class.getName());
  private static int FIELD_SIZE = 4;
  private FloatTextField rm;
  private FloatTextField rp;
  private FloatTextField rms;
  private FloatTextField rps;
  private IntTextField nRms;
  private IntTextField nRps;
  private JCheckBox complexHypo;
  private JCheckBox complexHypo2;
//  private FloatTextField error;
//  private JLabel errorLbl;

  private KinshipRatioSimViewV1 ksv_ = null;
  private static String pedigrees[] = {"Manual Input"
    , "Unrelated"
    , "Full siblings"
    , "Parent-offspring"
    , "Half siblings"
    , "Cousins"
    , "FullHaplodiploidSibs"
    , "HaplodiploidCousins"
  };
  private static float relRm[] = {0, 0, 0.5f, 1f, 0.5f, 0.25f, 0.5f, 0.375f};
  private static float relRp[] = {0, 0, 0.5f, 0f, 0.0f, 0.00f, 1.0f, 0.000f};
  private JComboBox pedigreeCombo = new JComboBox(KinshipIBDComplexView.pedigrees); // relationship choices
  private JComboBox pedigreeComboS = new JComboBox(KinshipIBDComplexView.pedigrees); // relationship choices
  public KinshipIBDComplexView(String label, KinshipIBDComplex model, Kinship kinship)
  {
    init(label);
    loadFrom(model, kinship);
    assemble();
  }

  private void init(String label)
  {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "" + label + " hypothesis");
    setBorder(titled);

    rm = new FloatTextField(FIELD_SIZE, 0f, 1f);
    rp = new FloatTextField(FIELD_SIZE, 0f, 1f);
    rms = new FloatTextField(FIELD_SIZE, 0f, 1f);
    rps = new FloatTextField(FIELD_SIZE, 0f, 1f);
    nRms = new IntTextField(FIELD_SIZE, 1, 10);
    nRps = new IntTextField(FIELD_SIZE, 1, 10);
    complexHypo = new JCheckBox("complex");
    complexHypo.setToolTipText("complex hypothesis");
    complexHypo2 = new JCheckBox("for action listeners");

//    errorLbl = new JLabel("error");
//    String tip = "Genotyping error rate per allele";
//    errorLbl.setToolTipText(tip);
//    error = new FloatTextField(FIELD_SIZE, 0f, 0f, 100f);
//    error.setMinimumSize(error.getPreferredSize());
//    error.setToolTipText(tip + " [0; 100]");

    complexHypo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        assemble();
        // todo: is there a better way to order action listeners???
        // bliat [051011]!!! trigger other action listeners
        ActionListener[] arr = complexHypo2.getActionListeners();
        if (arr == null)
          return;
        for (int i = 0; i < arr.length; i++) {
          ActionListener actionListener = arr[i];
          actionListener.actionPerformed(e);
        }
      }
    });
    pedigreeCombo.addActionListener(new PedigreeComboListener());
    pedigreeComboS.addActionListener(new PedigreeComboListenerS());
  }

  public void onComplexChange(UCController uc) {
    complexHypo2.addActionListener(new AdapterUCCToALThread(uc));
  }
  private void assemble() {
    if (complexHypo.isSelected())
      buildComplex();
    else
      buildSimple();
    invalidate();
//    log.info("min size = " + getMinimumSize());
//    log.info("pref size = " + getPreferredSize());
//    frame.validate();
//    frame.repaint();
    repaint();
  }
  private void buildSimple() {
//    log.info("simple");
    removeAll();
    endRow(pedigreeCombo);
    startRow(complexHypo);
    startRow(rm);
    endRow(rp);
//    startRow(new JLabel("Rm"));
//    startRow(new JLabel("Rp"));
//    endRow(new JLabel(""));
//    endRow(new JLabel(""));

//    if (error != null) {
//      startRow(errorLbl);
//      startRow(error);
//      endRow(new JLabel("%"));
//    }

    rm.setToolTipText("Rm [0; 1]");
    rp.setToolTipText("Rp [0; 1]");
  }
  private void buildComplex() {
//    log.info("complex");
    removeAll();
//    startRow(new JLabel(""));
//    startRow(new JLabel("Rm"));
//    endRow(new JLabel("Rp"));

    JButton bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      "From KINSHIP manual of " + Kinship.REFERENCE
        + ":\n\nA complex hypothesis has a range of (Rm, Rp) values instead of a single set.\n"
        + "Likelihood of each (Rm, Rp) combination is calculated and the maximum value of the range is used\n"
      + "as the likelihood of the complex hypothesis."
      , KingroupFrame.getInstance())));
    JPanel panel = new JPanel();
    panel.add(complexHypo);
    panel.add(bttn);

    startRow(panel);
    startRow(new JLabel("Rm"));
    startRow(new JLabel("Rp"));
    endRow(new JLabel());

    startRow(pedigreeCombo);
    startRow(rm);
    startRow(rp);
    endRow(new JLabel("From"));

    startRow(pedigreeComboS);
    startRow(rms);
    startRow(rps);
    endRow(new JLabel("To"));

    JLabel label = new JLabel("Search intervals");
    label.setToolTipText("number of search intervals");
    startRow(label);
    startRow(nRms);
    startRow(nRps);
    endRow(new JLabel());

    rm.setToolTipText("from Rm");
    rp.setToolTipText("from Rp");
    rms.setToolTipText("to Rm");
    rps.setToolTipText("to Rp");
  }
  private void loadFrom(KinshipIBDComplex model, Kinship kinship) {
//    NumberFormat nf = NumberFormat.getNumberInstance();
    rm.setValue((float)model.getRm());
    rp.setValue((float)model.getRp());
    rms.setValue((float)model.getRmMax());
    rps.setValue((float)model.getRpMax());
    nRms.setValue(model.getNumRms() - 1);
    nRps.setValue(model.getNumRps() - 1);
    complexHypo.setSelected(model.getComplex());
    loadPedigreeFrom(model);

//    if (kinship != null)
//      error.set(100f * kinship.getAlleleErrorRate());
//    else
//      error = null;
  }
  public void loadTo(KinshipIBDComplex model, Kinship kinship) {
    model.setRm(rm.getInput());
    model.setRp(rp.getInput());
    model.setRmMax(rms.getInput());
    model.setRpMax(rps.getInput());
    model.setNumRms(nRms.getInput() + 1);
    model.setNumRps(nRps.getInput() + 1);
    model.setComplex(complexHypo.isSelected());
//    if (error != null)
//      kinship.setAlleleErrorRate(error.getInput() / 100f);
  }
  private void loadPedigreeFrom(KinshipIBDComplex model) {
    float rm = (float)model.getRm();
    float rp = (float)model.getRp();
    int idx = matchPedigreeFrom(rm, rp);
    pedigreeCombo.setSelectedIndex(idx);
    rm = (float)model.getRmMax();
    rp = (float)model.getRpMax();
    idx = matchPedigreeFrom(rm, rp);
    pedigreeComboS.setSelectedIndex(idx);
  }
  private int matchPedigreeFrom(float rm, float rp) {
    int idx = 0;// NOTE: 0 is for manual input
    for (int i = 1; i < relRm.length && i < relRp.length; i++) // NOTE: 0 is for manual input
      if ((relRm[i] == rm && relRp[i] == rp)
        || (relRm[i] == rp && relRp[i] == rm)) {
        idx = i;
        break;
      }
    if (idx < 0 && idx >= pedigrees.length)
      idx = 0;
    return idx;
  }
  public void onIBDChange(UCController uc) {
//    log.info("called ");
    complexHypo2.addActionListener(new AdapterUCCToALThread(uc));
    pedigreeCombo.addActionListener(new AdapterUCCToALThread(uc));
    pedigreeComboS.addActionListener(new AdapterUCCToALThread(uc));
  }
  public String getRmView() {
    return rm.getText();
  }
  public String getRmsView() {
    return rms.getText();
  }
  public String getNumRmsView() {
    return nRms.getText();
  }
  public String getRpView() {
    return rp.getText();
  }
  public String getRpsView() {
    return rps.getText();
  }
  public String getNumRpsView() {
    return nRps.getText();
  }
  public boolean isComplex() {
    return complexHypo.isSelected();
  }

  public void setComplexEnabled(boolean b)
  {
    complexHypo.setEnabled(b);
  }

  private class PedigreeComboListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      setRmRpText(pedigreeCombo, rm, rp);
    }
  }
  private class PedigreeComboListenerS implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      setRmRpText(pedigreeComboS, rms, rps);
    }
  }
  private void setRmRpText(JComboBox box, FloatTextField rm, FloatTextField rp) {
    int idx = box.getSelectedIndex();
    if (idx <= 0 || idx >= KinshipIBDComplexView.pedigrees.length) {
      rm.setEnabled(true);
      rp.setEnabled(true);
      return;
    }
    rm.setValue(relRm[idx]);
//    rm.setEnabled(false);
    rp.setValue(relRp[idx]);
//    rp.setEnabled(false);
  }
  //todo: this should not be here!
  public void setSimulationView(KinshipRatioSimViewV1 ksv) {
    ksv_ = ksv;
  }
  public KinshipRatioSimViewV1 getSimulationView() {
    return (ksv_);
  }
}
