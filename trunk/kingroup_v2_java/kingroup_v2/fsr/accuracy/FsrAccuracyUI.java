package kingroup_v2.fsr.accuracy;
import kingroup_v2.partition.dr.DRAlgWithOldPop;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.ms2.MS2Alg;
import kingroup_v2.partition.sdr.SDRAlgV1;
import kingroup_v2.partition.simpson.SIMPS2Alg;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/11/2005, Time: 09:38:06
 */
public class FsrAccuracyUI extends JPanel {
  private JTabbedPaneX ui;

  private FsrAccuracyView simpsView;
  private int simpsIdx = -1;
  private JRadioButton simpsFocus;

  private FsrAccuracyView msView;
  private int msIdx = -1;
  private JRadioButton msFocus;

  private FsrAccuracyView ms2View;
  private int ms2Idx = -1;
  private JRadioButton ms2Focus;

  private FsrAccuracyView drView;
  private int drIdx = -1;
  private JRadioButton drFocus;

  private FsrAccuracyView sdrView;
  private int sdrIdx = -1;
  private JRadioButton sdrFocus;

  public FsrAccuracyUI() {
    init();
  }
  public void reset() {
    ui.removeAll();
    simpsView = null;
    simpsIdx = -1;
    msView = null;
    msIdx = -1;
    ms2View = null;
    ms2Idx = -1;
    drView = null;
    drIdx = -1;
    sdrView = null;
    sdrIdx = -1;
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    simpsFocus = new JRadioButton();
    msFocus = new JRadioButton();
    ms2Focus = new JRadioButton();
    drFocus = new JRadioButton();
    sdrFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(simpsFocus);
    group.add(msFocus);
    group.add(ms2Focus);
    group.add(drFocus);
    group.add(sdrFocus);
    ui = new JTabbedPaneX(JTabbedPane.TOP);
    add(ui);
  }
  private void rebuild() {
    simpsIdx = ui.processView(simpsView, simpsIdx, simpsFocus.isSelected()
      , "SIMPSON", SIMPS2Alg.REFERENCE);
    msIdx = ui.processView(msView, msIdx, msFocus.isSelected()
      , "MS", MSAlgV2.REFERENCE);
    ms2Idx = ui.processView(ms2View, ms2Idx, ms2Focus.isSelected()
      , "MS2", MS2Alg.REFERENCE);
    drIdx = ui.processView(drView, drIdx, drFocus.isSelected()
      , "DR", DRAlgWithOldPop.REFERENCE);
    sdrIdx = ui.processView(sdrView, sdrIdx, sdrFocus.isSelected()
      , "SDR", SDRAlgV1.REFERENCE);
    validate();
    repaint();
  }

  public void setSimpsAlgView(FsrAccuracyView view) {
    simpsView = view;
    simpsFocus.setSelected(true);
    rebuild();
  }
  public FsrAccuracyView getSimpsView() {
    return simpsView;
  }
  public void setMSAlgView(FsrAccuracyView view) {
    msView = view;
    msFocus.setSelected(true);
    rebuild();
  }
  public FsrAccuracyView getMSView() {
    return msView;
  }
  public void setMS2AlgView(FsrAccuracyView view) {
    ms2View = view;
    ms2Focus.setSelected(true);
    rebuild();
  }
  public FsrAccuracyView getMS2View() {
    return ms2View;
  }
  public void setDRAlgView(FsrAccuracyView view) {
    drView = view;
    drFocus.setSelected(true);
    rebuild();
  }
  public FsrAccuracyView getDRView() {
    return drView;
  }
  public void setSDRAlgView(FsrAccuracyView view) {
    sdrView = view;
    sdrFocus.setSelected(true);
    rebuild();
  }
  public FsrAccuracyView getSDRView() {
    return sdrView;
  }
}

