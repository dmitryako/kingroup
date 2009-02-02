package kingroup_v2.cervus;

import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Created by: jc1386591
 * Date: 14/07/2006. Time: 09:00:31
 */
public class AlleleAnalysisByCol extends AlleleAnalysisByRow {
  public AlleleAnalysisByCol(SysPop pop, Cervus cervus) {
    super(pop, cervus);
  }
  public int getNumRows()      {    return super.getNumCols();  }
  public int getNumCols()      {    return super.getNumRows();  }
  public void setNumRows(int n)  {    super.setNumCols(n);  }
  public void setNumCols(int n)  {    super.setNumRows(n);  }
  public String getColName(int i)  {    return super.getRowName(i);  }
  public String getRowName(int i)  {    return super.getColName(i);  }
  public String makeStringFor(int r, int c)  {    return super.makeStringFor(c, r);  }
}
