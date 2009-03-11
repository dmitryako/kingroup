package qsar.bench.qsar.cv.mcvs;
import qsar.bench.QBench;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 6/06/2007, 11:26:33
 */
public class McvsOptView extends GridBagView
  implements QBenchViewI
{
  private final static ProjectLogger log = ProjectLogger.getLogger(McvsOptView.class);

  private static int N_VARS_FIELD_SIZE = 5;
  private IntField nVar;
  private JLabel nVarLbl;

  private IntField nFixed;
  private JLabel nFixedLbl;

  private static int N_BEST_FIELD_SIZE = 5;
  private IntField nBest;
  private JLabel nBestLbl;

  private static int N_VIEW_FIELD_SIZE = 5;
  private IntField nView;
  private JLabel nViewLbl;

  public McvsOptView(QBench project)
  {
    super("Variable Selection (MCVS)");
    init();
    loadFrom(project);
    assemble();
  }
  protected void init()
  {
    nVarLbl = new JLabel("variable(s)");
    nVar = new IntField(N_VARS_FIELD_SIZE, 1, 10);
    nVar.setToolTipText("select subsets with this number of variables");

    nFixedLbl = new JLabel("fixed");
    nFixed = new IntField(N_VARS_FIELD_SIZE, 0, 9);
    nFixed.setToolTipText("number of first descriptors which are always included");

    nBestLbl = new JLabel("best subsets");
    nBest = new IntField(N_BEST_FIELD_SIZE, 10, 100);
    nBest.setToolTipText("display this number of best descriptor subsets");

    nViewLbl = new JLabel("update interval");
    nView = new IntField(N_VIEW_FIELD_SIZE, 1, 1000);
    nView.setToolTipText("display intermediate results after this number of iterations");
  }
  public void loadTo(QBench project) {
    Mcvs model = project.getMcvs();
    model.setNumVars(nVar.getInput());
    model.setNumFixedXCols(nFixed.getInput());
    model.setNumUpdatesPerView(nView.getInput());
    model.setNumBest(nBest.getInput());
  }
  public void loadFrom(QBench project) {
    Mcvs model = project.getMcvs();
    nVar.setValue(model.getNumVars());
    nFixed.setValue(model.getNumFixedXCols());
    nView.setValue(model.getNumUpdatesPerView());
    nBest.setValue(model.getNumBest());
  }
  protected void assemble() {
    startRow(nVar);     endRow(nVarLbl);
    startRow(nFixed);     endRow(nFixedLbl);
    startRow(nBest);    endRow(nBestLbl);
    startRow(nView);    endRow(nViewLbl);
  }
}


