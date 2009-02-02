package qsar.bench.ucm.alg.cv;
import pattern.ucm.UCUpdateTable;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.cv.mcvs.McvsAlg;
import qsar.bench.qsar.cv.mcvs.McvsAlgFactory;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swingx.tablex.TableViewWithOpt;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 6/06/2007, 14:40:40
 */
public class UCRunMcvs implements UCUpdateTable {
  private static ProjectLogger log = ProjectLogger.getLogger(UCRunMcvs.class);

  private TableViewWithOpt updateView;
  private final QBenchViewI optView;
  private final QsarAlgFactory algFactory;
  private final McvsAlgFactory mcvsFactory;

  public UCRunMcvs(QBenchViewI optView, QsarAlgFactory alg, McvsAlgFactory mcvs) {
    this.optView = optView;
    algFactory = alg;
    this.mcvsFactory = mcvs;
  }
  public boolean run() {
    log.trace("run()");
    QBench project = QBenchProject.getInstance();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

    QBenchMainUI ui = QBenchMainUI.getInstance();
    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      ui.showLoadTrainTableFirst();
      return false;
    }

//    McvsSaAlg alg = new McvsSaAlg(project, algFactory, updateView);
    McvsAlg mcvs = mcvsFactory.makeAlg(project, algFactory, updateView);
    mcvs.calcStats(zTrain, true);
    return true;
  }

  public void setUpdateView(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}


