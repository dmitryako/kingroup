package qsar.bench.qsar.MLR.huber;
import qsar.bench.QBench;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2007, Time: 16:23:04
 */
public class MlrAlgFactoryMlrHuber implements QsarAlgFactory
{
  public QsarAlg makeAlg(QBench model, double[][] Z)
  {
    return new QsarAlgMlrHuber(Z, model.getCltsNumStarts());
  }

  public String getName()
  {
    return "Huber MLR";
  }
}

