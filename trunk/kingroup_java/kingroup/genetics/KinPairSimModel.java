package kingroup.genetics;
import kingroup.model.RunSimModel;
public class KinPairSimModel extends RunSimModel {
  private boolean performSigTest;
  private float[] pLevels;
  private boolean displaySigFlag;
  public KinPairSimModel() {
  }
  public KinPairSimModel(KinPairSimModel from) {
    from.copyTo(this);
  }
  final public void loadDefaults() {
    setRp(0.5);
    setRm(0.5);
    setXHaploidChecked(false);
    setXDiploidChecked(true);
    setYHaploidChecked(false);
    setYDiploidChecked(true);
    setMaternal(false);
    setPaternal(false);
    setNumRuns(1000);
  }
  final public void setPerformSigTest(boolean b) {
    performSigTest = b;
  }
  final public boolean getPerformSigTest() {
    return performSigTest;
  }
  final public void setPLevels(float[] a) {
    pLevels = a;
  }
  final public float[] getPLevels() {
    return pLevels;
  }
  final public void setDisplaySigFlag(boolean b) {
    displaySigFlag = b;
  }
  final public boolean getDisplaySigFlag() {
    return displaySigFlag;
  }
  private boolean xHaploidChecked;
  public void setXHaploidChecked(boolean b) {
    xHaploidChecked = b;
  }
  public boolean getXHaploidChecked() {
    return (xHaploidChecked);
  }
  private boolean xDiploidChecked;
  public void setXDiploidChecked(boolean b) {
    xDiploidChecked = b;
  }
  public boolean getXDiploidChecked() {
    return (xDiploidChecked);
  }
  private boolean yHaploidChecked;
  public void setYHaploidChecked(boolean b) {
    yHaploidChecked = b;
  }
  public boolean getYHaploidChecked() {
    return (yHaploidChecked);
  }
  private boolean yDiploidChecked;
  public void setYDiploidChecked(boolean b) {
    yDiploidChecked = b;
  }
  public boolean getYDiploidChecked() {
    return (yDiploidChecked);
  }
  private boolean maternal;
  public void setMaternal(boolean b) {
    maternal = b;
  }
  public boolean getMaternal() {
    return (maternal);
  }
  private boolean paternal;
  public void setPaternal(boolean b) {
    paternal = b;
  }
  public boolean getPaternal() {
    return (paternal);
  }
  private double rm;
  public void setRm(double s) {
    rm = s;
  }
  public double getRm() {
    return rm;
  }
  private double rp;
  public void setRp(double s) {
    rp = s;
  }
  public double getRp() {
    return rp;
  }
  public void copyTo(KinPairSimModel bean) {
    bean.setNumRuns(getNumRuns());
    bean.setPerformSigTest(getPerformSigTest());
    bean.setPLevels(getPLevels());
    bean.setDisplaySigFlag(getDisplaySigFlag());
    bean.setRp(getRp());
    bean.setXHaploidChecked(getXHaploidChecked());
    bean.setXDiploidChecked(getXDiploidChecked());
    bean.setYHaploidChecked(getYHaploidChecked());
    bean.setYDiploidChecked(getYDiploidChecked());
    bean.setMaternal(getMaternal());
    bean.setPaternal(getPaternal());
    bean.setRm(getRm());
  }
}
