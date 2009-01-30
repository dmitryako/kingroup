package javax.swingx.tablex;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swingx.panelx.ViewWithOpt;
abstract public class TableViewWithOpt  extends ViewWithOpt
  implements TableViewI
{
  private static ProjectLogger log = ProjectLogger.getLogger(TableViewWithOpt.class);
  private JTable table;

  final public void setTableView(JTable t) {
    super.setView(t);
    table = t;
  }
  final public JTable getTableView() {
    return table;
  }
  public void loadTable(DefaultTableModel m) {
    setTableView(new JTable(m));
    JTableFactory.initColumnSizes(getTableView());
    assembleWithOpt(getOrientation());
  }
}

