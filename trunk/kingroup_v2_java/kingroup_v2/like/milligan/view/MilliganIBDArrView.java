package kingroup_v2.like.milligan.view;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.kinship.view.KinshipIBDArrView;
import kingroup_v2.like.milligan.Milligan;
import kingroup_v2.like.milligan.MilliganIBD;
import kingroup_v2.like.milligan.MilliganIBDArr;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swingx.panelx.GridBagView;
import javax.swingx.tablex.TableViewI;
import javax.utilx.arrays.StrVec;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/03/2006, Time: 17:11:10
 */
public class MilliganIBDArrView
  extends GridBagView
  implements TableViewI
{
  private static final int KIN_COL = 0;
  private JTable table;
  private MilliganIBDView ibdView;
  private ThisTableModel tableModel;

  public MilliganIBDArrView(Milligan model)
  {
    init();
    loadFrom(model);
    assemble();
  }

  private void assemble()
  {
//    getStartRow().anchor = GridBagConstraints.NORTHEAST;
//    getEndRow().anchor = GridBagConstraints.NORTHEAST;
    endRow(KinshipIBDArrView.assemble(table));
    endRow(ibdView);

    GridBagView panel = new GridBagView();
    JButton bttn = new JButton("Delete");
    bttn.addActionListener(new ThisUCDelete());
    panel.startRow(bttn);

    bttn = new JButton("Add");
    bttn.addActionListener(new ThisUCAdd());
    panel.endRow(bttn);

    JPanel panel2 = new JPanel(new BorderLayout());
    panel2.add(panel, BorderLayout.EAST);
    endRow(panel2);
  }

  public void loadFrom(Milligan model) {
    ibdView = new MilliganIBDView(model.getNullIBD());
    for (MilliganIBD v: model.getNullArr())
      tableModel.addIBD(v);
  }

  protected void init()   {
    tableModel = new ThisTableModel();
    table = new JTable(tableModel);
  }


  public void loadTo(Milligan model)
  {
    MilliganIBD[] arr = tableModel.getArr().toArray();
    model.setNullArr(arr);
  }
//  public KinshipIBDArr getArr() {tableModel.getArr();}
  public JTable getTableView() {return table;}
  private class ThisUCAdd implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      MilliganIBD v = new MilliganIBD();
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
      tableModel.remove(idx);
      table.revalidate();
      table.repaint();
    }
  }

  private class ThisTableModel extends AbstractTableModel {
    private MilliganIBDArr arr;
    String[] colNames;
//    private static final int K_0 = 9;
//    private static final int K_1 = 8;
//    private static final int K_2 = 7;

    public ThisTableModel(){
      init();
    }
    private void init(){
      arr = new MilliganIBDArr();
      colNames = new String[MilliganIBD.SIZE + 1];
      StrVec.set(colNames, " ");
      colNames[0] = "kin";
      for (int i = 0; i < MilliganIBD.SIZE; i++)
        colNames[i+1] = "s" + (i+1);
//      colNames[K_0] = "k0 (s9)";
//      colNames[K_1] = "k1 (s8)";
//      colNames[K_2] = "k2 (s7)";
    }
    public String getColumnName(int col) {
      return colNames[col].toString();
    }
    public int getRowCount() { return arr.size(); }
    public int getColumnCount() { return colNames.length; }
    public Object getValueAt(int row, int col) {
      MilliganIBD ibd = arr.get(row);
      MilliganIBDView view = new MilliganIBDView(ibd);
      if (col == KIN_COL)
        return view.getKinCode();
      if (col <= ibd.size())
        return view.getView(col - 1);
      return " ";
    }
    public boolean isCellEditable(int row, int col) { return false; }
    public void addIBD(MilliganIBD v)
    {
      if (arr.find(v))
      {
        MilliganIBDView view = new MilliganIBDView(v);
        String mssg = "The S=(";
        for (int i = 0; i < v.size(); i++) {
          mssg += view.getView(i);
          if (i != v.size()-1)
            mssg += "  ";
        }
        mssg += ") hypothesis already exists.";
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), mssg);
        return;
      }
      arr.add(v);
    }
    public MilliganIBDArr getArr()     {
      return arr;
    }
    public void remove(int idx)    {
      arr.remove(idx)  ;
    }
  }
}
