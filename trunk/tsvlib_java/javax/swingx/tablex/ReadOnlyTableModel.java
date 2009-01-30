package javax.swingx.tablex;
import javax.swing.table.DefaultTableModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/05/2006, Time: 10:43:47
 */
public class ReadOnlyTableModel extends DefaultTableModel
{
  public ReadOnlyTableModel(Object[][] data, Object[] columnNames) {
    super(data, columnNames);
  }
  public boolean isCellEditable(int row, int column) { return false; }
}
