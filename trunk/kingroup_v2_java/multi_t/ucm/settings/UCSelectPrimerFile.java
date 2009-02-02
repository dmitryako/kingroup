package multi_t.ucm.settings;
import multi_t.MultiT;
import multi_t.project.ProjectSettingsUI;
import pattern.ucm.UCController;

import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/11/2006, Time: 16:23:12
 */
public class UCSelectPrimerFile extends UCSelectFile {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSelectPrimerFile.class.getName());
  private UCController uc;
  public UCSelectPrimerFile(ProjectSettingsUI view, UCController uc)  {
    super(view);
    this.uc = uc;
  }
  protected String getFileName(MultiT model)  {
    return model.getPrimerFileName();
  }
  protected void setFileName(MultiT model, String fileName)  {
    model.setPrimerFileName(fileName);
  }
  public boolean run() {
    boolean res = super.run();
    if (!res)
      return false;

    if (uc != null)
      return uc.run();
    return true;
  }
}
