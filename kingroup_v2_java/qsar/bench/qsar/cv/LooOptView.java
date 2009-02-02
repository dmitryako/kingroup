package qsar.bench.qsar.cv;
import qsar.bench.QBench;
import qsar.bench.qsar.view.QsarAlgOptView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 20/04/2007, 16:14:53
 */
public class LooOptView extends QsarAlgOptView
{
  public LooOptView(QBench project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  protected void assemble() {
    endRow(diplayView);
  }
}
