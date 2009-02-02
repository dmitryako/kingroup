package qsar.bench.ucm.alg.MLR;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.qsar.MLR.MlrSelectAlgView;
import tsvlib.project.ProjectLogger;

import javax.swingx.ApplyDialogUI;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 13:13:19
 */
public class UCSelectMlrAlg
{
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSelectMlrAlg.class);

  public static boolean select(MlrSelectAlgView panel, QBench project) {
    ApplyDialogUI dlg = new ApplyDialogUI(panel, QBenchFrame.getInstance(), true);
    dlg.setTitle("Select");
    dlg.center();
    dlg.setFocusOnApply();
    dlg.setVisible(true);
    if (!dlg.apply()) {
      return false;
    }
    panel.loadTo(project);
    project.saveProjectToDefaultLocation();
    return true;
  }
}
