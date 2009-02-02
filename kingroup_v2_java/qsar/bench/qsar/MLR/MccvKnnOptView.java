package qsar.bench.qsar.MLR;
import qsar.bench.QBench;
import qsar.bench.qsar.cv.mccv.MccvOptView;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 20/04/2007, 17:05:41
 */
public class MccvKnnOptView  extends MlrKnnOptView
{
  private final static ProjectLogger log = ProjectLogger.getLogger(MccvKnnOptView.class);
  private MccvOptView mccv;

  public MccvKnnOptView(QBench project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  public void loadTo(QBench project) {
    super.loadTo(project);
    mccv.loadTo(project);
  }
  public void loadFrom(QBench project) {
    super.loadFrom(project);
    mccv = new MccvOptView(project);
  }
  protected void assemble() {
    endRow(diplayView);
    endRow(makeKnnPanel());
    endRow(mccv);
  }
}
