package kingroup_v2.like.milligan.view;
import kingroup_v2.like.milligan.MilliganIBD;
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
 * User: jc138691, Date: 23/03/2006, Time: 12:49:54
 */
public class MilliganIBDView
  extends GridBagView {
//    private static ProjectLogger log = ProjectLogger.getLogger(KinshipIBDView.class.getName());
  private static int FIELD_LEN = 3;
  private FloatTextField[] arr;

  private static String pedigrees[] = {"Manual Input"
    , "[U] Unrelated"
    , "[FS] Full siblings"
    , "[PO] Parent-offspring"
    , "[HS] Half siblings; grandchild-g.parent"
    , "[C] Cousins; Nephew-Uncle"
  };
  private static String codes[] = {""
    , "U"
    , "FS"
    , "PO"
    , "HS"
    , "C"
  };
  private static double kArr[][] = {
    {0, 0, 0, 0, 0, 0, 1, 0, 0},
    {0, 0, 0, 0, 0, 0, 1, 0, 0},
    {0, 0, 0, 0, 0, 0, 0.25f, 0.5f, 0.25f},
    {0, 0, 0, 0, 0, 0, 0, 1f, 0},
    {0, 0, 0, 0, 0, 0, 0.5f, 0.5f, 0},
    {0, 0, 0, 0, 0, 0, 0.75f, 0.25f, 0},
  };
  private JComboBox pedigreeCombo = new JComboBox(pedigrees); // relationship choices

  public MilliganIBDView(MilliganIBD model)
  {
    init();
    loadFrom(model);
    assemble();
  }

  private void init()
  {
    arr = new FloatTextField[MilliganIBD.SIZE];
    for (int i = 0; i < arr.length; i++)
      arr[i] = new FloatTextField(FIELD_LEN, 0f, 1f);

    pedigreeCombo.addActionListener(new PedigreeComboListener());
  }

  private void assemble() {
    removeAll();
    endRow(pedigreeCombo);

    final int NOT_K_SIZE = 6;
    for (int i = 0; i < NOT_K_SIZE; i++)
      startRow(new JLabel("s" + (i+1)));
    endRow(new JLabel());

    for (int i = 0; i < NOT_K_SIZE; i++)
      startRow(arr[i]);
    endRow(new JLabel());

    startRow(new JLabel("s7,k2="));
    startRow(arr[MilliganIBD.K_2]);

    startRow(new JLabel("s8,k1="));
    startRow(arr[MilliganIBD.K_1]);

    startRow(new JLabel("s9,k0="));
    startRow(arr[MilliganIBD.K_0]);

    endRow(new JLabel());
  }

  private void loadFrom(MilliganIBD model) {
    for (int i = 0; i < arr.length; i++) {
      arr[i].setValue((float)model.get(i));
    }
    loadPedigreeFrom(model);
  }
  public void loadTo(MilliganIBD model) {
    for (int i = 0; i < arr.length; i++) {
      model.set(i, arr[i].getInput());
    }
  }
  private void loadPedigreeFrom(MilliganIBD model) {
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

}

