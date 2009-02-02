package multi_t.ucm.settings;
import multi_t.MultiT;
import multi_t.MultiTMainUI;
import multi_t.MultiTProject;
import multi_t.project.ProjectSettingsUI;
import pattern.ucm.UCController;

import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/11/2006, Time: 17:33:33
 */
public class UCShowSettingsUI implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCShowSettingsUI.class.getName());
  public boolean run() {
    MultiT project = MultiTProject.getInstance();
    ProjectSettingsUI view = new ProjectSettingsUI(project);
    view.runOnReferenceBttn(new UCSelectReferenceFile(view));

    view.runOnPrimerBttn(new UCSelectPrimerFile(view, new UCReadPrimerFile(view)));
    view.runOnEnzymeBttn(new UCSelectEnzymeFile(view));
    view.runOnProfileBttn(new UCSelectProfileFile(view));

    view.runOnRefreshBttn(new UCRefreshSettingsUI());
    view.runOnSaveBttn(new UCSaveSettings());

    MultiTMainUI ui = MultiTMainUI.getInstance();
    ui.resetAll();
    ui.setSettingsView(view);

    return true;
  }
}
