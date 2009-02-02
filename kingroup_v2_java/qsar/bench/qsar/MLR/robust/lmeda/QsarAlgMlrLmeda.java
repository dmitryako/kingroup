package qsar.bench.qsar.MLR.robust.lmeda;
import qsar.bench.qsar.QsarAlg;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/05/2008, Time: 16:47:34
 */
public class QsarAlgMlrLmeda extends QsarAlg
{
  public static final String REFERENCE = "CLTS";

  public QsarAlgMlrLmeda(double[][] zTrain)
  {
    super(zTrain);
    setName("LMedA-MLR");
  }

  public double[] calcYFromXZ(double[][] xTest) {
    LmedaMlrAlg mlrAlg = new LmedaMlrAlg();
    if (!mlrAlg.calc(z))
      return null;
    setMlrRes(mlrAlg);
    double[] res = mlrAlg.calcYFromXZ(xTest);
    return res;
  }
}

