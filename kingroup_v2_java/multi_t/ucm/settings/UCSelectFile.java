package multi_t.ucm.settings;
import multi_t.MultiT;
import multi_t.MultiTFrame;
import multi_t.MultiTProject;
import multi_t.project.ProjectSettingsUI;
import pattern.ucm.UCController;

import javax.iox.FileX;
import javax.swingx.OpenFileUI;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/11/2006, Time: 17:03:51
 */
public class UCSelectFile  extends OpenFileUI
  implements UCController {
  protected ProjectSettingsUI parentView;
  public UCSelectFile(ProjectSettingsUI view)
  {
    parentView = view;
  }

  public boolean run() {
    MultiT model = MultiTProject.getInstance();
    String name = getFileName(model);  // overwitten!!
    File file = model.makeFile(name);
    file = selectFile(MultiTFrame.getInstance(), file);
    if (file == null)
      return false;
    setFileName(model, FileX.getFileName(file));  // overwitten!!
    model.saveProjectToDefaultLocation();
    if (parentView != null)  {
      parentView.loadFrom(model);
      parentView.repaint();
    }
    return true;
  }
  protected void setFileName(MultiT model, String fileName)  {
  }
  protected String getFileName(MultiT model)   {
    return null;
  }
}
