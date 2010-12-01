package kingroup_v2.kinship.view;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipFileFormat;
import kingroup_v2.relatedness.qg.QGRelatedness;
import pattern.ucm.AdapterUCCToALThread;
import pattern.ucm.UCShowHelpMssg;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/09/2005, Time: 12:21:40
 */
public class KinshipFileFormatView extends GridBagView {
  private static final char tab = '\t';
  private static final char semi = ';';
  private static final char comma = ',';
  private static int FIELD_SIZE = 2;
  public static String REFERENCE = "Goodnight KF & Queller DC, Molecular Ecology (1999) 8, 1231-1234";
  private JRadioButton colDelimTab;
  private JRadioButton colDelimSemi;
  private JRadioButton colDelimComma;
  private JTextField alleleDelim;
  private JTextField comment;
  private JCheckBox groupIdBox;
  private IntField groupIdCol;
  private JCheckBox idBox;
  private IntField idCol;
  private JCheckBox matIdBox;
  private IntField matIdCol;
  private JCheckBox patIdBox;
  private IntField patIdCol;
  private IntField locusCol;
  private IntField nLoci;

  private JCheckBox freqNorm;
  private JRadioButton freqFile;
  private JRadioButton freqCalc;
  private JRadioButton freqBias;
  private JTextField freqEnd;
  public KinshipFileFormatView(KinshipFileFormat model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void assemble() {
    startRow(makeOptionPanel());
    GridBagView gridbag = new GridBagView();
    gridbag.startRow(makeDelimetersPanel());
    gridbag.endRow(makeFreqPanel());
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
    alleleDelim = new JTextField(2);
    comment = new JTextField(2);

    groupIdBox = new JCheckBox("Column of group ID:");
    groupIdCol = new IntField(FIELD_SIZE, 1, 100);

    idBox = new JCheckBox("Column of individual ID:");
    idCol = new IntField(FIELD_SIZE, 1, 100);

    matIdBox = new JCheckBox("Column of maternal ID:");
    matIdCol = new IntField(FIELD_SIZE, 1, 100);

    patIdBox = new JCheckBox("Column of paternal ID:");
    patIdCol = new IntField(FIELD_SIZE, 1, 100);

    locusCol = new IntField(FIELD_SIZE, 1, 100);
    nLoci = new IntField(FIELD_SIZE, 1, 1000);

    freqNorm = new JCheckBox("Display normalized");
    freqNorm.setToolTipText("Frequencies are normalized to 1.0");
    freqFile = new JRadioButton("Import from file", false);
    freqFile.setToolTipText("Frequencies are included in file");
    freqCalc = new JRadioButton("Calculate from file", false);
    freqBias = new JRadioButton("Bias correction", false);
    freqBias.setToolTipText("A group does not contribute to its own allele frequencies, see "
      + QGRelatedness.REFERENCE);
    freqEnd = new JTextField(3);
    ButtonGroup freqGroup = new ButtonGroup();
    freqGroup.add(freqFile);
    freqGroup.add(freqCalc);
    freqGroup.add(freqBias);
    freqFile.setSelected(true); // default
    setBorder(BorderFactory.createLoweredBevelBorder());
  }
  private JPanel makeOptionPanel() {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Kinship File Format");
    panel.setBorder(titled);

    JCheckBox box = new JCheckBox("Column of first locus:");
    box.setSelected(true);
    box.setEnabled(false);
    box.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ((JCheckBox) e.getSource()).setSelected(true);
      }
    });
    panel.startRow(box);
    panel.endRow(locusCol);

    box = new JCheckBox("Number of loci:");
    box.setSelected(true);
    box.setEnabled(false);
    box.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ((JCheckBox) e.getSource()).setSelected(true);
      }
    });
    panel.startRow(box);
    panel.endRow(nLoci);

    JButton bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      "Check boxes indicate optional variables.\n"
        + "Check the box to indicate the variable is\n "
        + "present in the data set.\n"
      , KingroupFrame.getInstance())));
    panel.startRow(groupIdBox);
    panel.startRow(bttn);
    panel.endRow(groupIdCol);

    panel.startRow(idBox);
    panel.endRow(idCol);

    panel.startRow(matIdBox);
    panel.endRow(matIdCol);

    panel.startRow(patIdBox);
    panel.endRow(patIdCol);

    box = new JCheckBox("Comment line marker:");
    box.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ((JCheckBox) e.getSource()).setSelected(true);
      }
    });
    String text = "Lines beginning with the ";
    text += ("\"" + comment.getText() + "\" marker are ignored.");
    box.setToolTipText(text);
    box.setSelected(true);
    box.setEnabled(false);
    panel.startRow(box);
    panel.endRow(comment);

    box = new JCheckBox("Frequency block marker:");
    box.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ((JCheckBox) e.getSource()).setSelected(true);
      }
    });
    text = "If present, the getAllele frequency block must come first and be ended by the ";
    text += ("\"" + freqEnd.getText() + "\" marker.");
    box.setToolTipText(text);
    box.setSelected(true);
    box.setEnabled(false);

    panel.startRow(box);
    panel.endRow(freqEnd);

    return panel;
  }
  private JPanel makeFreqPanel() {
    // Allele Frequencies
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Allele Frequencies");
    panel.setBorder(titled);

    String basedOn = "Based on the KINSHIP manual of " + Kinship.REFERENCE + ":\n\n";
    String noFreqBlock = "If selected, an ALLELE FREQUENCY BLOCK must NOT be present in the data file.";

    JButton bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(basedOn
      + "Select, when an ALLELE FREQUENCY BLOCK is present in the data file.\n\n"
      + "ALLELE FREQUENCY BLOCK:\n"
      + "The first non-comment line of the block must be a list of locus names.\n"
      + "Each locus occupies two columns: the first for the names of the alleles\n"
      + "and the second for the frequency of that allele.\n"
      + "The block must come first, before GENOTYPE BLOCK.\n"
      + "The block must end by the word 'end' on a line by itself.\n"
      , KingroupFrame.getInstance())));
    panel.startRow(bttn);
    panel.endRow(freqFile);


    bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(basedOn
      + noFreqBlock
      + "\n\nAllele frequencies are calculated by counting each occurrence of an allele at its locus.\n"
      , KingroupFrame.getInstance())));
    panel.startRow(bttn);
    panel.endRow(freqCalc);

    bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(basedOn
      + "Bias correction calculation of " + QGRelatedness.REFERENCE + " is performed."
      + "\nEach group is assigned its own allele frequencies\n"
      + "by excluding all individuals of the group from background frequency calculations.\n\n"
      + noFreqBlock
      + "\n\nNOTE! \n"
      + " 1. These bias-corrected frequencies are for your information only.\n"
      + "    The frequencies will be saved in the RESULTS-file for possible further analysis.\n"
      + " 2. Finer control of the bias correction options is available elsewhere in KINGROUP.\n"
      + " 3. If an allele occurs only in one group, its bias-corrected frequency is ZERO."
      , KingroupFrame.getInstance())));
    panel.startRow(bttn);
    panel.endRow(freqBias);

    bttn = ProjectFrame.makeHelpButton();
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      "If ON, allele frequencies are DISPLAYED normalized to ONE at each locus.\n\n"
        + "If OFF, allele count is DISPLAYED.\n\n"
        + "Note! Either way, allele frequencies are normalized to ONE before any calculations are done."
      , KingroupFrame.getInstance())));
    panel.startRow(bttn);
    panel.endRow(freqNorm);

    return panel;
  }
  private JPanel makeDelimetersPanel() {
    GridBagView colDelims = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "Column");
    colDelims.setBorder(titled);
    colDelims.endRow(colDelimTab);
    colDelims.endRow(colDelimSemi);
    colDelims.endRow(colDelimComma);
    GridBagView alleleDelims = new GridBagView();
    etched = BorderFactory.createEtchedBorder();
    titled = BorderFactory.createTitledBorder(etched, "Allele");
    alleleDelims.setBorder(titled);
    alleleDelims.endRow(alleleDelim);
    GridBagView panel = new GridBagView();
    etched = BorderFactory.createEtchedBorder();
    titled = BorderFactory.createTitledBorder(etched, "Delimiters");
    panel.setBorder(titled);
    panel.endRow(colDelims);
    panel.endRow(alleleDelims);
    return panel;
  }
  private void loadFrom(KinshipFileFormat model) {
    alleleDelim.setText("" + model.getAlleleDelim());
    alleleDelim.setEditable(false);
    colDelimTab.setSelected(model.getColumnDelim() == tab);
    colDelimSemi.setSelected(model.getColumnDelim() == semi);
    colDelimComma.setSelected(model.getColumnDelim() == comma);
    comment.setText("" + model.getCommentLineMarker());
    comment.setEditable(false);

    freqNorm.setSelected(model.getFreqUserNorm());
    freqFile.setSelected(model.getFreqSource() == model.FREQ_SOURCE_FILE);
    freqCalc.setSelected(model.getFreqSource() == model.FREQ_SOURCE_CALC);
    freqBias.setSelected(model.getFreqSource() == model.FREQ_SOURCE_BIAS);
    freqEnd.setText(model.getFreqBlockEndMarker());
    freqEnd.setEditable(false);
    idCol.setValue(model.getIdColumn());
    idBox.setSelected(model.getHasId());
    idBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        adjust(idCol, ((JCheckBox) e.getSource()));
      }
    });
    adjust(idCol, idBox);
    matIdCol.setValue(model.getMatIdColumn());
    matIdBox.setSelected(model.getHasMatId());
    matIdBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        adjust(matIdCol, ((JCheckBox) e.getSource()));
      }
    });
    adjust(matIdCol, matIdBox);
    patIdCol.setValue(model.getPatIdColumn());
    patIdBox.setSelected(model.getHasPatId());
    patIdBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        adjust(patIdCol, ((JCheckBox) e.getSource()));
      }
    });
    adjust(patIdCol, patIdBox);
    groupIdCol.setValue(model.getGroupIdColumn());
    groupIdBox.setSelected(model.getHasGroupId());
    groupIdBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        adjust(groupIdCol, ((JCheckBox) e.getSource()));
      }
    });
    adjust(groupIdCol, groupIdBox);
    locusCol.setValue(model.getFirstLocusColumn());
    nLoci.setValue(model.getNumLoci());
  }
  private void adjust(JTextComponent text, AbstractButton button) {
    text.setEnabled(button.isSelected());
    text.setEditable(button.isSelected());
  }
  public void loadTo(KinshipFileFormat model) {
    loadColumnDelim(model);
    model.setFreqSource(getAlleleFreq());
    model.setFreqUserNorm(freqNorm.isSelected());

    model.setHasGroupId(groupIdBox.isSelected());
    model.setGroupIdColumn(groupIdCol.getInput());
    model.setHasId(idBox.isSelected());
    model.setIdColumn(idCol.getInput());
    model.setHasMatId(matIdBox.isSelected());
    model.setMatIdColumn(matIdCol.getInput());
    model.setHasPatId(patIdBox.isSelected());
    model.setPatIdColumn(patIdCol.getInput());
    model.setFirstLocusColumn(locusCol.getInput());
    model.setNumLoci(nLoci.getInput());
  }
  private int getAlleleFreq() {
    if (freqCalc.isSelected())
      return KinshipFileFormat.FREQ_SOURCE_CALC;
    if (freqBias.isSelected())
      return KinshipFileFormat.FREQ_SOURCE_BIAS;
    return KinshipFileFormat.FREQ_SOURCE_FILE;
  }
  private void loadColumnDelim(KinshipFileFormat bean) {
    bean.setColumnDelim(tab);
    if (colDelimComma.isSelected())
      bean.setColumnDelim(comma);
    else if (colDelimSemi.isSelected())
      bean.setColumnDelim(semi);
  }
}
