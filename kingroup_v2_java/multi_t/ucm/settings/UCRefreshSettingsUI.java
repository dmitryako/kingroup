package multi_t.ucm.settings;
import multi_t.MultiTMainUI;
import multi_t.project.ProjectSettingsUI;
import pattern.ucm.UCController;

import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/11/2006, Time: 11:46:26
 */
public class UCRefreshSettingsUI  implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCRefreshSettingsUI.class.getName());
  public boolean run() {
    MultiTMainUI ui = MultiTMainUI.getInstance();
    ProjectSettingsUI view = ui.getSettingsView();
    new UCReadPrimerFile(view).run();
    return true;
  }
}

