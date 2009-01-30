package tomsk.ucm.view;
import pattern.ucm.UCController;
import tomsk.project.Tomsk;
import tomsk.project.TomskProject;
import tomsk.view.tomsk3d.Tomsk3dModel;
import tomsk.view.tomsk3d.Tomsk3dOptView;
import tomsk.view.tomsk3d.Tomsk3dView;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/05/2007, 10:12:53
 */
public class UCToggleRotation implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCToggleRotation.class);
  private Tomsk3dView view;
  private Tomsk3dOptView optView;
//  private Tomsk3dModel model;

  public UCToggleRotation(Tomsk3dView view, Tomsk3dOptView optView)
  {
    this.view = view;
    this.optView = optView;
  }

  public boolean run()
  {
    Tomsk project = TomskProject.getInstance();
    Tomsk3dModel model = project.getTomsk3dModel();

    model.setEnableRotation(!view.getEnableRotation());

    view.setEnableRotation(model.getEnableRotation());
    if (optView != null) {
      optView.loadFrom(model);
    }
    return true;
  }
}
