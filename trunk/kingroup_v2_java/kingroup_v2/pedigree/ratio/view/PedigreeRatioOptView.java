package kingroup_v2.pedigree.ratio.view;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDComplex;
import kingroup_v2.kinship.like.view.KinshipRatioSimTableView;
import kingroup_v2.kinship.like.view.KinshipRatioSimView;
import kingroup_v2.kinship.view.*;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import javax.swingx.panelx.GridBagView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 12:59:04
 */
public class PedigreeRatioOptView
  extends GridBagView
  implements KinshipRatioOptViewI
{
//  private PedigreeRatioHistView histView;
  private JTabbedPaneX hypoPane;
  private JTabbedPaneX showPane;

  private KinshipIBDView primView;
  private int primIdx = -1;
  private JRadioButton primFocus;

  private KinshipIBDArrView nullView;
  private int nullIdx = -1;
  private JRadioButton nullFocus;

  private KinshipRatioSimView simView;

  private KinshipDisplayWithLogView showView;
  private int showIdx = -1;
  private JRadioButton showFocus;

  protected KinshipSortOptView sortView;
  private int sortIdx = -1;
  private JRadioButton sortFocus;

  public PedigreeRatioOptView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  public void setPValueEnabled(boolean b) {simView.setPValueEnabled(b);}

  public void loadFrom(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    KinshipIBDComplex prim = kinship.getComplexPrimIBD();
    primView = new KinshipIBDView(prim);
    nullView = new KinshipIBDArrView(kinship);
//    hapView = new KinshipHaploidOptView(model);
    showView = new KinshipDisplayWithLogView(kinship);
    sortView = new KinshipSortOptView(kinship);
    simView = new KinshipRatioSimView(kinship);
  }
  public void loadTo(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    KinshipIBD prim = kinship.getPrimIBD();
//    KinshipIBDComplex nullM = kinship.getComplexNullIBD();
    primView.loadTo(prim);
    nullView.loadTo(kinship);
    showView.loadTo(kinship);
    sortView.loadTo(kinship);
    simView.loadTo(kinship);
    kinship.setTreatHaploid(Kinship.HAPLOID_IGNORE);
  }

  private void init() {
    primFocus = new JRadioButton();
    nullFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(primFocus);
    group.add(nullFocus);
    hypoPane = new JTabbedPaneX(JTabbedPane.TOP);

    showFocus = new JRadioButton();
    sortFocus = new JRadioButton();
    group = new ButtonGroup();
    group.add(showFocus);
    group.add(sortFocus);
    showPane = new JTabbedPaneX(JTabbedPane.TOP);
  }
  private void assemble() {
    primIdx = hypoPane.processView(primView, primIdx, primFocus.isSelected()
      , "Primary", "Primary hypothesis");
    nullIdx = hypoPane.processView(nullView, nullIdx, nullFocus.isSelected()
      , "Null", "Null hypothesis");
    hypoPane.setSelectedIndex(0);

    showIdx = showPane.processView(showView, showIdx, showFocus.isSelected()
      , "Display", "Display options");
    sortIdx = showPane.processView(sortView, sortIdx, sortFocus.isSelected()
      , "Sort", "Sorting options");
    showPane.setSelectedIndex(0);

    startRow(hypoPane);
//    startRow(showView);
    startRow(showPane);
    endRow(simView);
  }
  public void setRatioSimTableView(KinshipRatioSimTableView view) {
    simView.setTableView(view);
  }

  public void onIBDChange(UCController uc) {
    primView.onIBDChange(uc);
    nullView.onIBDChange(uc);
  }
  public Dimension getMinimumSize(){
    return new Dimension(300, 250);
  }
  public boolean getDisplayLog() {
    return showView.getDisplayLog();
  }
  public KinshipIBDView getPrimIBDView() {
    return primView;
  }
  public KinshipIBDArrView getNullIBDView() {
    return nullView;
  }
  public KinshipRatioSimView getRatioSimView() {
    return simView;
  }
}

