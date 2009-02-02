package qsar.bench.qsar.view;
import qsar.bench.QBench;
import qsar.bench.qsar.QsarTableTypeView;
import qsar.bench.view.QBenchViewI;

import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 13:55:40
 */
public class QsarAlgOptView  extends GridBagView
  implements QBenchViewI
{
  protected QsarTableTypeView tableOptView;
  protected QsarAlgDisplayOptView diplayView;

  public QsarAlgOptView()
  {
    init();
  }
  public QsarAlgOptView(QBench project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  protected void init()
  {
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
    getEndRow().anchor = GridBagConstraints.NORTHWEST;
  }
  public void setEnabledTrain(boolean b) {
    tableOptView.setEnabledTrain(b);
  }
  public void setEnabledTest(boolean b) {
    tableOptView.setEnabledTest(b);
  }
  public void setEnabledPredict(boolean b) {
    tableOptView.setEnabledPredict(b);
  }
  public void loadTo(QBench model) {
    diplayView.loadTo(model);
    tableOptView.loadTo(model);
  }
  public void loadFrom(QBench model) {
    tableOptView = new QsarTableTypeView(model);
    diplayView = new QsarAlgDisplayOptView(model);
  }
  protected void assemble() {
    endRow(tableOptView);
    endRow(diplayView);
  }
//  public void onOptionChange(UCController uc) {
//    specieView.onSpecieChange(uc);
//    displayView.onDisplayViewChange(uc);
//    displayView.onSortByGroupChange(uc);
//  }
  public Dimension getMinimumSize() {
    int EXTRA_SIZE = 20;
    Dimension d = getPreferredSize();
    return new Dimension(d.width + EXTRA_SIZE, d.height + EXTRA_SIZE);
  }
}
