package kingroup_v2.kinship.like.view;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBDComplex;
import kingroup_v2.kinship.view.*;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swingx.panelx.GridBagView;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 07:59:36
 */
public class KinshipRatioOptView
  extends KinshipLikeOptView
  implements KinshipRatioOptViewI
{
  private KinshipIBDComplexView nullView;
  private KinshipRatioSimView simView;
  public KinshipRatioOptView(Kingroup model) {
    super(true, model);
//    loadFrom(model, pop); // called super
//    assemble(); // called from super
  }
  public void loadTo(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    super.loadTo(kingroup);
    nullView.loadTo(kinship.getComplexNullIBD(), kinship);
    simView.loadTo(kinship);
  }
  public void loadFrom(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    KinshipIBDComplex primIBD = kinship.getComplexPrimIBD();
    KinshipIBDComplex nullIBD = kinship.getComplexNullIBD();
    primIBD.setComplex(false);
    ibdView = new KinshipIBDComplexView("Primary", primIBD, kinship);
    ibdView.setComplexEnabled(false);
    nullView = new KinshipIBDComplexView("Null", nullIBD, kinship);
    hapView = new KinshipHaploidOptView(kinship);
    showView = new KinshipDisplayWithLogView(kinship);
    simView = new KinshipRatioSimView(kinship);

//    nullView.onComplexChange(new UCController() {
//      public void run() {
//        Dimension dim = getPreferredSize();
//        setMinimumSize(dim);
//      }
//    });
  }
  protected void assemble() {
    removeAll();
    JPanel panel = new JPanel();
    panel.add(ibdView);
    panel.add(nullView);
    endRow(panel);

    GridBagView grid = new GridBagView();
    grid.startRow(showView);
    grid.startRow(hapView);
    grid.startRow(simView);
    grid.endRow(new JPanel());
    endRow(grid);
  }
  public KinshipRatioSimTableView getRatioSimTableView() {
    return simView.getTableView();
  }
  public KinshipRatioSimView getRatioSimView() {
    return simView;
  }
  public KinshipRatioSimOptView getRatioSimOptionsView() {
    return simView.getOptionsView();
  }
  public KinshipIBDComplexView getPrimIBDView() {
    return getIBDView();
  }
  public KinshipIBDComplexView getNullIBDView() {
    return nullView;
  }
  public void onIBDChange(UCController uc) {
    ibdView.onIBDChange(uc);
    nullView.onIBDChange(uc);
  }
  public void setRatioSimTableView(KinshipRatioSimTableView view) {
    simView.setTableView(view);
  }
  public void onDisplaySigFlagChange(UCController uc) {
    simView.onSigFlagChange(uc);
  }
  public void onDisplayPValueChange(UCController uc) {
    simView.onPValueChange(uc);
  }
}
