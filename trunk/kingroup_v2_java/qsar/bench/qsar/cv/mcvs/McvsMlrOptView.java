package qsar.bench.qsar.cv.mcvs;
import qsar.bench.QBench;
import qsar.bench.qsar.cv.mccv.MccvOptView;
import qsar.bench.qsar.view.QsarAlgOptView;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 6/06/2007, 11:24:50
 */
public class McvsMlrOptView extends QsarAlgOptView
{
  private final static ProjectLogger log = ProjectLogger.getLogger(McvsMlrOptView.class);
  private MccvOptView mccv;
  private McvsOptView mcvs;

  public McvsMlrOptView(QBench project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  public void loadTo(QBench project) {
    super.loadTo(project);
    mccv.loadTo(project);
    mcvs.loadTo(project);
  }
  public void loadFrom(QBench project) {
    super.loadFrom(project);
    mccv = new MccvOptView(project);
    mcvs = new McvsOptView(project);
  }
  protected void assemble() {
    endRow(diplayView);
    endRow(mccv);
    endRow(mcvs);
  }
}

