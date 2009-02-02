package kingroup_v2.kinship;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/03/2006, Time: 13:44:36
 */
public abstract class DiploidLikeCalculator
{
  private static ProjectLogger log = ProjectLogger.getLogger(DiploidLikeCalculator.class.getName());
  private static final String DELIM = "/";
  protected double fx;
  protected double fx2;
  protected double fy;
  protected double fy2;
  protected int x;
  protected int x2;
  protected int matX;
  protected int matX2;
  protected int patX;
  protected int patX2;
  protected int y;
  protected int y2;
  protected int matY;
  protected int matY2;
  protected int patY;
  protected int patY2;

  public void initMatPat() {
    matX = matX2 = matY = matY2 = -1;
    patX = patX2 = patY = patY2 = -1;
  }
  abstract public double calcProb();
  abstract public double calcProbMatPat();
  public boolean isParent(int ca // child allele
    , int pa, int pa2) { // parent allele
    if (pa == -1 && pa2 == -1)
      return true;
    else
      return (ca == pa || ca == pa2);
  }
  public boolean isUnknownMat() {
    return (matX == -1 && matX2 == -1 && matY == -1 && matY2 == -1);
  }
  public boolean isUnknownPat() {
    return (patX == -1 && patX2 == -1 && patY == -1 && patY2 == -1);
  }
  public boolean isValidChild(int a, int a2 // child alleles
    , int pa, int pa2) { // parent alleles
    if (pa == -1 && pa2 == -1)
      return true;
    else
      return (a == pa || a == pa2)
        || (a2 == pa || a2 == pa2);
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append(" x/x2 = ").append(Integer.toString(x)).append(DELIM);
    buff.append(Integer.toString(x2));
    buff.append(", freq=").append(Float.toString((float) fx)).append(DELIM);
    buff.append(Float.toString((float) fx2));
    buff.append(", mat=").append(Integer.toString(matX)).append(DELIM);
    buff.append(Integer.toString(matX2));
    buff.append(", pat=").append(Integer.toString(patX)).append(DELIM);
    buff.append(Integer.toString(patX2)).append(SysProp.EOL);
    buff.append(" y/y2 = ").append(Integer.toString(y)).append(DELIM);
    buff.append(Integer.toString(y2));
    buff.append(", freq=").append(Float.toString((float) fy)).append(DELIM);
    buff.append(Float.toString((float) fy2));
    buff.append(", mat=").append(Integer.toString(matY)).append(DELIM);
    buff.append(Integer.toString(matY2));
    buff.append(", pat=").append(Integer.toString(patY)).append(DELIM);
    buff.append(Integer.toString(patY2)).append(SysProp.EOL);
    return buff.toString();
  }
  public void setX(int x) {
    this.x = x;
  }
  public void setX2(int x2) {
    this.x2 = x2;
  }
  public void setY(int y) {
    this.y = y;
  }
  public void setY2(int y2) {
    this.y2 = y2;
  }
  public void setFx(double fx) {
    this.fx = fx;
  }
  public void setFx2(double fx2) {
    this.fx2 = fx2;
  }
  public void setFy(double fy) {
    this.fy = fy;
  }
  public void setFy2(double fy2) {
    this.fy2 = fy2;
  }
  public void setMatX(int matX) {
    this.matX = matX;
  }
  public void setMatX2(int matX2) {
    this.matX2 = matX2;
  }
  public void setPatX(int patX) {
    this.patX = patX;
  }
  public void setPatX2(int patX2) {
    this.patX2 = patX2;
  }
  public void setMatY(int matY) {
    this.matY = matY;
  }
  public void setMatY2(int matY2) {
    this.matY2 = matY2;
  }
  public void setPatY(int patY) {
    this.patY = patY;
  }
  public void setPatY2(int patY2) {
    this.patY2 = patY2;
  }
}

