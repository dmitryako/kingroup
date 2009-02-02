package kingroup_v2.cervus.view;
import kingroup_v2.cervus.AlleleAnalysisByRow;
import kingroup_v2.cervus.Cervus;
import pattern.mvc.MVCTableView;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.utilx.arrays.StrMtrx;
import javax.utilx.arrays.StrVec;
import java.awt.event.MouseEvent;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/03/2006, Time: 11:35:07
 */
public class AlleleAnalysisTableView extends MVCTableView
{
  private final AlleleAnalysisByRow mtrx;
  private Cervus cervus;
  public AlleleAnalysisTableView(AlleleAnalysisByRow m, Cervus cervus)
  {
    this.mtrx = m;
    this.cervus = cervus;
    JTable t = makeTableView();
    assemble(t);
  }
  protected JTable makeTableView() {
    int nRow = mtrx.getNumRows();
    int nCol = mtrx.getNumCols();
    String[][] rowData = new String[nRow][nCol];
    StrMtrx.set(rowData, EMPTY);
    String[] columnNames = new String[nCol];
    StrVec.set(columnNames, EMPTY);

    for (int c = 0; c < nCol; c++) {
      columnNames[c] = mtrx.getColName(c);
      for (int r = 0; r < nRow; r++) {
        rowData[r][c] = mtrx.makeStringFor(r, c);
      }
    }

    JTable table = new JTable(rowData, columnNames) {
    //JTable table = new JTable(rowData, null) {
      // from http://java.sun.com/docs/books/tutorial/uiswing/components/example-1dot4/index.html#TableToolTipsDemo
      protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
          public String getToolTipText(MouseEvent e) {
            java.awt.Point p = e.getPoint();
            int idx = columnModel.getColumnIndexAtX(p.x);
            if (idx == -1)
              return null;
            int realIndex = columnModel.getColumn(idx).getModelIndex();
            return mtrx.getValTip(realIndex);
          }
        };
      }
    };
    return table;
  }
}
