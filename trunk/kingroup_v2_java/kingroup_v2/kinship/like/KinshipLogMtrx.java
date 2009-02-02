package kingroup_v2.kinship.like;
import kingroup.genetics.Like;
import kingroup_v2.Kingroup;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopMtrxI;
import tsvlib.project.ProjectLogger;

import javax.vecmathx.matrix.LowTriangleMatrix;
import javax.vecmathx.matrix.MtrxReadI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/03/2006, Time: 14:51:01
 */
public class KinshipLogMtrx
  implements SysPopMtrxI
{
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  private final LowTriangleMatrix mtrx;
  protected final SysPop pop;
  private String name;
  private KinshipLikeCalculatorI calc;

  public KinshipLogMtrx(SysPop pop) {
    mtrx = new LowTriangleMatrix(pop.size());
    this.pop = pop;
  }
  public boolean calcComplexNull(Kingroup kingroup) {
    throw new RuntimeException("Must overwite KinshipLogMtrx.calcComplexNull()");
  }
  public void setCalc(KinshipLikeCalculatorI calc) { this.calc = calc;}
  protected void loadRatio(MtrxReadI primMtrx, MtrxReadI nulMtrx) {
    for (int r = 0; r < size(); r++) {
      for (int c = 0; c < r; c++) {
        double primLog = primMtrx.get(c, r);
        double nullLog = nulMtrx.get(c, r);
        if (primLog == Like.MIN_LOG)  {
          setLog(c, r, Like.MIN_LOG);
        }
        else if (nullLog == Like.MIN_LOG)
          setLog(c, r, Like.MAX_LOG);
        else
          setLog(c, r, primLog - nullLog);
      }
    }
  }
  public void init(double v) {    mtrx.init(v);}

  public SysPop getPop()
  {
    return pop;
  }

  public int getId(int idx) {
    return pop.getIdIdx(idx);
  }
  final protected void calcMaxLog(KinshipLikeCalculatorI c) {
    setCalc(c);
    for (int i = 0; i < pop.size(); i++) {
      for (int k = i + 1; k < pop.size(); k++) {
        double oldLog = getLog(i, k);
        double newLog = calcLog(i, k);
        if (oldLog < newLog)
          setLog(i, k, newLog);
      }
    }
  }
  public boolean calcLogs(KinshipLikeCalculatorI c) {
//  public boolean calcLogs() {
    setCalc(c);
//    log.info("pop=\n"+pop);
    for (int i = 0; i < pop.size(); i++) {
      for (int k = i + 1; k < pop.size(); k++) {
        double sum = calcLog(i, k);
        setLog(i, k, sum); // i < k // only the above diagonal elements are calculated
      }
    }
    return true;
  }
  final public double calcLog(int i, int k) {
    double sum = 0;
    for (int L = 0; L < pop.getNumLoci(); L++) {
      double d = calc.calcLocusLog(i, k, L);
//      log.info("L=" + L + " [" + i + ", " + k + "]=" + (float)d);
      if (d == Like.IGNORE)
        continue;
      if (d == Like.MIN_LOG) {
        sum = Like.MIN_LOG;
        break;
      }
      sum += d;
    }
    return sum;
  }
  public int size() {
    return mtrx.size();
  }

  public int getStorageSize()
  {
    return mtrx.getStorageSize();
  }
// PRECOND: c < size  &&  r < size
  final public double getLog(int r, int c) {
    if (r == c)
      return Like.MIN_LOG; // NOTE!!! This is used in KinshipLikeMtrxV1.sort()???
    return mtrx.get(r, c);
  }
// PRECOND: c < size  &&  r < size
  final public void setLog(int r, int c, double val) {
//    log.info("["+r+", "+c+"]="+(float)val);
    mtrx.set(r, c, val);
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public double get(int r, int c)
  {
    return getLog(r, c);
  }
}
