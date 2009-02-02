package kingroup_v2.fsr;
import kingroup_v2.Kingroup;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/10/2005, Time: 11:19:34
 */
public class FsrAlgOptionsMS2View extends FsrAlgOptView {
  public FsrAlgOptionsMS2View(Kingroup model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void init() {
  }
}
