package kingroup_v2.fsr.bound;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/11/2005, Time: 16:13:39
 */
public class FsrLBoundResults
{
  private int[] phenotypeCount;
  private double accuracyError;
  private double numErrors;
  private double[] phenotypeProb;

  public void setPhenotypeCount(int[] qm)
  {
    this.phenotypeCount = qm;
  }

  public int[] getPhenotypeCount()
  {
    return phenotypeCount;
  }

  public void setAccuracyError(double accuracyError)
  {
    this.accuracyError = accuracyError;
  }

  public double getAccuracyError()
  {
    return accuracyError;
  }

  public void setNumErrors(double numErrors)
  {
    this.numErrors = numErrors;
  }

  public double getNumErrors()
  {
    return numErrors;
  }

  public void setPhenotypeProb(double[] phenotypeProb)
  {
    this.phenotypeProb = phenotypeProb;
  }

  public double[] getPhenotypeProb()
  {
    return phenotypeProb;
  }
}
