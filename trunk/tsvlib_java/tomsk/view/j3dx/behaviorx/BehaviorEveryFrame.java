package tomsk.view.j3dx.behaviorx;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupOnElapsedFrames;
import javax.vecmath.Point3d;
import java.util.Enumeration;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 23, 2004, Time: 11:26:31 AM
 */
final public class BehaviorEveryFrame extends Behavior {
  private BehaviorEveryFrameCtrI ctr_;
  private static final WakeupCondition wakeupCond_ = new WakeupOnElapsedFrames(0);
  public BehaviorEveryFrame(BehaviorEveryFrameCtrI ctr) {
    ctr_ = ctr;
  }
  public void initialize() {
    setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
    wakeupOn(wakeupCond_);
  }
  public void processStimulus(Enumeration criteria) {
    ctr_.updateOnElapsedFrame();
    wakeupOn(wakeupCond_);
  }
}
