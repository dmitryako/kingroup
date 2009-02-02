package qsar.bench.ucm;
import qsar.bench.view.QBenchViewI;
import qsar.bench.QBench;
import qsar.bench.QBenchProject;
import qsar.bench.QBenchFrame;
import pattern.ucm.UCController;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 1/04/2007, 10:22:18
 */
public class UCLoadToProjectAndReload implements UCController
{
  private QBenchViewI view;
  public UCLoadToProjectAndReload(QBenchViewI view)
  {
    this.view = view;
  }

  public boolean run()
  {
    QBench project = QBenchProject.getInstance();
    view.loadTo(project);
    project.saveProjectToDefaultLocation();

    view.loadFrom(project);
    if (QBenchFrame.getInstance() != null)
      QBenchFrame.getInstance().repaint();
    return true;
  }
}
