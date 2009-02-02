package qsar.bench.qsar.MLR.robust.mclts;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/05/2008, Time: 14:10:06
 */
public class QsarAlgMlrMclts extends QsarAlg
{
  public static final String REFERENCE = "MCLTS";

  public QsarAlgMlrMclts(double[][] zTrain)
  {
    super(zTrain);
    setName("MCLTS-MLR");
  }

  public double[] calcYFromXZ(double[][] xTest) {
    McltsMlrAlg reg = new McltsMlrAlg();
    if (!reg.calc(z))
      return null;
    setMlrRes(reg);
    double[] res = reg.calcYFromXZ(xTest);
    return res;
  }
}

