package qsar.bench.qsar;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/04/2007, 15:33:25
 */
public interface QsarAlgI
{
//  public double[] train(double[][] zTrain);
  public double[] calcYFromXZ(double[][] xTest);
  public int getNumPredictors();
}
