package qsar.bench.qsar.MLR;
import qsar.bench.QBench;
import qsar.bench.qsar.view.QsarAlgOptView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 20/04/2007, 15:24:04
 */
public class MlrAlgOptViewArr extends QsarAlgOptView
{
  private ArrayList<QsarAlgOptView> arr;
  public MlrAlgOptViewArr(QsarAlgOptView v, QsarAlgOptView v2) {
    initThis();
    arr.add(v);
    arr.add(v2);
    assembleThis();
  }
  private void initThis() {
    arr = new ArrayList<QsarAlgOptView>();
  }
  public void loadTo(QBench model) {
    for (Iterator<QsarAlgOptView> iterator = arr.iterator(); iterator.hasNext();) {
      iterator.next().loadTo(model);
    }
  }
  public void loadFrom(QBench model) {
    for (Iterator<QsarAlgOptView> iterator = arr.iterator(); iterator.hasNext();) {
      iterator.next().loadFrom(model);
    }
  }
  private void assembleThis() {
    for (Iterator<QsarAlgOptView> iterator = arr.iterator(); iterator.hasNext();) {
      endRow(iterator.next());
    }
  }
}
