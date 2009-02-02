package kingroup_v2.cervus;

import kingroup_v2.cervus.tools.CervusParentageFileFormat;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/01/2006, Time: 16:56:43
 */
public class Cervus {
  public final static String REFERENCE = "Marshall et al (1998) Molecular Ecology 7 p639";
  private CervusFileFormat fileFormat;
  private CervusParentageFileFormat parentageFormat;
  private boolean lociByCol;

  public Cervus() {
    init();
  }
  private void init() {
    CervusFileFormat file = new CervusFileFormat();
    file.loadDefault();
    setFileFormat(file);
    setLociByCol(false);

    CervusParentageFileFormat p = new CervusParentageFileFormat();
    p.loadDefaults();
    setParentageFormat(p);
  }
  public void loadDefault() {
    fileFormat.loadDefault();
  }
  public CervusFileFormat getFileFormat() {
    return fileFormat;
  }
  public void setFileFormat(CervusFileFormat fileFormat) {
    this.fileFormat = fileFormat;
  }

  public CervusParentageFileFormat getParentageFormat() {
    return parentageFormat;
  }

  public void setParentageFormat(CervusParentageFileFormat parentageFormat) {
    this.parentageFormat = parentageFormat;
  }

  public void setLociByCol(boolean lociByCol) {
    this.lociByCol = lociByCol;
  }

  public boolean getLociByCol() {
    return lociByCol;
  }
}
