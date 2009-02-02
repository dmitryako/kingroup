package kingroup_v2.fsr.bound;
import junit.framework.TestCase;

import javax.iox.LOG;
import tsvlib.project.ProjectLogger;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/11/2005, Time: 17:22:52
 */
public class FsrLBoundApproxGSOneTest extends TestCase
{
  double EPS = 1.e-6;
  private static ProjectLogger log = ProjectLogger.getLogger(FsrLBoundApproxGSOneTest.class.getName());
  String DIR = "papers" + File.separator + "kingroup2005c_limit" + File.separator + "output";

  public void testCalcNumErrorsRec()
  {
    int L = 1;
    int r = 2;
    int na = 10;
    FsrLBoundApproxGSOne one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.00909090909090909, one.calcAccuracyError2(), EPS);
    log.info("\n\n");
    assertEquals(0.00909090909090909, one.calcAccuracyError(false), EPS);
    log.info("\n\n");

    L = 1;
    r = 3;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.018071625344352613, one.calcAccuracyError(false), EPS);
    log.info("\n\n");
    assertEquals(0.01807162534435261, one.calcAccuracyError2(), EPS);
    log.info("\n\n");

    L = 1;
    r = 4;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.026943651389932394, one.calcAccuracyError(false), EPS);
    log.info("\n\n");
    assertEquals(0.026943651389932394, one.calcAccuracyError2(), EPS);
    log.info("\n\n");

    L = 1;
    r = 10;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.07797474793628406, one.calcAccuracyError(false), EPS);
    log.info("\n\n");
    assertEquals(0.07797474793626831, one.calcAccuracyError2(), EPS);
    log.info("\n\n");

    L = 1;
    r = 30;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.22394565972191607, one.calcAccuracyError(false), EPS);
    log.info("\n\n");
    assertEquals(0.22390656591042724, one.calcAccuracyError2(), EPS);
    log.info("\n\n");
  }

  public void testPlotFilledBoxesP()
  {
    int L = 1;
    int r = 10;
    int na = 10;
    FsrLBoundApproxGSOne one = new FsrLBoundApproxGSOne(r, na, L);
    double[] pk = new double[r];
    double[] pk2 = new double[r];
    double[] pk3 = new double[r];
    for (int k = 1; k < r; k++)
    {
//      pk[k] = one.calcFilledBoxesApprox(k);
      pk2[k] = one.calc_pk(k);
      pk3[k] = pk2[k] - pk[k];
    }
    one.calcAccuracyError(false);
    pk = one.Pk;
    one.calcAccuracyError2();
    pk2 = one.Pk;
    LOG.saveToFile(pk, pk2, pk3, DIR, "pk_r"+r+".csv");
  }
  public void testPlotAccuracyErrors()
  {
    int L = 2;
    int r = 40;
    int na = 10;
    double[] pk = new double[r];
    double[] pk2 = new double[r];
    for (int r2 = 2; r2 < r; r2++)
    {
      FsrLBoundApproxGSOne one = new FsrLBoundApproxGSOne(r2, na, L);
      pk[r2] = one.calcAccuracyError(false);
      pk2[r2] = one.calcAccuracyError2();
    }
    LOG.saveToFile(pk, pk2, DIR, "acc_error_r"+r+"_L"+L+".csv");
  }
  public void testCalcNumErrors()
  {
    int L = 1;
    int r = 2;
    int na = 10;
    FsrLBoundApproxGSOne one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.007940209259028334, one.calcAccuracyError(true), EPS);
    log.info("\n\n");
    assertEquals(0.00909090909090909, one.calcAccuracyError(false), EPS);
    log.info("\n\n");

    L = 1;
    r = 3;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.015443147815415482, one.calcAccuracyError(true), EPS);
    log.info("\n\n");
    assertEquals(0.018071625344352613, one.calcAccuracyError(false), EPS);
    log.info("\n\n");

    L = 1;
    r = 4;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.027322903893688258, one.calcAccuracyError(true), EPS);
    log.info("\n\n");
    assertEquals(0.026943651389932394, one.calcAccuracyError(false), EPS);
    log.info("\n\n");

    L = 1;
    r = 5;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.047042802769053385, one.calcAccuracyError(true), EPS);
    log.info("\n\n");
    assertEquals(0.03570846800081963, one.calcAccuracyError(false), EPS);
    log.info("\n\n");

    L = 1;
    r = 10;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.570252312220487, one.calcAccuracyError(true), EPS);
    log.info("\n\n");
    assertEquals(0.07797474793628406, one.calcAccuracyError(false), EPS);
    log.info("\n\n");


    L = 1;
    r = 20;
    na = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.570252312220487, one.calcAccuracyError(true), EPS);
    log.info("\n\n");
    assertEquals(0.07797474793628406, one.calcAccuracyError(false), EPS);
    log.info("\n\n");


    L = 2;
    r = 50;
    int k = 30;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(3.3941282025706847E-4, one.calc_pk2(k));
    assertEquals(3.3941282021364373E-4, one.calc_pk(k));

    L = 2;
    r = 50;
    one = new FsrLBoundApproxGSOne(r, na, L);
//    assertEquals(0.008056500842864111, one.calcAccuracyError(false, false));
    assertEquals(0.008056500842864111, one.calcAccuracyError(true));

    L = 3;
    r = 50;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.008056500842864111, one.calcAccuracyError(true));

    L = 1;
    r = 3;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.018071625344352613, one.calcAccuracyError(false));

    L = 1;
    r = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.07797474794107763, one.calcAccuracyError(false));

    L = 1;
    r = 100;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.5377949876913644, one.calcAccuracyError(false));

    L = 2;
    r = 10;
    one = new FsrLBoundApproxGSOne(r, na, L);
    assertEquals(0.0014862926783034886, one.calcAccuracyError(false));
  }
  public void testCalcFilledBoxesP()
  {
    double eps = 1e-7;
    int na = 10; // num of alleles

    int r = 2;
    int nLoci = 1;
    FsrLBoundApproxGSOne bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    int k = 2;
    assertEquals(0.5, bound.calc_pk(k), eps);
    assertEquals(bound.calc_pk(k), bound.calc_pk2(k), eps);

    r = 3;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    k = 2;
    assertEquals(0.75, bound.calc_pk(k), eps);
    assertEquals(bound.calc_pk(k), bound.calc_pk2(k), eps);
    k = 3;
    assertEquals(0.22222222222222238, bound.calc_pk(k), eps);
    assertEquals(bound.calc_pk(k), bound.calc_pk2(k), eps);

    r = 10;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    k = 2;
    assertEquals(0.998046875, bound.calc_pk(k), eps);
    assertEquals(bound.calc_pk(k), bound.calc_pk2(k), eps);
    k = 3;
    assertEquals(0.9480262155159275, bound.calc_pk(k), eps);
    assertEquals(bound.calc_pk(k), bound.calc_pk2(k), eps);

    r = 50;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    k = 2;
    assertEquals(1.000000000000008, bound.calc_pk2(k), eps);
    assertEquals(bound.calc_pk2(k), bound.calc_pk(k), eps);
    k = 3;
    assertEquals(0.999999995295028, bound.calc_pk2(k), eps);
    assertEquals(bound.calc_pk(k), bound.calc_pk2(k), eps);
  }

  public void testCalcBoxesErrors()
  {
    double eps = 1e-7;
    int na = 10; // num of alleles

    int r = 2;
    int nLoci = 1;
    FsrLBoundApproxGSOne bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.018181818181818393, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);

    r = 3;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.05421487603305821, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);

    r = 10;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.779747479410557, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);

    r = 50;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(16.974358520688202, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), 0.001);

    r = 100;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(53.77951073507844, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), 0.001);
  }

  public void testCalcBoxesErrorsL()
  {
    double eps = 1e-7;
    int na = 10; // num of alleles

    int r = 2;
    int nLoci = 2;
    FsrLBoundApproxGSOne bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(3.305785123968523E-4, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);

    r = 3;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(9.916262550363835E-4, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);

    r = 10;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.01486292678304006, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);

    r = 25;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.0989226611317073, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), 0.00001);

    r = 30;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), 0.01);

    r = 50;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.4028250421439361, bound.calcNumErrors(true), eps);
  }

  public void testCalcBoxesErrorsApprox()
  {
    double eps = 1e-7;
    int na = 10; // num of alleles

    int r = 2;
    int nLoci = 2;
    FsrLBoundApproxGSOne bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(3.305785123968523E-4, bound.calcNumErrors(true), EPS);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);


    r = 3;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(9.916262550363835E-4, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);

    r = 10;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.01486292678304006, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), eps);

    r = 25;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.0989226611317073, bound.calcNumErrors(true), eps);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), 0.00001);

    r = 30;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(bound.calcNumErrors(true), bound.calcNumErrors(false), 0.01);

    r = 50;
    bound = new FsrLBoundApproxGSOne(r, na, nLoci);
    assertEquals(0.4028250421439361, bound.calcNumErrors(true), eps);
  }
}
