package kingroup_v2.ucm.import_sample;
import kingroup_v2.Kingroup;
import kingroup_v2.cervus.Cervus;
import kingroup_v2.kinship.Kinship;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/09/2005, Time: 15:59:10
 */
public class SelectImportFormatView extends GridBagView {
  private JRadioButton kinshipFormat;
  private JRadioButton cervusFormat;
  public SelectImportFormatView(Kingroup bean) {
    init();
    loadFrom(bean);
    assemble();
  }
  public void loadFrom(Kingroup bean) {
    int format = bean.getFileType();
    kinshipFormat.setSelected(true);// default
    kinshipFormat.setSelected(format == bean.KINSHIP_FILE);
    cervusFormat.setSelected(format == bean.CERVUS_FILE);
  }
  public void loadTo(Kingroup bean) {
    if (kinshipFormat.isSelected())
      bean.setFileType(bean.KINSHIP_FILE);
    else
      bean.setFileType(bean.CERVUS_FILE);
  }
  private void init() {
    kinshipFormat = new JRadioButton("KINSHIP", true);
    kinshipFormat.setToolTipText(Kinship.REFERENCE);

    cervusFormat = new JRadioButton("CERVUS", false);
    cervusFormat.setToolTipText(Cervus.REFERENCE);

    ButtonGroup group = new ButtonGroup();
    group.add(kinshipFormat);
    group.add(cervusFormat);
  }
  private void assemble() {
    endRow(assembleFormat());
  }
  private JPanel assembleFormat() {
    GridBagView panel = new GridBagView("Format");
    panel.endRow(kinshipFormat);
    panel.endRow(cervusFormat);
    return panel;
  }
}
