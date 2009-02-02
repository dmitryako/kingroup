package qsar.bench.qsar.MLR;
import pattern.ucm.AdapterUCCToALThread;
import pattern.ucm.UCShowHelpMssg;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.qsar.view.QsarAlgOptView;
import tsvlib.project.ProjectFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/03/2007, 15:09:38
 */
public class MlrKnnOptView  extends QsarAlgOptView
{
  private static int FIELD_SIZE = 3;
  protected JCheckBox norm;
  private IntTextField knn;
  private JLabel knnLbl;

  public MlrKnnOptView()
  {
    init();
  }
  public MlrKnnOptView(QBench project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  protected void init()
  {
    super.init();
    norm = new JCheckBox("standardize");
    knnLbl = new JLabel("neighbors");
    knn = new IntTextField(FIELD_SIZE, 1, 1000);
    knn.setToolTipText("number of nearest neighbors");
  }
  public void loadTo(QBench model) {
    super.loadTo(model);
    model.setMeanZeroVarOne(norm.isSelected());
    model.setKnn(knn.getInput());
  }
  public void loadFrom(QBench model) {
    super.loadFrom(model);
    norm.setSelected(model.getMeanZeroVarOne());
    knn.setValue(model.getKnn());
  }
  protected JPanel makeKnnPanel() {
    JPanel p = new JPanel();
    p.add(knn);
    p.add(knnLbl);

    JButton bttn = ProjectFrame.makeHelpButton();
//    bttn.setToolTipText(title);
    bttn.addActionListener(new AdapterUCCToALThread(new UCShowHelpMssg(
      "descriptor columns are standardized to mean=0 and var=1 [for TRAINing only!]"
      , QBenchFrame.getInstance())));
    JPanel p2 = new JPanel();
    p2.add(norm);
    p2.add(bttn);

    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, "kNN");
    panel.setBorder(titled);
    panel.endRow(p);
    panel.endRow(p2);
    return panel;
  }
  protected void assemble() {
    endRow(tableOptView);
    endRow(diplayView);
    endRow(makeKnnPanel());
  }
}

