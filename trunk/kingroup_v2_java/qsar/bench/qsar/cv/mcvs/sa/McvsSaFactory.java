package qsar.bench.qsar.cv.mcvs.sa;
import qsar.bench.QBench;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.cv.mcvs.McvsAlg;
import qsar.bench.qsar.cv.mcvs.McvsAlgFactory;

import javax.swingx.tablex.TableViewWithOpt;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/11/2007, Time: 12:23:52
 */
public class McvsSaFactory implements McvsAlgFactory
{
  public McvsAlg makeAlg(QBench project, QsarAlgFactory algFactory, TableViewWithOpt updateView) {
    return new McvsSaAlg(project, algFactory, updateView);
  }
}
