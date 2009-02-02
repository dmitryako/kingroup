package multi_t;
import multi_t.pcr.Primer;
import tsvlib.project.Project;
import tsvlib.project.ProjectLogger;
import tsvlib.project.ProjectModel;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2006, Time: 16:34:51
 */
public class MultiT  extends Project {
  private final static ProjectLogger log = ProjectLogger.getLogger(MultiT.class.getName());
  private String errorLogFileName;
  private String lastImportedFileName;
  private String lastSavedFileName;

  private String referenceFileName;
  private String enzymeFileName;
  private String primerFileName;
  private String profileFileName;

  private Primer fwdPrimer;
  private Primer revPrimer;

  public MultiT() {
    init();
  }
  private void init() {
    errorLogFileName = new String();
    lastImportedFileName = new String();
    lastSavedFileName = new String();

    referenceFileName = new String();
    enzymeFileName = new String();
    primerFileName = new String("C:\\Documents and Settings\\jc138691\\My Documents\\dev\\multi_t\\testing\\primers.txt");
    profileFileName = new String();

    Primer p = new Primer();
    p.loadDefault();
    setFwdPrimer(p);

    p = new Primer();
    p.loadDefault();
    setRevPrimer(p);
  }
  public void loadDefault(String appName, String appVersion) {
    super.loadDefault(appName, appVersion);

    setErrorLogFileName("ErrorLog.txt");
  }
  public void copyTo(ProjectModel to) {
    super.copyTo(to);
    if (!(to instanceof MultiT)) {
      log.severe("Copy destination is not a MultiT object");
    }
    MultiT bean = (MultiT) to;

    bean.setReferenceFileName(getReferenceFileName());
    bean.setEnzymeFileName(getEnzymeFileName());
    bean.setPrimerFileName(getPrimerFileName());
    bean.setProfileFileName(getProfileFileName());

    bean.setErrorLogFileName(getErrorLogFileName());
    bean.setLastImportedFileName(getLastImportedFileName());
    bean.setLastSavedFileName(getLastSavedFileName());
    bean.setLastProjectFileName(getLastProjectFileName());

    bean.setFwdPrimer(getFwdPrimer());
    bean.setRevPrimer(getRevPrimer());
  }
  public String getLastImportedFileName() {
    return lastImportedFileName;
  }
  public void setLastImportedFileName(String lastImportedFileName) {
    this.lastImportedFileName = lastImportedFileName;
  }
  public String getLastSavedFileName() {
    return lastSavedFileName;
  }
  public void setLastSavedFileName(String lastSavedFileName) {
    this.lastSavedFileName = lastSavedFileName;
  }

  public String getErrorLogFileName()
  {
    return errorLogFileName;
  }

  public void setErrorLogFileName(String errorLogFileName)
  {
    this.errorLogFileName = errorLogFileName;
  }

  public String getReferenceFileName()
  {
    return referenceFileName;
  }

  public void setReferenceFileName(String referenceFileName)
  {
    this.referenceFileName = referenceFileName;
  }

  public String getEnzymeFileName()
  {
    return enzymeFileName;
  }

  public void setEnzymeFileName(String enzymeFileName)
  {
    this.enzymeFileName = enzymeFileName;
  }

  public String getPrimerFileName()
  {
    return primerFileName;
  }

  public void setPrimerFileName(String primerFileName)
  {
    this.primerFileName = primerFileName;
  }

  public String getProfileFileName()
  {
    return profileFileName;
  }

  public void setProfileFileName(String profileFileName)
  {
    this.profileFileName = profileFileName;
  }

  public Primer getFwdPrimer()
  {
    return fwdPrimer;
  }

  public void setFwdPrimer(Primer fwdPrimer)
  {
    this.fwdPrimer = fwdPrimer;
  }

  public Primer getRevPrimer()
  {
    return revPrimer;
  }

  public void setRevPrimer(Primer revPrimer)
  {
    this.revPrimer = revPrimer;
  }
}

