package qsar.bench.qsar.MLR.robust.lta;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/04/2008, Time: 12:35:39
 */
public class QsarAlgMlrLta  extends QsarAlg
{
  public static final String REFERENCE = "CLTS";

  public QsarAlgMlrLta(double[][] zTrain)
  {
    super(zTrain);
    setName("LTA-MLR");
  }

  public double[] calcYFromXZ(double[][] xTest) {
    LtaMlrAlg reg = new LtaMlrAlg();
    if (!reg.calc(z))
      return null;
    setMlrRes(reg);
    double[] res = reg.calcYFromXZ(xTest);
    return res;
  }
}

