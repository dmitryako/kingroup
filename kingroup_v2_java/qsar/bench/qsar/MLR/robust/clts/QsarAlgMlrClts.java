package qsar.bench.qsar.MLR.robust.clts;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/03/2008, Time: 14:51:27
 */
public class QsarAlgMlrClts  extends QsarAlg
{
  public static final String REFERENCE = "CLTS";

  public QsarAlgMlrClts(double[][] zTrain)
  {
    super(zTrain);
    setName("CLTS-MLR");
  }

  public double[] calcYFromXZ(double[][] xTest) {
    CltsMlrAlg reg = new CltsMlrAlg();
    if (!reg.calc(z))
      return null;
    setMlrRes(reg);
    double[] res = reg.calcYFromXZ(xTest);
    return res;
  }
}

