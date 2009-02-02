package multi_t.ucm.settings;
import multi_t.MultiT;
import multi_t.project.ProjectSettingsUI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/11/2006, Time: 16:23:50
 */
public class UCSelectProfileFile extends UCSelectFile {
  public UCSelectProfileFile(ProjectSettingsUI view)  {
    super(view);
  }
  protected String getFileName(MultiT model)  {
    return model.getProfileFileName();
  }
  protected void setFileName(MultiT model, String fileName)  {
    model.setProfileFileName(fileName);
  }
}

