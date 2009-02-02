package kingroup_v2.pop.sample.builder;
import kingroup_v2.kinship.view.KinshipIBDView;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.view.PopBuilderView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntTextField;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/05/2006, Time: 16:08:36
 */
public class PopBuilderKinshipIBDView   extends PopBuilderView
{
  protected IntTextField nPairs;
  protected JLabel nPairsLbl;
  protected final static int FIELD_SIZE = 6;

  private KinshipIBDView ibdView;

  public PopBuilderKinshipIBDView() {

  }
  public PopBuilderKinshipIBDView(PopBuilderModel bean) {
    init();
    loadFrom(bean);
    assemble();
  }

  protected void init() {
    setLayout(new BorderLayout());
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "KINSHIP pairs");
    setBorder(titled);

    nPairs = new IntTextField(FIELD_SIZE, 1, 10000);
    nPairs.setMinimumSize(nPairs.getPreferredSize());
    nPairsLbl = new JLabel("pairs");
    nPairsLbl.setToolTipText("number of KINSHIP pairs");
  }
  private void assemble() {
    GridBagView panel = new GridBagView();
    panel.startRow(nPairs);
    panel.endRow(nPairsLbl);
    panel.endRow(ibdView);
    add(panel, BorderLayout.SOUTH);
  }
  public void loadFrom(PopBuilderModel model) {
    nPairs.setValue(model.getNumKinshipPairs());
    ibdView = new KinshipIBDView(model.getKinshipIBD());
  }
  public void loadTo(PopBuilderModel model) {
    model.setBuilderType(PopBuilderModel.KINSHIP_IBD_BUILDER);
    model.setNumKinshipPairs(nPairs.getInput());
    ibdView.loadTo(model.getKinshipIBD());
  }
}