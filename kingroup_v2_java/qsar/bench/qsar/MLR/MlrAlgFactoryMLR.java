package qsar.bench.qsar.MLR;
import qsar.bench.QBench;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 14:39:09
 */
public class MlrAlgFactoryMLR implements QsarAlgFactory
{
  public QsarAlg makeAlg(QBench model, double[][] Z)
  {
    return new QsarAlgMlr(Z);
  }

  public String getName()
  {
    return "OLS-MLR";
  }
}