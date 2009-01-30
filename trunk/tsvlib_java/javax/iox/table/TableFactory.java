package javax.iox.table;
import tsvlib.project.ProjectLogger;

import javax.stats.Stats;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.bitset.BitSetFactory;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 3/06/2007, 13:11:41
 */
public class TableFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(TableFactory.class);

  public static Table makeMeanZeroVarOne(Table from) {
    double[][] z = Stats.makeColsMeanZeroVarOne(from.getMtrx());
    Table res = new Table(from);
    res.setMtrx(z);
    return res;
  }

  public static Table normColsToRange(Table from, double min, double max) {
    Table res = new Table(from);
    double[][] z = Mtrx.normColsToRange(from.getMtrx(), min, max);
    res.setMtrx(z);
    return res;
  }

  public static Table sortRows(final int byColIdx, final Table from, final boolean ascend) {
    int[] idxOrder = from.sortRows(byColIdx, ascend);
    Table res = from.getRows(idxOrder);    log.debug("res=\n", res);
    return res;
  }

  public static Table sortColsByCorr(final Table from, final boolean ascend)
  {
    int[] idxOrder = from.sortColsByCorr(ascend);
    Table res = from.getCols(idxOrder);    log.debug("res=\n", res);
    return res;
  }

  public static Table sortColsByAbsCorr(final Table from, final boolean ascend)
  {
    int[] idxOrder = from.sortColsByAbsCorr(ascend);
    Table res = from.getCols(idxOrder);    log.debug("res=\n", res);
    return res;
  }

  public static TablePair splitRandRows(int nFirst, Table from)
  {
    int n = from.getNumRows();
    BitSet set = BitSetFactory.makeRandom(nFirst, n);
    Table a = from.getRows(set);          log.debug("a=\n", a);

    set.flip(0, n);    
    Table b = from.getRows(set);    log.debug("b=\n", b);
    TablePair res = new TablePair(a, b);
    return res;
  }
}
