package multi_t.ucm.settings;
import multi_t.MultiT;
import multi_t.project.ProjectSettingsUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/11/2006, Time: 16:23:31
 */
public class UCSelectEnzymeFile  extends UCSelectFile {
  public UCSelectEnzymeFile(ProjectSettingsUI view)  {
    super(view);
  }
  protected String getFileName(MultiT model)  {
    return model.getEnzymeFileName();
  }
  protected void setFileName(MultiT model, String fileName)  {
    model.setEnzymeFileName(fileName);
  }
}

