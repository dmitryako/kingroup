package pattern.mvc;
import javax.swing.*;
import javax.swingx.tablex.JTableFactory;
import javax.swingx.tablex.TableViewI;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/09/2005, Time: 11:00:40
 */
public class MVCTableView
  extends JPanel
  implements TableViewI
{
  protected final static String EMPTY = " ";
  private JTable table;
  public MVCTableView() {
    init();
  }
  private void init() {
    setLayout(new BorderLayout());
  }
  public void assemble(JTable table) {
    if (table == null)
      return;
    this.table = table;
    removeAll();
    JScrollPane scroll;
    if (table == null) {
      scroll = new JScrollPane(new JTable());
    } else {
      JTableFactory.initColumnSizes(table);
      scroll = new JScrollPane(table);
    }
    add(scroll, BorderLayout.CENTER);
//    endRow(scroll);
  }
  public JTable getTableView() {
    return table;
  }
//  public void setRightMouseButton(final UCController uc) {
//    table.addMouseListener(new MouseAdapter(){
//      public void mouseClicked(MouseEvent e){
//        if (javax.swing.SwingUtilities.isRightMouseButton(e)){
//          uc.run();
////          int index = myTable.rowAtPoint( e.getPoint() );
//        }
//      }
//    });
//  }

  public static JTable makeTableView(MVCTableView[] views) {
    int count = 0;
    for (int i = 0; i < views.length; i++)
      if (views[i] != null)
        count++;

    JTable[] tables = new JTable[count];
    int idx = 0;
    for (int i = 0; i < views.length; i++)  {
      if (views[i] == null)
        continue;
      tables[idx++] = views[i].getTableView();
    }

    return JTableFactory.assemble(tables);
  }

  protected JTable makeTableView(MVCTableView view, MVCTableView view2) {
    MVCTableView[] arr = new MVCTableView[2];
    arr[0] = view;
    arr[1] = view2;
    return makeTableView(arr);
  }
}


