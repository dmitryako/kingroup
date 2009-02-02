package qsar.bench.qsar.MLR.robust;
import qsar.bench.QBench;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.MLR.robust.clts.QsarAlgMlrClts;
import qsar.bench.qsar.MLR.robust.lmeda.QsarAlgMlrLmeda;
import qsar.bench.qsar.MLR.robust.lta.QsarAlgMlrLta;
import qsar.bench.qsar.MLR.robust.mclts.QsarAlgMlrMclts;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/04/2008, Time: 12:32:54
 */
public class MlrAlgFactoryRobust implements QsarAlgFactory
{
  public QsarAlg makeAlg(QBench model, double[][] Z)
  {
    if (model.getRobustType() == QBench.ROBUST_LTA) {
      return new QsarAlgMlrLta(Z);
    }

    if (model.getRobustType() == QBench.ROBUST_LMEDA) {
      return new QsarAlgMlrLmeda(Z);
    }

    if (model.getRobustType() == QBench.ROBUST_CLTS) {
      return new QsarAlgMlrClts(Z);
    }

    if (model.getRobustType() == QBench.ROBUST_MCLTS) {
      return new QsarAlgMlrMclts(Z);
    }

    return null;
  }

  public String getName()
  {
    return "Robust-MLR";
  }
}

