package kingroup_v2.kinship.like.view;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipRatioSimTable;

import javax.swing.*;
import javax.swingx.tablex.JTableFactory;
import javax.swingx.tablex.ReadOnlyTableModel;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;
import java.awt.*;
import java.text.NumberFormat;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/10/2005, Time: 07:32:33
 */
public class KinshipRatioSimTableView extends JPanel {
  private static final int TYPE_I_ERROR_IDX = 0;
  private static final int SIG_LEVEL_IDX = 1;
  private static final int TYPE_II_ERROR_IDX = 2;
  private JTable table;
  public KinshipRatioSimTableView(Kinship model) {
    init();
    table = makeTable(model);
    assemble(table);
  }
  public KinshipRatioSimTableView(Kinship model, KinshipRatioSimTable from) {
    table = makeTable(model);
    loadFrom(from, table, model);
    assemble(table);
  }
  public JTable getTable() {
    return table;
  }
  private void loadFrom(KinshipRatioSimTable from, JTable to, Kinship kinship) {
    loadSigLevels(from, to, kinship);
    loadTypeTwoErrors(from, to);
  }
  public void loadTypeOneErrors(JTable to, Kinship kinship) {
    float[] levels = kinship.getSigLevels();  // type I errors
    NumberFormat nf = NumberFormat.getNumberInstance();
    int i = 0;
    nf.setMaximumFractionDigits(0); // 0.05, 0.01
    to.setValueAt("*" + nf.format(100. * levels[i]) + "%", i, TYPE_I_ERROR_IDX);
    i++;
    to.setValueAt("**" + nf.format(100. * levels[i]) + "%", i, TYPE_I_ERROR_IDX);
    i++;
    nf.setMaximumFractionDigits(1); // 0.001
    to.setValueAt("***" + nf.format(100. * levels[i]) + "%", i, TYPE_I_ERROR_IDX);
  }
  private void loadSigLevels(KinshipRatioSimTable from, JTable to, Kinship kinship) {
    double[] sigLevels = from.getSigLevelLogs();
    if (sigLevels == null)
      return;
    for (int i = 0; i < sigLevels.length; i++) {
      String txt = kinship.formatLog(sigLevels[i]);
      to.setValueAt(txt, i, SIG_LEVEL_IDX);
    }
  }
  private void loadTypeTwoErrors(KinshipRatioSimTable from, JTable to) {
    NumberFormat nf2 = NumberFormat.getNumberInstance();
    nf2.setMaximumFractionDigits(0);
    NumberFormat nf3 = NumberFormat.getNumberInstance();
    nf3.setMaximumFractionDigits(1);
    StringBuffer buff = new StringBuffer();
    double[] twoErrors = from.getTypeTwoErrorLogs();
    if (twoErrors == null)
      return;
    for (int i = 0; i < twoErrors.length; i++) {
      buff.setLength(0);
      if (twoErrors[i] >= 0.1)
        buff.append(nf2.format(100. * twoErrors[i]));
      else
        buff.append(nf3.format(100. * twoErrors[i]));
      to.setValueAt(buff.append('%').toString(), i, TYPE_II_ERROR_IDX);
    }
  }
  private void init() {
    setLayout(new BorderLayout());
  }
  public void assemble(JTable t) {
    JTableFactory.initColumnSizes(t);
    t.setEnabled(false);
    t.setRowSelectionAllowed(true);
    t.setColumnSelectionAllowed(false);
    t.getTableHeader().setVisible(true);
    t.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    JScrollPane scroll = new JScrollPane(t);
    Dimension ps = JTableFactory.getPreferredSize(t);
    scroll.setPreferredSize(ps);
    scroll.setEnabled(false);
    add(scroll, BorderLayout.CENTER);
  }
  private JTable makeTable(Kinship kinship) {
    int N_ROWS = 3;
    int N_COLS = 3;
    String[][] rowData = new String[N_ROWS][N_COLS];
    StrMtrx.set(rowData, " ");
    String[] columnNames = new String[N_COLS];
    StrVec.set(columnNames, " ");
    columnNames[0] = "p<";
    if (kinship.getDisplayLogs())
      columnNames[1] = "log10";
    else
      columnNames[1] = "ratio";
    columnNames[2] = "type II";
    JTable res = new JTable(new ReadOnlyTableModel(rowData, columnNames));
    loadTypeOneErrors(res, kinship);
    return res;
  }
}
