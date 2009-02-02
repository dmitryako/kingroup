package qsar.bench.qsar.cv.mcvs;
import javax.iox.table.Table;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/11/2007, Time: 12:26:23
 */
public interface McvsAlg
{
  void calcStats(Table zTrain, boolean b);
}
