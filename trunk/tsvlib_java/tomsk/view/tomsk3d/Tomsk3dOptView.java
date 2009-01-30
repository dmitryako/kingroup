package tomsk.view.tomsk3d;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/05/2007, 15:16:04
 */
public class Tomsk3dOptView extends GridBagView
{
  private final static ProjectLogger log = ProjectLogger.getLogger(Tomsk3dOptView.class);

  private static int N_SEC_FIELD_SIZE = 5;
  private IntTextField nSec;
  private JLabel nSecLbl;
  private JCheckBox rotOn;

  private JComboBox bgrdCombo;

  public Tomsk3dOptView(Tomsk3dModel model)
  {
    init(model);
    loadFrom(model);
    assemble();
  }

  private void init(Tomsk3dModel model)
  {
    rotOn = new JCheckBox("continuous");

    String help = "number of seconds it takes to complete one rotation";
    nSec = new IntTextField(N_SEC_FIELD_SIZE, model.getMinRotationSecs(), model.getMaxRotationSecs());
    nSec.setToolTipText(help);

    nSecLbl = new JLabel("seconds");
    nSecLbl.setToolTipText(help);

    bgrdCombo = new JComboBox(model.getBackgroundNames());
    bgrdCombo.setToolTipText("Select backgrounds");
  }

  public void loadTo(Tomsk3dModel model)
  {
    model.setEnableRotation(rotOn.isSelected());
    model.setNumRotationSecs(nSec.getInput());
    model.setBackgroundIdx(bgrdCombo.getSelectedIndex());
  }
  public void loadFrom(Tomsk3dModel model)
  {
    nSec.setValue(model.getNumRotationSecs());
    rotOn.setSelected(model.getEnableRotation());

    bgrdCombo.setSelectedIndex(model.getBackgroundIdx());
  }

  private void assemble()
  {
    endRow(makeBackground());
    endRow(makeRotation());
  }
  private JPanel makeRotation() {
    GridBagView panel = new GridBagView();
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Rotation");
    panel.setBorder(titled);

    panel.endRow(rotOn);
    panel.startRow(nSec);
    panel.endRow(nSecLbl);
    return panel;
  }
  private JPanel makeBackground() {
    GridBagView panel = new GridBagView();
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Background");
    panel.setBorder(titled);

    panel.endRow(bgrdCombo);
    return panel;
  }

}
