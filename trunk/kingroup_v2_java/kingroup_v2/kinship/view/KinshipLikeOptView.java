package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBDComplex;
import kingroup_v2.view.KingroupViewI;
import kingroup_v2.Kingroup;
import pattern.ucm.UCController;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2005, Time: 13:47:47
 */
public class KinshipLikeOptView
  extends KinshipOptionsView
  implements KingroupViewI
{
  private final boolean prim;
  protected KinshipIBDComplexView ibdView;
  public KinshipLikeOptView(boolean prim, Kingroup model) {
    this.prim = prim;
    init();
    loadFrom(model);
    assemble();
  }
  public void loadTo(Kingroup model) {
    Kinship kinship = model.getKinship();
    KinshipIBDComplex ibd = kinship.getComplexNullIBD();
    if (prim)
      ibd = kinship.getComplexPrimIBD();
    ibdView.loadTo(ibd, kinship);
    hapView.loadTo(kinship);
    showView.loadTo(kinship);
  }
  public KinshipIBDComplex getKinshipIBDModel(Kinship model) {
    if (prim)
      return model.getComplexPrimIBD();
    else
      return model.getComplexNullIBD();
  }
  public void loadFrom(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    KinshipIBDComplex ibd = kinship.getComplexNullIBD();
    if (prim)
      ibd = kinship.getComplexPrimIBD();
    if (prim)
      ibdView = new KinshipIBDComplexView("Primary", ibd, kinship);
    else
      ibdView = new KinshipIBDComplexView("Null", ibd, kinship);
    hapView = new KinshipHaploidOptView(kinship);
    showView = new KinshipDisplayWithLogView(kinship);
  }
  protected void assemble() {
    startRow(hapView);
    startRow(ibdView);
    endRow(showView);
  }
  public void onIBDChange(UCController uc) {
    ibdView.onIBDChange(uc);
  }
  public KinshipIBDComplexView getIBDView() {
    return ibdView;
  }
  public boolean getDisplayLog() {
    return showView.getDisplayLog();
  }
}
