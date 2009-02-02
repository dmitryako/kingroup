package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBDComplex;
import kingroup_v2.kinship.view.KinshipIBDComplexView;
import pattern.ucm.UCController;

import tsvlib.project.ProjectLogger;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/10/2005, Time: 08:52:49
 */
public class FsrAlgOptionsDRView extends FsrAlgOptView {
  private static ProjectLogger log = ProjectLogger.getLogger(FsrAlgOptionsDRView.class.getName());
  private KinshipIBDComplexView primView;
  private KinshipIBDComplexView nullView;
  public FsrAlgOptionsDRView(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void init() {
  }
  public void loadTo(Kingroup to) {
    super.loadTo(to);
    Kinship kinship = to.getKinship();
    primView.loadTo(kinship.getComplexPrimIBD(), kinship);
    nullView.loadTo(kinship.getComplexNullIBD(), kinship);
  }
  protected void loadFrom(Kingroup from) {
    super.loadFrom(from);
    Kinship kinship = from.getKinship();
    KinshipIBDComplex prim = kinship.getComplexPrimIBD();
    KinshipIBDComplex nullM = kinship.getComplexNullIBD();
    primView = new KinshipIBDComplexView("Primary", prim, kinship);
    nullView = new KinshipIBDComplexView("Null", nullM, kinship);
  }
  protected void assemble() {
    startRow(specieView);
    endRow(displayView);
    endRow(primView);
    endRow(nullView);
  }
  public void onOptionChange(UCController uc) {
    super.onOptionChange(uc);
    primView.onIBDChange(uc);
    nullView.onIBDChange(uc);
  }
}
