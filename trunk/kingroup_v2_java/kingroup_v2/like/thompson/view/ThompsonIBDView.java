package kingroup_v2.like.thompson.view;
import kingroup_v2.like.thompson.ThompsonIBD;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.FloatTextField;
import javax.utilx.arrays.vec.Vec;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 11:00:42
 */
public class ThompsonIBDView
  extends GridBagView {
//    private static ProjectLogger log = ProjectLogger.getLogger(KinshipIBDView.class.getName());
  private static int FIELD_LEN = 4;
  private FloatTextField[] arr;

  private static String pedigrees[] = {"Manual Input"
    , "[U] Unrelated"
    , "[FS] Full-siblings"
    , "[PO] Parent-offspring"
    , "[HS] Half-siblings"
    , "[C] Cousins; Nephew-Uncle"
    , "[T] Self; identical twin"
  };
  private static String codes[] = {""
    , "U"
    , "FS"
    , "PO"
    , "HS"
    , "C"
    , "T"
  };
  private static double kArr[][] = {
    {1, 0, 0},
    {1, 0, 0},
    {0.25, 0.5, 0.25},
    {0, 1, 0},
    {0.5, 0.5, 0},
    {0.75, 0.25, 0},
    {0, 0, 1},
  };
  private JComboBox pedigreeCombo = new JComboBox(pedigrees); // relationship choices

  public ThompsonIBDView(ThompsonIBD model)
  {
    init();
    loadFrom(model);
    assemble();
  }

  private void init()
  {
    arr = new FloatTextField[ThompsonIBD.SIZE];
    for (int i = 0; i < arr.length; i++)
      arr[i] = new FloatTextField(FIELD_LEN, 0f, 1f);

    pedigreeCombo.addActionListener(new PedigreeComboListener());
  }

  private void assemble() {
    removeAll();
    endRow(pedigreeCombo);

    for (int i = 0; i < ThompsonIBD.SIZE; i++)
      startRow(new JLabel("k" + i));
    endRow(new JLabel());

    for (int i = 0; i < ThompsonIBD.SIZE; i++)
      startRow(arr[i]);
    endRow(new JLabel());
  }

  private void loadFrom(ThompsonIBD model) {
    for (int i = 0; i < arr.length; i++) {
      arr[i].setValue((float)model.get(i));
    }
    loadPedigreeFrom(model);
  }
  public void loadTo(ThompsonIBD model) {
    for (int i = 0; i < arr.length; i++) {
      model.set(i, arr[i].getInput());
    }
  }
  private void loadPedigreeFrom(ThompsonIBD model) {
    int idx = matchPedigreeFrom(model.getArr());
    pedigreeCombo.setSelectedIndex(idx);
  }
  private int matchPedigreeFrom(double[] a) {
    int idx = 0;// NOTE: 0 is for manual input
    for (int i = 1; i < kArr.length; i++) // NOTE: 0 is for manual input
      if (Vec.floatEquals(kArr[i], a)) {
        idx = i;
        break;
      }
    if (idx < 0 && idx >= pedigrees.length)
      idx = 0;
    return idx;
  }
  public void onIBDChange(UCController uc) {
//    log.info("called ");
    pedigreeCombo.addActionListener(new AdapterUCCToALThread(uc));
  }
  public String getView(int i) {
    return arr[i].getText();
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
      for (FloatTextField f : arr)
        f.setEnabled(true);
      return;
    }
    for (int i = 0; i < arr.length; i++) {
      arr[i].setValue((float)kArr[idx][i]);
      arr[i].setEnabled(false);
    }
  }

  public String toString() {
    String mssg = "";
    for (int i = 0; i < arr.length; i++) {
      mssg += getView(i);
      if (i != arr.length-1)
        mssg += "  ";
    }
    return mssg;
  }
}
