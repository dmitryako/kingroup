package kingroup.population;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/03/2005, Time: 08:38:27
 */
public class CensusPopBuilderModel extends ButlerPopBuilderModel {
  private int numRepeats;
  private int misprintRate;
  public void loadDefaults() {
    super.loadDefaults();
    setNumReplicates(1);
    setMisprintRate(0);
  }
  public void copyTo(CensusPopBuilderModel model) {
    super.copyTo(model);
    model.setNumReplicates(getNumRepeats());
    model.setMisprintRate(getMisprintRate());
  }
  public int getNumRepeats() {
    return numRepeats;
  }
  public void setNumReplicates(int numRepeats) {
    this.numRepeats = numRepeats;
  }
  public void setMisprintRate(int misprintRate) {
    this.misprintRate = misprintRate;
  }
  public int getMisprintRate() {
    return misprintRate;
  }
}
