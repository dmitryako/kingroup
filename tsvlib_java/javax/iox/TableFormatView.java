package javax.iox;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;
import java.awt.*;

/**
 * Copyright www.DmitryKonovalov.org
 * User: jc138691, Date: 6/03/2007, Time: 13:38:24
 */
public class TableFormatView  extends GridBagView {
  private static final char tab = '\t';
  private static final char semi = ';';
  private static final char comma = ',';
  private static int FIELD_SIZE = 2;
//  public static String REFERENCE = "Goodnight KF & Queller DC, Molecular Ecology (1999) 8, 1231-1234";
  private JRadioButton colDelimTab;
  private JRadioButton colDelimSemi;
  private JRadioButton colDelimComma;

  private IntField firstRow;
  private JLabel firstRowLbl;
  private IntField firstCol;
  private JLabel firstColLbl;

  private IntField lastRow;
  private JLabel lastRowLbl;
  private IntField lastCol;
  private JLabel lastColLbl;

  private IntField headerRow;
  private JLabel headerRowLbl;
  private IntField headerCol;
  private JLabel headerColLbl;

  public TableFormatView(TableFormat model) {
    init();
    loadFrom(model);
    assemble();
  }
  protected TableFormatView() {
  }
  protected void assemble() {
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
    startRow(makeDelimetersPanel());
    startRow(makeHeadersPanel());
    startRow(makeDataStartPanel());
    endRow(makeDataEndPanel());
  }
  protected void init() {
    colDelimTab = new JRadioButton("Tab", false);
    colDelimSemi = new JRadioButton("Semicolon (;)", false);
    colDelimComma = new JRadioButton("Comma (,)", false);
    colDelimComma.setToolTipText("CSV-format");
    ButtonGroup delimGroup = new ButtonGroup();
    delimGroup.add(colDelimTab);
    delimGroup.add(colDelimSemi);
    delimGroup.add(colDelimComma);
    colDelimComma.setSelected(true); //default

    firstRowLbl = new JLabel("row #");
    firstRow = new IntField(FIELD_SIZE, 1, 10);
    firstColLbl = new JLabel("column #");
    firstCol = new IntField(FIELD_SIZE, 1, 10);

    lastRowLbl = new JLabel("row #");
    lastRow = new IntField(FIELD_SIZE, 0, 100);
    lastRow.setToolTipText("all available if 0");
    lastColLbl = new JLabel("column #");
    lastCol = new IntField(FIELD_SIZE, 0, 100);
    lastCol.setToolTipText("all available if 0");

    headerRowLbl = new JLabel("row #");
    headerRow = new IntField(FIELD_SIZE, 0, 10);
    headerRow.setToolTipText("ignored if 0");
    headerColLbl = new JLabel("column #");
    headerCol = new IntField(FIELD_SIZE, 0, 10);
    headerCol.setToolTipText("ignored if 0");

    setBorder(BorderFactory.createLoweredBevelBorder());
  }
  private JPanel makeDelimetersPanel() {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Column Delimiter");
    panel.setBorder(titled);
    panel.endRow(colDelimTab);
    panel.endRow(colDelimSemi);
    panel.endRow(colDelimComma);
    return panel;
  }
  private JPanel makeHeadersPanel() {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Headers in");
    panel.setBorder(titled);
    panel.startRow(headerRowLbl);
    panel.endRow(headerRow);
    panel.startRow(headerColLbl);
    panel.endRow(headerCol);
    return panel;
  }
  private JPanel makeDataStartPanel() {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Data start");
    panel.setBorder(titled);
    panel.startRow(firstRowLbl);
    panel.endRow(firstRow);
    panel.startRow(firstColLbl);
    panel.endRow(firstCol);
    return panel;
  }
  private JPanel makeDataEndPanel() {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Data end");
    panel.setBorder(titled);
    panel.startRow(lastRowLbl);
    panel.endRow(lastRow);
    panel.startRow(lastColLbl);
    panel.endRow(lastCol);
    return panel;
  }
  public void loadFrom(TableFormat model) {
    firstRow.setValue(model.getFirstRow());
    firstCol.setValue(model.getFirstCol());
    lastRow.setValue(model.getLastRow());
    lastCol.setValue(model.getLastCol());
    headerRow.setValue(model.getHeaderRow());
    headerCol.setValue(model.getHeaderCol());
    colDelimTab.setSelected(model.getColumnDelim() == tab);
    colDelimSemi.setSelected(model.getColumnDelim() == semi);
    colDelimComma.setSelected(model.getColumnDelim() == comma);
  }
//  private void adjust(JTextComponent text, AbstractButton button) {
//    text.setEnabled(button.isSelected());
//    text.setEditable(button.isSelected());
//  }
  public void loadTo(TableFormat model) {
    loadColumnDelim(model);
    model.setFirstRow(firstRow.getInput());
    model.setFirstCol(firstCol.getInput());
    model.setLastRow(lastRow.getInput());
    model.setLastCol(lastCol.getInput());
    model.setHeaderRow(headerRow.getInput());
    model.setHeaderCol(headerCol.getInput());
  }
  private void loadColumnDelim(TableFormat model) {
    char savedDelim = model.getColumnDelim();

    model.setColumnDelim(tab);
    if (colDelimComma.isSelected())
      model.setColumnDelim(comma);
    else if (colDelimSemi.isSelected())
      model.setColumnDelim(semi);
  }

  public void setLastColEnabled(boolean b)
  {
    lastCol.setEnabled(b);
  }
}

