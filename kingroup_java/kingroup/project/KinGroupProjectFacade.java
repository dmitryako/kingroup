package kingroup.project;
import kingroup.genetics.KinPairSimModel;
import kingroup.genetics.PartitionSearchModel;
import kingroup.model.HypothesisModel;
import kingroup.model.RatioModel;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.partition.PartitionModel;

import javax.iox.LOG;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 27, 2004, Time: 11:09:54 AM
 */

// This class is a collection of facade attributes
public class KinGroupProjectFacade extends KinGroupProjectModel {
  private PartitionModel partition_;
  private RatioModel ratio_;
//   private AccuracyModel accuracy_;

//   public void loadDefault(String appName, String appVersion) {
//      super.loadDefault(appName, appVersion);
//   }

//   final public AccuracyModel getKinGroupAccuracyModel() {return accuracy_;}
  final public PartitionModel getPartitionModel() {
    return partition_;
  }
  final public RatioModel getRatioModel() {
    return ratio_;
  }
  public void setPrimModel(HypothesisModel b) {
    super.setPrimModel(b);
    partition_ = new PartitionModel(this);
//      accuracy_ = new AccuracyModel(this);
    ratio_ = new RatioModel(this);
  }
  public void setNullModel(HypothesisModel b) {
    super.setNullModel(b);
    partition_ = new PartitionModel(this);
//      accuracy_ = new AccuracyModel(this);
    ratio_ = new RatioModel(this);
  }
  public void setKinGroupSearchModel(PartitionSearchModel v) {
    super.setKinGroupSearchModel(v);
    partition_ = new PartitionModel(this);
  }
  public void setSimulationModel(KinPairSimModel v) {
    super.setSimulationModel(v);
    ratio_ = new RatioModel(this);
  }
  public void setButlerPopModel(ButlerPopBuilderModel v) {
    super.setButlerPopModel(v);
//      accuracy_ = new AccuracyModel(this);
  }
//   public void setAccuracySimModel(AccuracySimModel v) {
//      super.setAccuracySimModel(v);
//      accuracy_ = new AccuracyModel(this);
//   }
  final public void setLastSavedResultsFile(File file) {
    try {
      if (file == null)
        setLastSavedResultsFileName("");
      else
        setLastSavedResultsFileName(file.getCanonicalPath());
    }
    catch (FileNotFoundException e) {
      LOG.error(this, ": ", e);
    }
    catch (IOException e) {
      LOG.error(this, ": ", e);
    }
  }
  final public File getLastSavedResultsFile() {
    String name = getLastSavedResultsFileName();
    if (name == null && name.length() == 0)
      return null;
    return new File(name);
  }
  final public void setLastImportedKinshipFile(File file) {
    try {
      if (file == null)
        setLastImportedKinshipFileName("");
      else
        setLastImportedKinshipFileName(file.getCanonicalPath());
    }
    catch (FileNotFoundException e) {
      LOG.error(this, ": ", e);
    }
    catch (IOException e) {
      LOG.error(this, ": ", e);
    }
  }
  final public File getLastImportedKinshipFile() {
    String name = getLastImportedKinshipFileName();
    if (name == null && name.length() == 0)
      return null;
    return new File(name);
  }
}
