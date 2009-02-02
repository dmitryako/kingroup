package qsar.bench.qsar.view;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.view.QBenchViewI;

import javax.swingx.panelx.GridBagView;
import javax.textx.FractionDigitsView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 19/03/2007, 13:43:42
 */
public class QsarAlgDisplayOptView extends GridBagView
  implements QBenchViewI {
  protected FractionDigitsView digitsView;
  public QsarAlgDisplayOptView() {
    super("Display");
    init();
  }
  public QsarAlgDisplayOptView(QBench model) {
    super("Display");
    init();
    initFrom(model);
    assemble();
  }
  private void init() {
  }
  public void loadTo(QBench model) {
    digitsView.loadTo(model.getDigitsModel());
  }
  private void initFrom(QBench model) {
    digitsView = new FractionDigitsView(model.getDigitsModel());
  }
  public void loadFrom(QBench model) {
    digitsView.loadFrom(model.getDigitsModel());
  }
  private void assemble() {
    endRow(digitsView);
  }
  public void setRunOnDigitChange(UCController uc) {
    digitsView.setRunOnChange(uc);
  }
}

