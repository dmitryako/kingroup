package kingroup_v2.fsr;
import kingroup_v2.Kingroup;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/12/2005, Time: 13:37:02
 */
public class FsrAlgOptionsMCSView extends FsrAlgOptionsSIMPSView {
  public FsrAlgOptionsMCSView(Kingroup model) {
    init();
    loadFrom(model);
    assemble("Markov chain SIMPSON");
  }
}
