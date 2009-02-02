package qsar.bench.qsar.MLR;
import qsar.bench.QBench;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 15:00:38
 */
public class MlrAlgFactoryKnn implements QsarAlgFactory
{
  public QsarAlg makeAlg(QBench model, double[][] zTrain) {
    return new QsarAlgKnnMlr(model.getKnn(), zTrain);
  }

  public String getName()
  {
    return "kNN-MLR";
  }
}

