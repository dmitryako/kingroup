package kingroup_v2.like.thompson.view;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.kinship.view.KinshipIBDArrView;
import kingroup_v2.like.thompson.Thompson;
import kingroup_v2.like.thompson.ThompsonIBD;
import kingroup_v2.like.thompson.ThompsonIBDArr;

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
 * User: jc138691, Date: 27/03/2006, Time: 11:08:07
 */
public class ThompsonIBDArrView
  extends GridBagView
  implements TableViewI
{
  private static final int KIN_COL = 0;
  private JTable table;
  private ThompsonIBDView ibdView;
  private ThisTableModel tableModel;

  public ThompsonIBDArrView(Thompson model)
  {
    init();
    loadFrom(model);
    assemble();
  }

  private void assemble()
  {
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

  public void loadFrom(Thompson model) {
    ibdView = new ThompsonIBDView(model.getNullIBD());
    for (ThompsonIBD v: model.getNullArr())
      tableModel.addIBD(v);
  }

  protected void init()   {
    tableModel = new ThisTableModel();
    table = new JTable(tableModel);
  }


  public void loadTo(Thompson model)
  {
    ThompsonIBD[] arr = tableModel.getArr().toArray();
    model.setNullArr(arr);
  }
  public JTable getTableView() {return table;}

  public boolean find(ThompsonIBD ibd)
  {
    return tableModel.getArr().find(ibd);
  }


  private class ThisUCAdd implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      ThompsonIBD v = new ThompsonIBD();
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
    private ThompsonIBDArr arr;
    String[] colNames;

    public ThisTableModel(){
      init();
    }
    private void init(){
      arr = new ThompsonIBDArr();
      colNames = new String[ThompsonIBD.SIZE + 1];
      StrVec.set(colNames, " ");
      colNames[0] = "kin";
      for (int i = 0; i < ThompsonIBD.SIZE; i++)
        colNames[i+1] = "k" + i;
    }
    public String getColumnName(int col) {
      return colNames[col].toString();
    }
    public int getRowCount() { return arr.size(); }
    public int getColumnCount() { return colNames.length; }
    public Object getValueAt(int row, int col) {
      ThompsonIBD ibd = arr.get(row);
      ThompsonIBDView view = new ThompsonIBDView(ibd);
      if (col == KIN_COL)
        return view.getKinCode();
      if (col <= ibd.size())
        return view.getView(col - 1);
      return " ";
    }
    public boolean isCellEditable(int row, int col) { return false; }
    public void addIBD(ThompsonIBD v)
    {
      ThompsonIBDView view = new ThompsonIBDView(v);
//      if (v.equals(primIBD)) {
//        String mssg = "The k=("
//          + view.toString()
//          + ") null hypothesis already exists as primary.";
//        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), mssg);
//        return;
//      }
      if (arr.find(v))
      {
        String mssg = "The k=("
          + view.toString()
          + ") null hypothesis already exists.";
        JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), mssg);
        return;
      }
      arr.add(v);
    }
    public ThompsonIBDArr getArr()     {
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
