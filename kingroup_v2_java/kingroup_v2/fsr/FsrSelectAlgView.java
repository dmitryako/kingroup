package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.partition.dr.DRAlg;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.smc.MCSAlg;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 07:44:00
 */
public class FsrSelectAlgView extends GridBagView {
  private JRadioButton sdr;
  private JRadioButton ms2;
  private JRadioButton ms;
  private JRadioButton dr;
  private JRadioButton mcs;
  private JRadioButton simps;
  public FsrSelectAlgView(Kingroup bean, String mssg) {
    init();
    loadFrom(bean);
    assemble(mssg);
  }
  public void loadFrom(Kingroup bean) {
    int format = bean.getFsrAlg();
    simps.setSelected(true); // default
    mcs.setSelected(format == bean.FSR_MCS);
    sdr.setSelected(format == bean.FSR_SDR);
    ms2.setSelected(format == bean.FSR_MS2);
    ms.setSelected(format == bean.FSR_MS);
    dr.setSelected(format == bean.FSR_DR);
    simps.setSelected(format == bean.FSR_SIMPS);
  }
  public void loadTo(Kingroup bean) {
    if (sdr.isSelected())
      bean.setFsrAlg(bean.FSR_SDR);
    else if (ms2.isSelected())
      bean.setFsrAlg(bean.FSR_MS2);
    else if (ms.isSelected())
      bean.setFsrAlg(bean.FSR_MS);
    else if (dr.isSelected())
      bean.setFsrAlg(bean.FSR_DR);
    else if (simps.isSelected())
      bean.setFsrAlg(bean.FSR_SIMPS);
    else if (mcs.isSelected())
      bean.setFsrAlg(bean.FSR_MCS);
    else
      bean.setFsrAlg(bean.FSR_SIMPS);
  }
  private void init() {
    mcs = new JRadioButton("Markov chain SIMPSON (MCS)", true);
    mcs.setToolTipText(MCSAlg.REFERENCE);

    simps = new JRadioButton("SIMPSON", false);
    simps.setToolTipText(SIMPS2Alg.REFERENCE);

    ms = new JRadioButton("Modified SIMPSON (MS)", false);
    ms.setToolTipText(MSAlgV2.REFERENCE);

    dr = new JRadioButton("Descending Ratio (DR)", false);
    dr.setToolTipText(DRAlg.REFERENCE);

    sdr = new JRadioButton("SIMPSON-assisted Descending Ratio (SDR)", false);
//    sdr.setToolTipText(SDRAlg.REFERENCE);
    sdr.setToolTipText(MS2AlgV2.REFERENCE);

    ms2 = new JRadioButton("Modified SIMPSON O(n^2) (MS2)", false);
    ms2.setToolTipText(MS2AlgV2.REFERENCE);

    ButtonGroup group = new ButtonGroup();
    group.add(dr);
    group.add(simps);
    group.add(ms);
    group.add(sdr);
    group.add(ms2);
    group.add(mcs);
  }
  private void assemble(String mssg) {
    if (mssg != null  && mssg.length() != 0)
      endRow(new JLabel(mssg));
    endRow(assembleFormat());
  }
  private JPanel assembleFormat() {
    GridBagView panel = new GridBagView("FSR Algorithms");

    GridBagView tmpPanel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Robust to genotyping errors");
    tmpPanel.setBorder(titled);
    tmpPanel.endRow(dr);
    tmpPanel.endRow(new JLabel("More accurate than DR at few loci:"));
    tmpPanel.endRow(sdr);
    panel.endRow(tmpPanel);

    tmpPanel = new GridBagView();
    etched = BorderFactory.createEtchedBorder();
    titled = BorderFactory.createTitledBorder(etched, "Not robust to genotyping errors");
    tmpPanel.setBorder(titled);
    tmpPanel.endRow(simps);
    tmpPanel.endRow(ms);
    tmpPanel.endRow(new JLabel("For a large number of individuals (n>1000):"));
    tmpPanel.endRow(ms2);
    panel.endRow(tmpPanel);

    tmpPanel = new GridBagView();
    etched = BorderFactory.createEtchedBorder();
    titled = BorderFactory.createTitledBorder(etched, "Work in progress");
    tmpPanel.setBorder(titled);
    tmpPanel.endRow(mcs);
    panel.endRow(tmpPanel);
    return panel;
  }
}
