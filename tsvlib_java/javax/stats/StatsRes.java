package javax.stats;
import javax.utilx.pair.DoublePair;
import java.util.ArrayList;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 18, 2004, Time: 9:09:59 AM
 */
public class StatsRes {
  private double mean;
  private double s2;
  private double[][] arr;
  private int[] idxArr;
  private double mse;
  private double tmse;
  private double mae;
  private double tmae;
  private double medAE;
//  private double mad;
  private double f;
  private double ra2;
  private double r2;
  private double robCorr;
  private double corr;
  private double msr;
  private double mst;
  private ArrayList<DoublePair> graph;

  public double getMean() {
    return mean;
  }
  public void setMean(double mean) {
    this.mean = mean;
  }
  public double getS() {
    return Math.sqrt(s2);
  }
  public double getS2()
  {
    return s2;
  }

  public void setS2(double s2)
  {
    this.s2 = s2;
  }
  public String toString() {
    return "mean=" + (float)mean + ", s=" + (float)getS() + ", s2=" + (float)getS2()
      + ", r2=" + (float)getR2()
      ;
  }

  public void setArr(double[][] arr)
  {
    this.arr = arr;
  }

  public double[][] getArr()
  {
    return arr;
  }

  public void setIdxArr(int[] idxArr)
  {
    this.idxArr = idxArr;
  }

  public int[] getIdxArr()
  {
    return idxArr;
  }

  public void setR2(double r2)
  {
    this.r2 = r2;
  }

  public double getR2()
  {
    return r2;
  }

  public void setMse(double mse)
  {
    this.mse = mse;
  }

  public double getRmse()
  {
    return Math.sqrt(mse);
  }
  public double getMse()
  {
    return mse;
  }
  public void setMae(double mae)
  {
    this.mae = mae;
  }

  public double getMae()
  {
    return mae;
  }

  public double getTmae()
  {
    return tmae;
  }

  public double getTmse()
  {
    return tmse;
  }

  public void setF(double f)
  {
    this.f = f;
  }

  public double getF()
  {
    return f;
  }

  public void setRa2(double ra2)
  {
    this.ra2 = ra2;
  }

  public double getRa2()
  {
    return ra2;
  }

  public double getMsr()
  {
    return msr;
  }

  public void setMsr(double msr)
  {
    this.msr = msr;
  }

  public double getMst()
  {
    return mst;
  }

  public void setMst(double mst)
  {
    this.mst = mst;
  }

  public void add(StatsRes from)
  {
    mean += from.mean;
    medAE += from.medAE;
    mse += from.mse;
    msr += from.msr;
    mst += from.mst;
    mae += from.mae;
    f += from.f;
    corr += from.corr;
    ra2 += from.ra2;
    r2 += from.r2;
    s2 += from.s2;
    robCorr += from.robCorr;
  }

  public void norm(int n)
  {
    mean /= n;
    medAE /= n;
    mse /= n;
    msr /= n;
    mst /= n;
    mae /= n;
    f /= n;
    ra2 /= n;
    r2 /= n;
    s2 /= n;
    corr /= n;
    robCorr /= n;
  }

  public void save(double x, double y)
  {
    if (graph == null)
      graph = new ArrayList<DoublePair>();
    DoublePair point = new DoublePair(x, y);
    graph.add(point);
  }

  public ArrayList<DoublePair> getGraph()
  {
    return graph;
  }

  public double[][] getGraphAsArr()
  {
    if (graph == null)
      return new double[0][0];
    double[][] res = new double[graph.size()][2];
    for (int i = 0; i < res.length; i++) {
      DoublePair p = graph.get(i);
      res[i][0] = p.a;
      res[i][1] = p.b;
    }
    return res;
  }

  public double getMedAE()
  {
    return medAE;
  }

  public void setMedAE(double medAE)
  {
    this.medAE = medAE;
  }

  public void setTmse(double tmse)
  {
    this.tmse = tmse;
  }

  public void setTmae(double tmae)
  {
    this.tmae = tmae;
  }

  public double getRobCorr()
  {
    return robCorr;
  }
  public double getRobR2()
  {
    return robCorr * robCorr;
  }

  public void setRobCorr(double robCorr)
  {
    this.robCorr = robCorr;
  }

  public void setCorr(double corr)
  {
    this.corr = corr;
  }

  public double getCorr()
  {
    return corr;
  }
}
