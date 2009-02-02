package kingroup_v2.cervus.tools;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/01/2006, Time: 10:28:57
 */
public class CervusParentageFormatView extends GridBagView {
  private static int FIELD_SIZE = 2;

  private JLabel idBox;
  private IntTextField idCol;

  private JLabel parentBox;
  private IntTextField parentCol;

  private JLabel nProgeniesLbl;
  private IntTextField nProgenies;

  public CervusParentageFormatView(CervusParentageFileFormat model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void assemble() {
    JPanel optionPanel = makeOptionPanel();
    endRow(optionPanel);
  }
  private void init() {
    nProgeniesLbl = new JLabel("Number of progenies ");
    nProgenies = new IntTextField(FIELD_SIZE, 1, 1000);

    idBox = new JLabel("Column of individual ID ");
    idCol = new IntTextField(FIELD_SIZE, 1, 100);

    parentBox = new JLabel("Column of parental ID ");
    parentCol = new IntTextField(FIELD_SIZE, 1, 100);
  }
  private JPanel makeOptionPanel() {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Cervus File Format");
    panel.setBorder(titled);

    panel.startRow(nProgeniesLbl);
    panel.endRow(nProgenies);

    panel.startRow(idBox);
    panel.endRow(idCol);

    panel.startRow(parentBox);
    panel.endRow(parentCol);

    panel.endRow(new JLabel("Assumed: CSV-format, ignore first line."));
    return panel;
  }
  private void loadFrom(CervusParentageFileFormat model) {
    nProgenies.setValue(model.getNumProgenies());
    idCol.setValue(model.getIdColumn());
    parentCol.setValue(model.getParentColumn());
  }
  public void loadTo(CervusParentageFileFormat model) {
    model.setNumProgenies(nProgenies.getInput());
    model.setIdColumn(idCol.getInput());
    model.setParentColumn(parentCol.getInput());
  }
}
