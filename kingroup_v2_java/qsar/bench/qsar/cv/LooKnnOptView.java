package qsar.bench.qsar.cv;
import qsar.bench.QBench;
import qsar.bench.qsar.MLR.MlrKnnOptView;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 25/04/2007, 16:59:55
 */
public class LooKnnOptView  extends MlrKnnOptView
{
  private final static ProjectLogger log = ProjectLogger.getLogger(LooKnnOptView.class);

  public LooKnnOptView(QBench project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  protected void assemble() {
    endRow(diplayView);
    endRow(makeKnnPanel());
  }
}
