package tomsk.view.tomsk3d.utils;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 23/05/2007, 10:36:52
 */
public class Tomsk3dBehaviourFactory
{
  public static BranchGroup addMouseRotate(BranchGroup scene, Bounds bounds) {
    TransformGroup tg = new TransformGroup();
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

    BranchGroup res = new BranchGroup();
    res.addChild(tg);
    tg.addChild(scene);

    MouseRotate mouseAction = new MouseRotate(tg);
    mouseAction.setSchedulingBounds(bounds);
    res.addChild(mouseAction);
    return res;
  }

  public static BranchGroup addMouseTranslate(BranchGroup scene, Bounds bounds) {
    TransformGroup tg = new TransformGroup();
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

    BranchGroup res = new BranchGroup();
    res.addChild(tg);
    tg.addChild(scene);

    MouseTranslate mouseAction = new MouseTranslate(tg);
    mouseAction.setSchedulingBounds(bounds);
    res.addChild(mouseAction);
    return res;
  }

  public static BranchGroup addMouseZoom(BranchGroup scene, Bounds bounds) {
    TransformGroup tg = new TransformGroup();
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

    BranchGroup res = new BranchGroup();
    res.addChild(tg);
    tg.addChild(scene);

    MouseZoom mouseAction = new MouseZoom(tg);
    mouseAction.setSchedulingBounds(bounds);
    res.addChild(mouseAction);
    return res;
  }
}
