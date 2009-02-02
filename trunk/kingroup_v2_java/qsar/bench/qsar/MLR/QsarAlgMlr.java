package qsar.bench.qsar.MLR;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 15:12:35
 */
public class QsarAlgMlr extends QsarAlg
{
  public static final String REFERENCE = "Multiple Linear Regression";

  public QsarAlgMlr(double[][] zTrain)
  {
    super(zTrain);
    setName("MLR");
  }

  public double[] calcYFromXZ(double[][] xTest) {
    MlrAlg reg = new MlrAlg();
    if (!reg.calc(z))
      return null;
    setMlrRes(reg);
    double[] res = reg.calcYFromXZ(xTest);
    return res;
  }
}
