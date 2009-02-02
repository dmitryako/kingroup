package qsar.bench.qsar.cv.mccv;
import qsar.bench.QBench;
import qsar.bench.qsar.view.QsarAlgOptView;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 20/04/2007, 14:55:13
 */
public class MccvMlrOptView extends QsarAlgOptView
{
  private final static ProjectLogger log = ProjectLogger.getLogger(MccvMlrOptView.class);
  private MccvOptView mccv;
  private MccvDisplayOptView mccvOpt;

  public MccvMlrOptView(QBench project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  public void loadTo(QBench project) {
    super.loadTo(project);
    mccv.loadTo(project);
    mccvOpt.loadTo(project);
  }
  public void loadFrom(QBench project) {
    super.loadFrom(project);
    mccv = new MccvOptView(project);
    mccvOpt = new MccvDisplayOptView(project);
  }
  protected void assemble() {
    endRow(diplayView);
    endRow(mccv);
    endRow(mccvOpt);
  }
}

