package kingroup_v2.cervus.view;

import kingroup_v2.cervus.CervusFileBean;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/01/2006, Time: 16:45:40
 */
public class CervusFileFormatView  extends GridBagView {
  private JRadioButton locusFirst;
  private JRadioButton dataFirst;
  private JRadioButton ignoreFirst;

  private static final char tab = '\t';
  private static final char semi = ';';
  private static final char comma = ',';
  private static int FIELD_SIZE = 2;
  private JRadioButton colDelimTab;
  private JRadioButton colDelimSemi;
  private JRadioButton colDelimComma;

  private JLabel idBox;
  private IntField idCol;

  private JLabel locusBox;
  private IntField locusCol;
  private JLabel nLociBox;
  private IntField nLoci;
  private JCheckBox freqNorm;
  private JRadioButton freqCalc;
  public CervusFileFormatView(CervusFileBean model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void assemble() {
    JPanel optionPanel = makeOptionPanel();
    JPanel colDelimPanel = makeDelimetersPanel();
    JPanel freqPanel = makeFreqPanel();
    GridBagView gridbag = new GridBagView();
    gridbag.startRow(colDelimPanel);
    gridbag.endRow(freqPanel);
    startRow(optionPanel);
    endRow(gridbag);
  }
  private void init() {
    colDelimTab = new JRadioButton("Tab", false);
    colDelimSemi = new JRadioButton("Semicolon (;)", false);
    colDelimComma = new JRadioButton("Comma (,)", false);
    colDelimComma.setToolTipText("CSV-format");
    ButtonGroup delimGroup = new ButtonGroup();
    delimGroup.add(colDelimTab);
    delimGroup.add(colDelimSemi);
    delimGroup.add(colDelimComma);
    colDelimComma.setSelected(true); //default

    idBox = new JLabel("Column of individual ID ");
    idCol = new IntField(FIELD_SIZE, 1, 100);

    locusBox = new JLabel("First allele in column ");
    locusCol = new IntField(FIELD_SIZE, 1, 100);

    nLociBox = new JLabel("Number of loci ");
    nLoci = new IntField(FIELD_SIZE, 1, 100);

    freqCalc = new JRadioButton("calculate from file", false);
    freqCalc.setEnabled(true);

    freqNorm = new JCheckBox("Display normalized");
    freqNorm.setToolTipText("Frequencies are normalized to 1.0");

    ButtonGroup freqGroup = new ButtonGroup();
    freqGroup.add(freqCalc);
    freqCalc.setSelected(true); // default
    setBorder(BorderFactory.createLoweredBevelBorder());

    locusFirst = new JRadioButton("Locus names on first line", true);
    dataFirst = new JRadioButton("Data start on first line", false);
    ignoreFirst = new JRadioButton("Ignore first line", false);
    ButtonGroup group = new ButtonGroup();
    group.add(locusFirst);
    group.add(dataFirst);
    group.add(ignoreFirst);
    locusFirst.setSelected(true);
  }
  private JPanel makeOptionPanel() {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Cervus File Format");
    panel.setBorder(titled);

    panel.endRow(locusFirst);
    panel.endRow(dataFirst);
    panel.endRow(ignoreFirst);

    panel.startRow(idBox);
    panel.endRow(idCol);

    panel.startRow(locusBox);
    panel.endRow(locusCol);

    panel.startRow(nLociBox);
    panel.endRow(nLoci);

    panel.endRow(new JLabel("Missing data identifies: ?, * or 0(zero)"));
    return panel;
  }
  private JPanel makeFreqPanel() {
    // Allele Frequencies
    JPanel panel = new JPanel(new GridLayout(4, 1)); //int rows, int cols)
    panel.add(freqNorm);
    panel.add(freqCalc);
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Allele Frequencies");
    panel.setBorder(titled);
    return panel;
  }
  private JPanel makeDelimetersPanel() {
    GridBagView colDelims = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Delimiters");
    colDelims.setBorder(titled);
    colDelims.endRow(colDelimTab);
    colDelims.endRow(colDelimSemi);
    colDelims.endRow(colDelimComma);
    return colDelims;
  }
  private void loadFrom(CervusFileBean model) {
    locusFirst.setSelected(model.getFirstLine() == model.FIRST_LINE_LOCUS_NAMES);
    dataFirst.setSelected(model.getFirstLine() == model.FIRST_LINE_DATA);
    ignoreFirst.setSelected(model.getFirstLine() == model.FIRST_LINE_IGNORE);

    colDelimTab.setSelected(model.getColumnDelim() == tab);
    colDelimSemi.setSelected(model.getColumnDelim() == semi);
    colDelimComma.setSelected(model.getColumnDelim() == comma);

    freqNorm.setSelected(model.getFreqUserNorm());
    freqCalc.setSelected(true);
    idCol.setValue(model.getIdColumn());
    locusCol.setValue(model.getFirstLocusColumn());
    nLoci.setValue(model.getNumLoci());
  }
  public void loadTo(CervusFileBean model) {
    model.setFirstLine(getFirstLine());

    loadColumnDelim(model);
    model.setFreqUserNorm(freqNorm.isSelected());
    model.setFreqSource(CervusFileBean.FREQ_SOURCE_CALC);
    model.setHasId(true);
    model.setIdColumn(idCol.getInput());
    model.setFirstLocusColumn(locusCol.getInput());
    model.setNumLoci(nLoci.getInput());
  }
  private int getFirstLine() {
    if (locusFirst.isSelected())
      return CervusFileBean.FIRST_LINE_LOCUS_NAMES;
    if (dataFirst.isSelected())
      return CervusFileBean.FIRST_LINE_DATA;
    return CervusFileBean.FIRST_LINE_IGNORE;
  }
  private void loadColumnDelim(CervusFileBean bean) {
    bean.setColumnDelim(tab);
    if (colDelimComma.isSelected())
      bean.setColumnDelim(comma);
    else if (colDelimSemi.isSelected())
      bean.setColumnDelim(semi);
  }
}
