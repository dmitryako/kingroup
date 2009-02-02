package qsar.bench.qsar.MLR.huber;
import qsar.bench.qsar.MLR.irls.HuberIrlsMlrAlg;
import qsar.bench.qsar.MLR.irls.HuberIrslNorm;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2007, Time: 12:51:39
 */
public class QsarAlgMlrHuber  extends QsarAlg
{
  public static final String REFERENCE = "Multiple Linear Regression";
  private HuberIrslNorm huberNorm;

  public QsarAlgMlrHuber(double[][] zTrain, float param)
  {
    super(zTrain);
    setName("HuberMLR");
    huberNorm = new HuberIrslNorm();
    huberNorm.setParam(param);
  }

  public double[] calcYFromXZ(double[][] xTest) {
    HuberIrlsMlrAlg reg = new HuberIrlsMlrAlg(huberNorm);
    if (!reg.calc(z))
      return null;
    setMlrRes(reg);
    double[] res = reg.calcYFromXZ(xTest);
    return res;
  }


}

