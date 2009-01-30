/*
--------------------------------------------------------------------------------
Fly by Night
Listing 3. Moving around your view of the world requires an understanding of affine geometry and transformations. The Flyer class demonstrates how to implement translational and rotational transformations to move forward and backward and to pitch and roll.

      */
package tomsk.view.j3dx.examples.learning_to_fly;
import javax.media.j3d.*;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
public class Flyer extends Viewer {
  private int frameRate;
  private double velocity
  ,
  acceleration;
  private double maxPitchVelocity
  ,
  maxRollVelocity;
  private double velocityPerUpdate
  ,
  pitchPerUpdate
  ,
  rollPerUpdate;
  private MovementBehavior movementb;
  private KeyEventBehavior keyb;
  private MouseEventBehavior mouseb;
  private Vector3d position
  ,
  tmpVector;
  private Matrix3d matrix;
  private Transform3D roll
  ,
  pitch;
  private AxisAngle4d angle;
  private boolean isPaused;
  public class MovementBehavior extends Behavior {
    WakeupCriterion criterion;
    public void initialize() {
      criterion = new WakeupOnElapsedFrames(0);
      setSchedulingBounds(new BoundingSphere(
        new Point3d(0, 0, 0), 1e10));
      wakeupOn(criterion);
    }
    public void processStimulus(
      Enumeration criteria) {
      update();
      wakeupOn(criterion);
    }
  }
  public class KeyEventBehavior extends Behavior {
    WakeupOnAWTEvent criterion;
    public void initialize() {
      criterion = new WakeupOnAWTEvent(
        Event.KEY_PRESS);
      setSchedulingBounds(new BoundingSphere(
        new Point3d(0, 0, 0), 1e10));
      wakeupOn(criterion);
    }
    public void processStimulus(
      Enumeration criteria) {
      AWTEvent[] events = criterion.getAWTEvent();
      switch (((KeyEvent) events[0]).getKeyChar()) {
        case 'a':
          accelerate(1);
          break;
        case 'A':
          accelerate(10);
          break;
        case 'd':
          accelerate(-1);
          break;
        case 'D':
          accelerate(-10);
          break;
        case 'p':
          pause();
          break;
      }
      wakeupOn(criterion);
    }
  }
  public class MouseEventBehavior extends Behavior {
    WakeupOnAWTEvent criterion;
    public void initialize() {
      criterion = new WakeupOnAWTEvent(
        Event.MOUSE_MOVE);
      setSchedulingBounds(new BoundingSphere(
        new Point3d(0, 0, 0), 1e10));
      wakeupOn(criterion);
    }
    public void processStimulus(
      Enumeration criteria) {
      AWTEvent[] events = criterion.getAWTEvent();
      MouseEvent me = (MouseEvent) events[0];
      roll(me.getX());
      pitch(me.getY());
      wakeupOn(criterion);
    }
  }
  public Flyer() {
    setFrameRate(1);
    setVelocity(0);
    setAcceleration(0);
    setMaxPitchVelocity(0.25);
    setMaxRollVelocity(0.25);
    rollPerUpdate = pitchPerUpdate = 0;
    isPaused = false;
    matrix = new Matrix3d();
    position = new Vector3d();
    tmpVector = new Vector3d();
    roll = new Transform3D();
    pitch = new Transform3D();
    angle = new AxisAngle4d();
    movementb = new MovementBehavior();
    keyb = new KeyEventBehavior();
    mouseb = new MouseEventBehavior();
  }
  public void pause() {
    isPaused = !isPaused;
    if (isPaused)
      movementb.setEnable(false);
    else
      movementb.setEnable(true);
  }
  public void addBehavior(World world) {
    world.addBehavior(movementb);
    world.addBehavior(keyb);
    world.addBehavior(mouseb);
  }
  public void setViewingDirection(
    Vector3d location, Vector3d direction,
    Vector3d up) {
    Vector3d xViewBasis, yViewBasis, zViewBasis;
    xViewBasis = new Vector3d();
    yViewBasis = new Vector3d();
    zViewBasis = new Vector3d();
    zViewBasis.normalize(direction);
    zViewBasis.scale(-1);
    xViewBasis.cross(up, zViewBasis);
    xViewBasis.normalize();
    yViewBasis.cross(zViewBasis, xViewBasis);
    yViewBasis.normalize();
    matrix.setColumn(0, xViewBasis);
    matrix.setColumn(1, yViewBasis);
    matrix.setColumn(2, zViewBasis);
    eye.set(matrix);
    eye.setTranslation(location);
    root.setTransform(eye);
  }
  public void update() {
    // Move forward.
    eye.get(position);
    eye.get(matrix);
    matrix.getColumn(2, tmpVector);
    tmpVector.scale(-velocityPerUpdate);
    position.add(tmpVector);
    eye.setTranslation(position);

    // Apply roll.
    eye.mul(roll);

    // Apply pitch.
    eye.mul(pitch);
    root.setTransform(eye);
  }
  public void setVelocity(double v) {
    velocity = v;
    velocityPerUpdate = velocity / frameRate;
  }
  public double getVelocity() {
    return velocity;
  }
  public void setAcceleration(double a) {
    acceleration = a;
  }
  public double getAcceleration() {
    return acceleration;
  }
  public void setMaxPitchVelocity(double v) {
    maxPitchVelocity = v;
  }
  public double getMaxPitchVelocity() {
    return maxPitchVelocity;
  }
  public void setMaxRollVelocity(double v) {
    maxRollVelocity = v;
  }
  public double getMaxRollVelocity() {
    return maxRollVelocity;
  }
  public void setFrameRate(int rate) {
    frameRate = rate;
  }
  public int getFrameRate() {
    return frameRate;
  }
  public void accelerate(int factor) {
    velocity += (factor * acceleration);
    velocityPerUpdate = velocity / frameRate;
  }
  public void roll(int x) {
    double rollVelocity;
    rollVelocity = ((double) 2 * x) / (
      canvas.getWidth() - 1) - 1;
    rollVelocity *= -maxRollVelocity;
    rollPerUpdate = rollVelocity / frameRate;
    angle.set(0, 0, 1, rollPerUpdate);
    roll.setRotation(angle);
  }
  public void pitch(int y) {
    double pitchVelocity;
    pitchVelocity = ((double) (2 * y)) / (
      canvas.getHeight() - 1) - 1;
    pitchVelocity *= maxPitchVelocity;
    pitchPerUpdate = pitchVelocity / frameRate;
    angle.set(1, 0, 0, pitchPerUpdate);
    pitch.setRotation(angle);
  }
  public double getRollPerUpdate() {
    return rollPerUpdate;
  }
  public double getPitchPerUpdate() {
    return rollPerUpdate;
  }
  public double getVelocityPerUpdae() {
    return velocityPerUpdate;
  }
}



