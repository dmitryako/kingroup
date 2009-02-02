package qsar.bench.qsar.MLR;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 10:28:26
 */
public class QsarAlgMccvMlr extends QsarAlg
{
  public static final String REFERENCE = "MCCV-of-MLR";

  public QsarAlgMccvMlr(double[][] zTrain) {
    super(zTrain);
    setName("MCCV-of-MLR");
  }
//  public double[] train(double[][] z)
//  {
//    double[] res = new Loocv(new LooMlr()).calcLOO(z);
//    return res;
//  }
  public double[] calcYFromXZ(double[][] xTest) {
    MlrAlg reg = new MlrAlg();
    reg.calc(z);
    double[] res = reg.calcYFromXZ(xTest);
    return res;
  }
}


