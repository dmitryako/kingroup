package qsar.bench.qsar.cv.mcvs;
import qsar.bench.QBench;
import qsar.bench.qsar.MLR.QsarAlgFactory;

import javax.swingx.tablex.TableViewWithOpt;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/11/2007, Time: 12:21:35
 */
public interface McvsAlgFactory
{
//  String getName();
  public McvsAlg makeAlg(QBench project, QsarAlgFactory algFactory, TableViewWithOpt updateView);
}

