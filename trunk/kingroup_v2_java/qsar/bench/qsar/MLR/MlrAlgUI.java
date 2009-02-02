package qsar.bench.qsar.MLR;
import qsar.bench.qsar.view.QsarAlgView;
import qsar.papers.chem2007_LogBB.LogBB_2007_paper;
import qsar.papers.chem2007b_PValue.submission_0708.PValue_2007_paper;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 13:32:29
 */
public class MlrAlgUI extends JPanel {
  private final static ProjectLogger log = ProjectLogger.getLogger(MlrAlgUI.class);
  private JTabbedPaneX ui;

  private QsarAlgView mlrView;
  private int mlrIdx = -1;
  private JRadioButton mlrFocus;

  private QsarAlgView looView;
  private int looIdx = -1;
  private JRadioButton looFocus;

  private QsarAlgView looKnnView;
  private int looKnnIdx = -1;
  private JRadioButton looKnnFocus;

  private QsarAlgView mccvView;
  private int mccvIdx = -1;
  private JRadioButton mccvFocus;

  private QsarAlgView saView;
  private int saIdx = -1;
  private JRadioButton saFocus;

  private QsarAlgView gaView;
  private int gaIdx = -1;
  private JRadioButton gaFocus;

  private QsarAlgView mccvKnnView;
  private int mccvKnnIdx = -1;
  private JRadioButton mccvKnnFocus;

  private QsarAlgView knnView;
  private int knnIdx = -1;
  private JRadioButton knnFocus;

  public MlrAlgUI() {
    init();
  }
  public void reset() {
    ui.removeAll();
    mlrView = null;        mlrIdx = -1;
    looView = null;        looIdx = -1;
    looKnnView = null;     looKnnIdx = -1;
    mccvView = null;       mccvIdx = -1;
    saView = null;       saIdx = -1;
    gaView = null;       gaIdx = -1;
    mccvKnnView = null;    mccvKnnIdx = -1;
    knnView = null;        knnIdx = -1;
  }
  private void init() {
    setLayout(new GridLayout(1, 1));  //Add the tabbed_ pane to this panel.
    mlrFocus = new JRadioButton();
    looFocus = new JRadioButton();
    mccvFocus = new JRadioButton();
    saFocus = new JRadioButton();
    gaFocus = new JRadioButton();
    looKnnFocus = new JRadioButton();
    mccvKnnFocus = new JRadioButton();
    knnFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(mlrFocus);
    group.add(looFocus);
    group.add(looKnnFocus);
    group.add(mccvFocus);
    group.add(saFocus);
    group.add(gaFocus);
    group.add(mccvKnnFocus);
    group.add(knnFocus);
    ui = new JTabbedPaneX(JTabbedPane.TOP);
    add(ui);
  }
  private void rebuild() {
    log.trace("rebuild()");
    mlrIdx = ui.processView(mlrView, mlrIdx, mlrFocus.isSelected()
      , "MLR", QsarAlgMlr.REFERENCE);
    knnIdx = ui.processView(knnView, knnIdx, knnFocus.isSelected()
      , "kNN", LogBB_2007_paper.REFERENCE);
    looIdx = ui.processView(looView, looIdx, looFocus.isSelected()
      , "LOO-MLR", LogBB_2007_paper.REFERENCE);
    looKnnIdx = ui.processView(looKnnView, looKnnIdx, looKnnFocus.isSelected()
      , "LOO-kNN", LogBB_2007_paper.REFERENCE);
    mccvIdx = ui.processView(mccvView, mccvIdx, mccvFocus.isSelected()
      , "MCCV-MLR", LogBB_2007_paper.REFERENCE);
    saIdx = ui.processView(saView, saIdx, saFocus.isSelected()
      , "SA-MCVS-MLR", PValue_2007_paper.REFERENCE);
    gaIdx = ui.processView(gaView, gaIdx, gaFocus.isSelected()
      , "GA-MCVS-MLR", PValue_2007_paper.REFERENCE);
    mccvKnnIdx = ui.processView(mccvKnnView, mccvKnnIdx, mccvKnnFocus.isSelected()
      , "MCCV-kNN", LogBB_2007_paper.REFERENCE);
    validate();
    repaint();
  }

  public void setMlrAlgView(QsarAlgView view)
  {
    this.mlrView = view;
    mlrFocus.setSelected(true);
    rebuild();
  }
  public void setLooAlgView(QsarAlgView view)
  {
    log.trace("setLooAlgView(", view);
    this.looView = view;
    looFocus.setSelected(true);
    rebuild();
  }
  public void setMccvAlgView(QsarAlgView view)
  {
    this.mccvView = view;
    mccvFocus.setSelected(true);
    rebuild();
  }
  public void setMcvsSaView(QsarAlgView view)
  {
    this.saView = view;
    saFocus.setSelected(true);
    rebuild();
  }
  public void setMcvsGaView(QsarAlgView view)
  {
    this.gaView = view;
    gaFocus.setSelected(true);
    rebuild();
  }
  public void setMccvKnnAlgView(QsarAlgView view)
  {
    log.trace("setMccvKnnAlgView", view);
    this.mccvKnnView = view;
    mccvKnnFocus.setSelected(true);
    rebuild();
  }
  public void setLooKnnAlgView(QsarAlgView view)
  {
    log.trace("setLooKnnAlgView", view);
    this.looKnnView = view;
    looKnnFocus.setSelected(true);
    rebuild();
  }
  public void setKnnAlgView(QsarAlgView view)
  {
    this.knnView = view;
    knnFocus.setSelected(true);
    rebuild();
  }

  public JTable getSelectedResultsTable()
  {
    int idx = ui.getSelectedIndex();
    if (idx == -1)
      return null;
    if (mlrIdx == idx) {
      return mlrView.getTableView();
    }
    if (looIdx == idx) {
      return looView.getTableView();
    }
    if (knnIdx == idx) {
      return knnView.getTableView();
    }
    if (mccvIdx == idx) {
      return mccvView.getTableView();
    }
    if (mccvKnnIdx == idx) {
      return mccvKnnView.getTableView();
    }
    if (saIdx == idx) {
      return saView.getTableView();
    }
    if (gaIdx == idx) {
      return gaView.getTableView();
    }
    return null;
  }
}
