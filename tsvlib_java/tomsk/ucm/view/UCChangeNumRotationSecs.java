package tomsk.ucm.view;
import pattern.ucm.UCController;
import tomsk.project.Tomsk;
import tomsk.project.TomskProject;
import tomsk.view.tomsk3d.Tomsk3dModel;
import tomsk.view.tomsk3d.Tomsk3dOptView;
import tomsk.view.tomsk3d.Tomsk3dView;
import tsvlib.project.ProjectLogger;

import javax.mathx.MathX;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/05/2007, 10:37:06
 */
public class UCChangeNumRotationSecs implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCChangeNumRotationSecs.class);
  private Tomsk3dView view;
  private double scale;
  private int minChange;
  private Tomsk3dOptView optView;

  public UCChangeNumRotationSecs(Tomsk3dView view, Tomsk3dOptView optView, int minChange, double scale)
  {
    this.view = view;
    this.optView = optView;
    this.minChange = minChange;
    this.scale = scale;
  }

  public UCChangeNumRotationSecs(Tomsk3dView view, int minChange, double scale)
  {
    this.view = view;
    this.minChange = minChange;
    this.scale = scale;
  }

  public boolean run()
  {
    Tomsk project = TomskProject.getInstance();
    Tomsk3dModel model = project.getTomsk3dModel();

    int oldTime = model.getNumRotationSecs();
    log.debug("oldTime=", oldTime);
    int newTime = (int)(scale * oldTime);
    log.debug("newTime=", newTime);
    int change = newTime - oldTime;
    if (Math.abs(change) < Math.abs(minChange))
      newTime = oldTime + minChange;

    newTime = MathX.limit(newTime, model.getMinRotationSecs(), model.getMaxRotationSecs());
    model.setNumRotationSecs( newTime );
    model.setEnableRotation(true);
    project.saveProjectToDefaultLocation();

    if (optView != null) {
      optView.loadFrom(model);
    }
    view.setNumRotationSecs( newTime );
    view.setEnableRotation( true );
    return true;
  }

  public Tomsk3dOptView getOptView()
  {
    return optView;
  }

  public void setOptView(Tomsk3dOptView optView)
  {
    this.optView = optView;
  }
}


