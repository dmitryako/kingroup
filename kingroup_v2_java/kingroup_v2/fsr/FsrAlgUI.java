package kingroup_v2.fsr;
import kingroup_v2.partition.dr.DRAlgWithOldPop;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.partition.sdr.SDRAlgV1;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.smc.MCSAlg;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 08:51:37
 */
public class FsrAlgUI extends JPanel {
  private JTabbedPaneX ui;

  private FsrAlgView simpsView;
  private int simpsIdx = -1;
  private JRadioButton simpsFocus;

  private FsrAlgView msView;
  private int msIdx = -1;
  private JRadioButton msFocus;

  private FsrAlgView mcsView;
  private int mcsIdx = -1;
  private JRadioButton mcsFocus;

  private FsrAlgView ms2View;
  private int ms2Idx = -1;
  private JRadioButton ms2Focus;

  private FsrAlgView drView;
  private int drIdx = -1;
  private JRadioButton drFocus;

  private FsrAlgView sdrView;
  private int sdrIdx = -1;
  private JRadioButton sdrFocus;

  public FsrAlgUI() {
    init();
  }
  public void reset() {
    ui.removeAll();
    simpsView = null;
    simpsIdx = -1;
    mcsView = null;
    mcsIdx = -1;
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
    mcsFocus = new JRadioButton();
    msFocus = new JRadioButton();
    ms2Focus = new JRadioButton();
    drFocus = new JRadioButton();
    sdrFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(simpsFocus);
    group.add(mcsFocus);
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
    mcsIdx = ui.processView(mcsView, mcsIdx, mcsFocus.isSelected()
      , "MCS", MCSAlg.REFERENCE);
    msIdx = ui.processView(msView, msIdx, msFocus.isSelected()
      , "MS", MSAlgV2.REFERENCE);
    ms2Idx = ui.processView(ms2View, ms2Idx, ms2Focus.isSelected()
      , "MS2", MS2AlgV2.REFERENCE);
    drIdx = ui.processView(drView, drIdx, drFocus.isSelected()
      , "DR", DRAlgWithOldPop.REFERENCE);
    sdrIdx = ui.processView(sdrView, sdrIdx, sdrFocus.isSelected()
      , "SDR", SDRAlgV1.REFERENCE);
    validate();
    repaint();
  }

  public void setSIMPSAlgView(FsrAlgView view) {
    simpsView = view;
    simpsFocus.setSelected(true);
    rebuild();
  }
  public FsrAlgView getSIMPSView() {
    return simpsView;
  }
  public void setMCSAlgView(FsrAlgView view) {
    mcsView = view;
    mcsFocus.setSelected(true);
    rebuild();
  }
  public FsrAlgView getMCSView() {
    return mcsView;
  }
  public void setMSAlgView(FsrAlgView view) {
    msView = view;
    msFocus.setSelected(true);
    rebuild();
  }
  public FsrAlgView getMSView() {
    return msView;
  }
  public void setMS2AlgView(FsrAlgView view) {
    ms2View = view;
    ms2Focus.setSelected(true);
    rebuild();
  }
  public FsrAlgView getMS2View() {
    return ms2View;
  }
  public void setDRAlgView(FsrAlgView view) {
    drView = view;
    drFocus.setSelected(true);
    rebuild();
  }
  public FsrAlgView getDRView() {
    return drView;
  }
  public void setSDRAlgView(FsrAlgView view) {
    sdrView = view;
    sdrFocus.setSelected(true);
    rebuild();
  }
  public FsrAlgView getSDRView() {
    return sdrView;
  }
}
