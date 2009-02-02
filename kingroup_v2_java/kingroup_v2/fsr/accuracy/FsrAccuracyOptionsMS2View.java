package kingroup_v2.fsr.accuracy;
import kingroup_v2.Kingroup;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/11/2005, Time: 09:55:10
 */
public class FsrAccuracyOptionsMS2View extends FsrAccuracyOptionsView
{
  public FsrAccuracyOptionsMS2View(Kingroup project)
  {
    super(project);
    init();
    loadFrom(project);
    assemble();
  }
  private void init()
  {
  }
}
