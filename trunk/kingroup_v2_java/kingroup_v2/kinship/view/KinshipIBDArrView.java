package kingroup_v2.kinship.view;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDArr;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swingx.panelx.GridBagView;
import javax.swingx.tablex.JTableFactory;
import javax.swingx.tablex.TableViewI;
import javax.utilx.arrays.StrVec;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 15:57:20
 */
public class KinshipIBDArrView
  extends GridBagView
  implements TableViewI
{
  private static final int KIN_COL = 0;
  private static final int RM_COL = 1;
  private static final int RP_COL = 2;
  private JTable table;
  private KinshipIBDView ibdView;
  private ThisTableModel tableModel;
  private JButton deleteBttn;
  private JButton addBttn;

  public KinshipIBDArrView(Kinship model)
  {
    init();
    loadFrom(model);
    assemble();
  }

  private void assemble()
  {
//    getStartRow().anchor = GridBagConstraints.NORTHEAST;
//    getEndRow().anchor = GridBagConstraints.NORTHEAST;
    endRow(assemble(table));
    endRow(ibdView);

    GridBagView panel = new GridBagView();
    deleteBttn = new JButton("Delete");
    deleteBttn.addActionListener(new ThisUCDelete());
    panel.startRow(deleteBttn);

    addBttn = new JButton("Add");
    addBttn.addActionListener(new ThisUCAdd());
    panel.endRow(addBttn);

    JPanel panel2 = new JPanel(new BorderLayout());
    panel2.add(panel, BorderLayout.EAST);
    endRow(panel2);
  }

  public void loadFrom(Kinship model) {
    ibdView = new KinshipIBDView(model.getComplexNullIBD());
    for (KinshipIBD v: model.getNullArr())
      tableModel.addIBD(v);
  }

  protected void init()   {
    tableModel = new ThisTableModel();
    table = new JTable(tableModel);
  }

  public static JPanel assemble(JTable t) {
    JPanel res = new JPanel(new BorderLayout());
    JTableFactory.initColumnSizes(t);
    t.setEnabled(true);
    t.setDragEnabled(false);
    t.getTableHeader().setReorderingAllowed(false);

    t.setRowSelectionAllowed(true);
    t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    t.setColumnSelectionAllowed(false);

    t.getTableHeader().setVisible(true);
    t.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    JScrollPane scroll = new JScrollPane(t);
    Dimension ps = JTableFactory.getPreferredSize(t);
    final int EXTRA = 40;
    scroll.setPreferredSize(new Dimension(ps.width, ps.height + EXTRA));
    scroll.setEnabled(false);
    res.add(scroll, BorderLayout.CENTER);
    return res;
  }


  public void loadTo(Kinship model)
  {
    KinshipIBD[] arr = tableModel.getArr().toArray();
    model.setNullArr(arr);
  }
//  public KinshipIBDArr getArr() {tableModel.getArr();}
  public JTable getTableView() {return table;}

  public void onIBDChange(UCController uc) {
    deleteBttn.addActionListener(new AdapterUCCToALThread(uc));
    addBttn.addActionListener(new AdapterUCCToALThread(uc));
  }

  public boolean find(KinshipIBD ibd)
  {
    return tableModel.getArr().find(ibd);
  }

  private class ThisUCAdd implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      KinshipIBD v = new KinshipIBD();
      ibdView.loadTo(v);
      tableModel.addIBD(v);
      table.revalidate();
      table.repaint();
    }
  }
  private class ThisUCDelete implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      int idx = table.getSelectedRow();
      if (idx == -1) {
        String mssg = "You may only delete a selected row.";
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), mssg);
        return;
      }
      tableModel.remove(idx);
      table.revalidate();
      table.repaint();
    }
  }

  private class ThisTableModel extends AbstractTableModel {
    private KinshipIBDArr arr;
    private static final int N_COLS = 3;
    String[] colNames;
    public ThisTableModel(){
      init();
    }
    private void init(){
      arr = new KinshipIBDArr();
      colNames = new String[N_COLS];
      StrVec.set(colNames, " ");
      colNames[0] = "kin";
      colNames[1] = "Rm";
      colNames[2] = "Rp";
    }
    public String getColumnName(int col) {
      return colNames[col].toString();
    }
    public int getRowCount() { return arr.size(); }
    public int getColumnCount() { return colNames.length; }
    public Object getValueAt(int row, int col) {
      KinshipIBD ibd = arr.get(row);
      KinshipIBDView view = new KinshipIBDView(ibd);
      if (col == KIN_COL)
        return view.getKinCode();
      if (col == RM_COL)
        return view.getRmView();
      if (col == RP_COL)
        return view.getRpView();
      return " ";
    }
    public boolean isCellEditable(int row, int col) { return false; }
    public void addIBD(KinshipIBD v)
    {
      KinshipIBDView view = new KinshipIBDView(v);
      if (arr.find(v))
      {
        String mssg = "The ("
          + view.toString()
          + ") null hypothesis already exists.";
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), mssg);
        return;
      }
      arr.add(v);
    }
    public KinshipIBDArr getArr()     {
      return arr;
    }
    public void remove(int idx)    {
      if (idx >= arr.size())
        return;
      if (idx < 0)
        return;
      arr.remove(idx)  ;
    }
  }
}
