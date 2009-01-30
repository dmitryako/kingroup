package tomsk.project;
import tomsk.view.tomsk3d.Tomsk3dModel;
import tsvlib.project.Project;
import tsvlib.project.ProjectLogger;
import tsvlib.project.ProjectModel;

import javax.textx.FractionDigitsModel;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 20, 2004, Time: 3:34:21 PM
 */
public class Tomsk extends Project {
  private final static ProjectLogger log = ProjectLogger.getLogger(Tomsk.class);
  private String pdbFileName;
  private int intTest;
  private FractionDigitsModel digitsModel;
  private Tomsk3dModel tomsk3dModel;
  private String imageFileName;

  public Tomsk() {
    init();
  }

  private void init() {
    setPdbFileName("E:\\dev\\chem\\tomsk\\data\\PDB\\CC(=O)C.pdb");

    FractionDigitsModel digits = new FractionDigitsModel();
    digits.loadDefault();
    setDigitsModel(digits);

    Tomsk3dModel t3d = new Tomsk3dModel();
    t3d.loadDefault();
    setTomsk3dModel(t3d);
  }

  public void loadDefault(String appName, String appVersion) {
    log.trace("loadDefault(", appName, appVersion);
    super.loadDefault(appName, appVersion);
    setIntTest(11);
    setPdbFileName("E:\\dev\\chem\\tomsk\\data\\PDB\\CC(=O)C.pdb");

    digitsModel.loadDefault();
    tomsk3dModel.loadDefault();

    setErrorLogFileName("tomsk_error_log.txt");
    setProjectFileExtension("tpj");
    setImageFileName("image.jpg");
  }
  public void copyTo(ProjectModel to) {
    super.copyTo(to);
    if (!(to instanceof Tomsk)) {
      log.severe("Copy destination is not a TOMSK object");
      return;
    }
    Tomsk model = (Tomsk) to;
    model.setIntTest(getIntTest());
    model.setPdbFileName(getPdbFileName());
    model.setDigitsModel(getDigitsModel());
    model.setImageFileName(getImageFileName());
    model.setTomsk3dModel(getTomsk3dModel());
  }

  final public void setIntTest(int v) {
    intTest = v;
  }

  final public int getIntTest() {
    return intTest;
  }

  public String getPdbFileName() {
    return pdbFileName;
  }

  public void setPdbFileName(String pdbFileName) {
    this.pdbFileName = pdbFileName;
  }

  public FractionDigitsModel getDigitsModel() {
    return digitsModel;
  }

  public void setDigitsModel(FractionDigitsModel digitsModel)
  {
    this.digitsModel = digitsModel;
  }

  public Tomsk3dModel getTomsk3dModel()
  {
    return tomsk3dModel;
  }

  public void setTomsk3dModel(Tomsk3dModel tomsk3dModel)
  {
    this.tomsk3dModel = tomsk3dModel;
  }

  public String getImageFileName()
  {
    return imageFileName;
  }

  public void setImageFileName(String imageFileName)
  {
    this.imageFileName = imageFileName;
  }
}