package javax.swingx.text_fieldx;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swingx.panelx.GridBagView;
import javax.utilx.arrays.IntVec;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/11/2005, Time: 08:57:49
 */
public class IntegerList extends GridBagView
{
  private JTable tableView;
  private ThisTableModel tableModel;
  private IntTextField intField;
  private IntegerListModel model;

  public IntegerList(IntegerListModel model) {
    this.model = model;
    init();
    assemble();
  }

  private void assemble()
  {
    JButton bttn = new JButton("Add");
    bttn.addActionListener(new ThisUCAddInteger());

    JScrollPane scrollPane = new JScrollPane(tableView);
    Dimension dim = new Dimension(model.getWidth(), model.getHeight());
    tableView.setPreferredScrollableViewportSize(dim);
    scrollPane.setMinimumSize(dim);
//    scrollPane.setPreferredSize(new Dimension(100, 100));
    this.endRow(scrollPane);
    this.startRow(intField);
    this.endRow(bttn);
  }

  private void init()
  {
    tableModel = new ThisTableModel();
    tableView = new JTable(tableModel);
    tableView.setDefaultEditor(Integer.class
      , new IntegerEditor(model.getMinValue(), model.getMaxValue()));
    intField = new IntTextField(model.getFieldSize(), model.getMinValue(), model.getMinValue(), model.getMaxValue());
    intField.setMinimumSize(intField.getPreferredSize());
  }

  public void loadFrom(int[] from)
  {
    tableModel.setIntArray(from);
  }
  public int[] getIntArray()
  {
    return tableModel.getIntArray();
  }

  public void setColumnName(int i, String s)
  {
    tableModel.setColumnName(i, s);
    tableView.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(s);
  }

  public void setIntField(int i)
  {
    intField.setValue(i);
  }

  public int getIntField()
  {
    return intField.getInput();
  }

  private class ThisTableModel extends AbstractTableModel
  {
    private ArrayList<Integer> arr = new ArrayList<Integer>();
    private static final int N_COLUMNS = 2;
    private String[] columnNames = {"row #", "value"};

    public int getRowCount()
    {
      return arr.size();
    }

    public int getColumnCount()
    {
      return N_COLUMNS;
    }
    public String getColumnName(int col) {
      return columnNames[col];
    }
    public Object getValueAt(int rowIndex, int columnIndex)
    {
      if (columnIndex == 0)
        return new Integer(rowIndex+1);
      return arr.get(rowIndex);
    }
    public Class getColumnClass(int c) {
      return getValueAt(0, c).getClass();
    }
    public boolean isCellEditable(int row, int col) {
      if (col == 0) {
        return false;
      } else {
        return true;
      }
    }
    public void setValueAt(Object value, int row, int col) {
      if (col == 1)
        arr.set(row, (Integer)value);
      fireTableCellUpdated(row, col);
    }

    public void add(int i)
    {
      arr.add(new Integer(i));
    }

    public void setColumnName(int i, String s)
    {
      columnNames[i] = s;
    }

    public int[] getIntArray()
    {
      return IntVec.toArray(arr.toArray());
    }

    public void setIntArray(int[] from)
    {
      if (from == null)
        return;
      for (int i = 0; i < from.length; i++) {
        add(from[i]);
      }
    }
  }

  private class ThisUCAddInteger implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      int i = intField.getInput();
      tableModel.add(i);
      tableView.revalidate();
      tableView.repaint();
    }
  }
}
