package qsar.bench.ucm.LFER;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.descriptors.LFER.LferAlgUI;
import qsar.bench.descriptors.LFER.LferPlattsOptView;
import qsar.bench.descriptors.LFER.LferPlattsAlgView;
import qsar.bench.ucm.UCLoadToProjectAndReload;
import pattern.ucm.UCController;

import javax.iox.StrTable;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 30/03/2007, 13:59:36
 */
public class UCShowLferPlattsUI  implements UCController {
  public boolean run() {
    QBench project = QBenchProject.getInstance();
    QBenchMainUI ui = QBenchMainUI.getInstance();
    StrTable table = ui.getSmilesTable();
    if (table == null   ||  table.getNumRows() == 0) {
      ui.showLoadSmilesTableFirst();
      return false;
    }

    LferAlgUI lferUI = ui.getLferView();
    if (lferUI == null)
      lferUI = new LferAlgUI();
    ui.setLferView(lferUI);

    LferPlattsOptView optView = new LferPlattsOptView(project);
    UCRunLferPlatts runOnApply = new UCRunLferPlatts(optView);
    LferPlattsAlgView algView = new LferPlattsAlgView(optView, project, runOnApply);
    runOnApply.setUpdateView(algView);

    UCLoadToProjectAndReload reloadUC = new UCLoadToProjectAndReload(algView);
    optView.setRunOnDigitChange(reloadUC);
    optView.setRunOnDisplayChange(reloadUC);

    lferUI.setPlattsView(algView);
    return true;
  }
}

