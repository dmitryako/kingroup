package javax.iox.table;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
import javax.utilx.arrays.StrVec;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.util.BitSet;

/**
 * Copyright www.DmitryKonovalov.org
 * User: jc138691, Date: 6/03/2007, Time: 15:15:43
 */
public class Table extends Mtrx
{
  private static ProjectLogger log = ProjectLogger.getLogger(Table.class);
  private String name;
  private String[] rowIds;
  private String[] colIds;

  public Table() {
//    super();
  }
  public Table(int nRows, int nCols)
  {
    super(nRows, nCols);
    name = "";
  }

  public Table(double[][] m)
  {
    super(m);
    name = "";
  }

  public Table(Table from)
  {
    super(from.getMtrx());
    setName(from.getName());
    setRowIds(from.getRowIds());
    setColIds(from.getColIds());
  }

  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append(colIdsToCSV());
    buff.append(SysProp.EOL);
    buff.append(toCSV(ProjectLogger.MAX_N_ROWS_TO_STRING));
    return buff.toString();
  }

  private String colIdsToCSV()
  {
    StringBuffer buff = new StringBuffer();
    buff.append(getName() + " , ");
    for (int c = 0; c < getNumCols(); c++) {
      buff.append(getColId(c));
      if (c != getNumCols()-1)
        buff.append(", ");
    }
    return buff.toString();
  }

  public String toCSV(int nRows) {
    StringBuffer buff = new StringBuffer();
    int nView = getNumRows();
    if (nRows != -1)
      nView = Math.min(nRows, nView);
    for (int r = 0; r < nView; r++) {
      buff.append(getRowId(r));
      buff.append(", ");
      buff.append(Vec.toCSV(getRow(r)));
      buff.append(SysProp.EOL);
    }
    return buff.toString();
  }

  public String getColId(int c) {
    if (colIds == null  ||  colIds.length <= c  || c < 0)
      return Integer.toString(c+1);
    return colIds[c];
//    return Integer.toString(c+1) + "-" + colIds[c];
  }
  public String getRowId(int r) {
    if (rowIds == null  ||  rowIds.length <= r  || r < 0)
      return Integer.toString(r+1);
    return rowIds[r];
//    return Integer.toString(r+1) + "-" + rowIds[r];
  }

  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }

  public String[] getRowIds() {
    return rowIds;
  }
  public void setRowIds(String[] rowIds)
  {
    this.rowIds = rowIds;
  }

  public String[] getColIds() {
    return colIds;
  }
  public void setColIds(String[] colIds)
  {
    this.colIds = colIds;
  }

  public Table getRows(int[] rowIdxOrder)
  {
    int nR = getNumRows();
    double[][] newM = Mtrx.getRows(getByRows(), rowIdxOrder, nR);
    Table res = new Table(newM);
    String[] newRowIds = StrVec.order(rowIdxOrder, getRowIds());
    res.setRowIds(newRowIds);
    res.setColIds(getColIds());
    res.setName(getName());
    return res;
  }
  public Table getCols(int[] colIdxOrder)
  {
    Table res = trans();
    res = res.getRows(colIdxOrder);
    return res.trans();
  }
  public Table trans()
  {
    double[][] zt = super.trans().getByRows();
    Table res = new Table(zt);
    res.setName(getName());
    res.setRowIds(getColIds());
    res.setColIds(getRowIds());
    return res;
  }


  public Table getCols(BitSet set) {
    double[][] m = Mtrx.getCols(set, getByRows());
    Table res = new Table(m);
    String[] ids = StrVec.get(set, getColIds());
    res.setColIds(ids);
    res.setRowIds(getRowIds());
    res.setName(getName());
    return res;
  }

  public Table getRows(BitSet set) {
    double[][] m = Mtrx.getRows(set, getByRows());
    Table res = new Table(m);
    String[] ids = StrVec.get(set, getRowIds());
    res.setRowIds(ids);
    res.setColIds(getColIds());
    res.setName(getName());
    return res;
  }

  public Table delCols(int fromIdx, int toIdx) {
    BitSet set = new BitSet();
    set.set(0, getNumCols(), true);
    set.set(fromIdx, toIdx, false);
    return getCols(set);
  }

  public Table appendCols(Table from)
  {
    log.debug("t2=\n", from);
    int nR = getNumRows();
    if (nR != from.getNumRows())
      return null;

    double[][] mtrx = Mtrx.appendCols(getByRows(), from.getByRows());

    Table res = new Table(mtrx);
    String[] newColIds = StrVec.append(getColIds(), from.getColIds());
    res.setColIds(newColIds);
    res.setRowIds(getRowIds());
    res.setName(getName() + "+" + from.getName());
    log.debug("table res=\n", res);
    return res;
  }
  public Table appendRows(Table from)
  {
    log.debug("t2=\n", from);
    int nR = getNumCols();
    if (nR != from.getNumCols())
      return null;

    double[][] mtrx = Mtrx.appendRows(getByRows(), from.getByRows());

    Table res = new Table(mtrx);
    String[] newRowIds = StrVec.append(getRowIds(), from.getRowIds());
    res.setRowIds(newRowIds);
    res.setColIds(getColIds());
    res.setName(getName() + "+" + from.getName());
    log.debug("table res=\n", res);
    return res;
  }

  public Table getTableCols(int startInc, int endExc)
  {
    double[][] m = Mtrx.getCols(startInc, endExc, getByRows());
    Table res = new Table(m);
    String[] ids = StrVec.get(startInc, endExc, getColIds());
    res.setColIds(ids);
    res.setRowIds(getRowIds());
    res.setName(getName());
    return res;
  }

  public Table getRows(int startInc, int endExc)
  {
    double[][] m = Mtrx.getRows(startInc, endExc, getByRows());
    Table res = new Table(m);
    String[] ids = StrVec.get(startInc, endExc, getRowIds());
    res.setColIds(getColIds());
    res.setRowIds(ids);
    res.setName(getName());
    return res;
  }
  public Table randomEachRow()
  {
    double[][] m = getByRows();
    Mtrx.randomEachRow(m);
    Table res = new Table(m);

    res.setColIds(getColIds());
    res.setRowIds(getRowIds());
    res.setName(getName());
    return res;
  }

  public Table randomEachCol()
  {
    Table t = trans();   log.trace("trans:\n", t);
    t = t.randomEachRow(); log.trace("random Table:\n", t);
    return t.trans();
  }

  public Table getColsByName(String[] names)
  {
    int[] orderIdx = StrVec.find(names, getColIds());
    return getCols(orderIdx);
  }
  public Table moveColByName(String name, int idxDest)
  {
    int idx = StrVec.find(name, getColIds());
    if (idx == -1)
      return null;
    String[] names = getColIds();
    int[] orderIdx = new int[names.length];
    int idxFrom = 0;
    for (int idxTo = 0; idxTo < orderIdx.length; idxTo++) {
      if (idxTo == idxDest) {
        orderIdx[idxTo] = idx;
        continue;
      }
      if (idxFrom == idx) {
        orderIdx[idxTo] = ++idxFrom;
        continue;
      }
      orderIdx[idxTo] = idxFrom++;
    }    
    return getCols(orderIdx);
  }

}
