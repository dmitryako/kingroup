package tomsk.ucm.world.tests;
import pattern.ucm.UCController;
import tomsk.TomskMainUI;
import tomsk.domain.Point3dArr;
import tomsk.domain.ParticleArrFactory;
import tomsk.domain.ParticleArrGeometry;
import tomsk.domain.ParticleArrView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/04/2007, 12:10:54
 */
public class UCRunBrownianByRef implements UCController {
  public boolean run()
  {
    final int SIZE = 10000;
    Point3dArr model = ParticleArrFactory.makeRandom(SIZE);
    ParticleArrGeometry geom = new ParticleArrGeometry(model);
    TomskMainUI.getInstance().addWorldView(new ParticleArrView(geom).makeView());
    return true;
  }
}

