package javax.iox.table;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;
import javax.textx.FractionDigitsView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/11/2007, Time: 15:10:26
 */
public class TableDisplayOptView  extends GridBagView {
  private final static ProjectLogger log = ProjectLogger.getLogger(TableDisplayOptView.class);

  private static int N_VARS_FIELD_SIZE = 4;

  private IntTextField maxNRows;
  private JLabel maxNRowsLbl;
  private IntTextField maxNCols;
  private JLabel maxNColsLbl;

  protected FractionDigitsView digitsView;
  private JCheckBox countOn;

  public TableDisplayOptView(TableDisplayOpt model) {
    super("Table Display Options");
    init();
    loadFrom(model);
    assemble();
  }
  private void init() {
    maxNRowsLbl = new JLabel("rows");
    maxNRows = new IntTextField(N_VARS_FIELD_SIZE, 1
      , TableDisplayOpt.DEFAULT_MAX_NUM_ROWS);
    maxNRows.setToolTipText("Number of rows");

    maxNColsLbl = new JLabel("columns");
    maxNCols = new IntTextField(N_VARS_FIELD_SIZE, 1
      , TableDisplayOpt.DEFAULT_MAX_NUM_COLS);
    maxNCols.setToolTipText("Number of columns");

    countOn = new JCheckBox("count");
    countOn.setToolTipText("row and column count");
  }
  public void loadTo(TableDisplayOpt model) {
    digitsView.loadTo(model.getDigitsModel());
    model.setMaxNumRows(maxNRows.getInput());
    model.setMaxNumCols(maxNCols.getInput());
    model.setCountOn(countOn.isSelected());
  }
  public void loadFrom(TableDisplayOpt model) {
    digitsView = new FractionDigitsView(model.getDigitsModel());
    maxNRows.setValue(model.getMaxNumRows());
    maxNCols.setValue(model.getMaxNumCols());
    countOn.setSelected(model.getCountOn());
  }
  private void assemble() {
    endRow(countOn);
    endRow(digitsView);
    startRow(maxNRows);     endRow(maxNRowsLbl);
    startRow(maxNCols);     endRow(maxNColsLbl);
  }
//  public void setRunOnDigitChange(UCController uc) {
//    digitsView.setRunOnChange(uc);
//  }
}

