package qsar.bench.ucm.LFER;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.iox.StrTable;
import javax.swingx.tablex.TableViewWithOpt;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 30/03/2007, 14:32:49
 */
public class UCRunLferPlatts  implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCRunLferPlatts.class.getName());

  private TableViewWithOpt updateView;
  private final QBenchViewI optView;
//  private final QsarAlgFactory algFactory;

  public UCRunLferPlatts(QBenchViewI optView) {
    this.optView = optView;
  }
  public boolean run() {
    QBench project = QBenchProject.getInstance();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    QBenchMainUI ui = QBenchMainUI.getInstance();
    StrTable smiles = ui.getSmilesTable();
    if (smiles == null   ||  smiles.getNumRows() == 0) {
      ui.showLoadSmilesTableFirst();
      return false;
    }

//    QsarAlg alg = algFactory.makeAlg(project);
//    double[] yp = alg.train(smiles.getByRows());
//    if (yp == null) {
//      UCShowErrorMssg.showMessageDialog(ui, alg.getLastError());
//      return false;
//    }
//    JTable tableView = new QsarYZView(alg.getName(), yp, smiles
//      , project.getDigitsModel()).getTableView();
    if (updateView != null) {
//      updateView.setTable(tableView);
      updateView.assembleWithOpt();
    }
    return true;
  }


  public void setUpdateView(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}
