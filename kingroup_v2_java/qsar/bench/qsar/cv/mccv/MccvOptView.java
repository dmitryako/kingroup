package qsar.bench.qsar.cv.mccv;
import qsar.bench.QBench;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 20/04/2007, 17:14:55
 */
public class MccvOptView extends GridBagView
  implements QBenchViewI
{
  private final static ProjectLogger log = ProjectLogger.getLogger(MccvOptView.class);

  private static int N_ITER_FIELD_SIZE = 5;
  private IntTextField nIter;
  private JLabel nIterLbl;

  private static int N_VALID_FIELD_SIZE = 5;
  private IntTextField size;
  private JLabel sizeLbl;

  public MccvOptView(QBench project)
  {
    super("Monte Carlo (MCCV)");
    init();
    loadFrom(project);
    assemble();
  }
  protected void init()
  {
    nIterLbl = new JLabel("iterations (N)");
    nIter = new IntTextField(N_ITER_FIELD_SIZE, 1, 100000);
    nIter.setToolTipText("number of iterations");

    sizeLbl = new JLabel("validation size (n_v)");
    size = new IntTextField(N_VALID_FIELD_SIZE, 1, 10000);
    size.setToolTipText("the size of validation subset");
  }
  public void loadTo(QBench project) {
    Mccv mccv = project.getMccv();
    mccv.setValidSize(size.getInput());
    mccv.setNumIter(nIter.getInput());
  }
  public void loadFrom(QBench project) {
    Mccv mccv = project.getMccv();
    size.setValue(mccv.getValidSize());
    nIter.setValue(mccv.getNumIter());
  }
  protected void assemble() {
    startRow(nIter);
    endRow(nIterLbl);
    startRow(size);
    endRow(sizeLbl);
  }
}

