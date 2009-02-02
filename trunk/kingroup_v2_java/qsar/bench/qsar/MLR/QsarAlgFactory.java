package qsar.bench.qsar.MLR;
import qsar.bench.QBench;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 14:37:58
 */
public interface QsarAlgFactory
{
  public QsarAlg makeAlg(QBench model, double[][] Z);

  String getName();
}
