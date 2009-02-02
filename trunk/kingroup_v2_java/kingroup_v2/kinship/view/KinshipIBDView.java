package kingroup_v2.kinship.view;
import kingroup_v2.kinship.KinshipIBD;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.FloatTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 15:37:52
 */
public class KinshipIBDView  extends GridBagView {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipIBDView.class.getName());
  private static int FIELD_SIZE = 4;
  private FloatTextField rm;
  private FloatTextField rp;

  private static String pedigrees[] = {"Manual Input"
    , "[U] Unrelated"
    , "[FS] Full siblings"
    , "[PO] Parent-offspring"
    , "[HS] Half siblings"
    , "[C] Cousins"
    , "[HFS] Haplodiploid Full sibs"
    , "[HC] Haplodiploid Cousins"
  };
  private static String codes[] = {""
    , "U"
    , "FS"
    , "PO"
    , "HS"
    , "C"
    , "HFS"
    , "HC"
  };
  private static float relRm[] = {0, 0, 0.5f, 1f, 0.5f, 0.25f, 0.5f, 0.375f};
  private static float relRp[] = {0, 0, 0.5f, 0,  0,    0,     1.0f, 0};
  private JComboBox pedigreeCombo = new JComboBox(pedigrees); // relationship choices
  public KinshipIBDView(KinshipIBD model)
  {
    init();
    loadFrom(model);
    assemble();
  }

  private void init()
  {
    rm = new FloatTextField(FIELD_SIZE, 0f, 1f);
    rp = new FloatTextField(FIELD_SIZE, 0f, 1f);
    pedigreeCombo.addActionListener(new PedigreeComboListener());
  }

  private void assemble() {
    removeAll();
    endRow(pedigreeCombo);
    startRow(new JLabel("Rm"));
    startRow(rm);
    startRow(new JLabel(""));
    startRow(new JLabel("Rp"));
    startRow(rp);
    endRow(new JLabel(""));

    rm.setToolTipText("Rm [0; 1]");
    rp.setToolTipText("Rp [0; 1]");
  }

  private void loadFrom(KinshipIBD model) {
    rm.setValue((float)model.getRm());
    rp.setValue((float)model.getRp());
    loadPedigreeFrom(model);
  }
  public void loadTo(KinshipIBD model) {
    model.setRm(rm.getInput());
    model.setRp(rp.getInput());
  }
  private void loadPedigreeFrom(KinshipIBD model) {
    float rm = (float)model.getRm();
    float rp = (float)model.getRp();
    int idx = matchPedigreeFrom(rm, rp);
    pedigreeCombo.setSelectedIndex(idx);
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
    pedigreeCombo.addActionListener(new AdapterUCCToALThread(uc));
  }
  public String getRmView() {
    return rm.getText();
  }
  public String getRpView() {
    return rp.getText();
  }

  public String getKinCode()
  {
    int idx = pedigreeCombo.getSelectedIndex();
    return codes[idx];
  }

  private class PedigreeComboListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      setText(pedigreeCombo);
    }
  }
  private void setText(JComboBox box) {
    int idx = box.getSelectedIndex();
    if (idx <= 0 || idx >= pedigrees.length) {
      rm.setEnabled(true);
      rp.setEnabled(true);
      return;
    }
//    NumberFormat nf = NumberFormat.getNumberInstance();

//    rm.setText(Double.toString(relRm[idx]));
    rm.setValue(relRm[idx]);
    rm.setEnabled(false);
//    rp.setText(Double.toString(relRp[idx]));
    rp.setValue(relRp[idx]);
    rp.setEnabled(false);
  }
  public String toString() {
    return "Rm=" + getRmView() + "  Rp=" + getRpView();
  }
}


