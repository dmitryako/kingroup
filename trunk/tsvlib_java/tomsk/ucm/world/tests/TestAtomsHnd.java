package tomsk.ucm.world.tests;
import tomsk.TomskMainUI;
import tomsk.view.j3dx.geometryx.Java3DAtomsCtr;
import tomsk.view.j3dx.geometryx.Java3DAtomsModel;
import tomsk.optimization.StorageAdaptorJava3D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 22, 2004, Time: 12:23:07 PM
 */
public class TestAtomsHnd implements ActionListener {
  public void actionPerformed(ActionEvent event) {
    final int SIZE = 100;
    StorageAdaptorJava3D pool = new StorageAdaptorJava3D(SIZE);
    //MovingParticleArray pa = ParticlesFactory.makeParticleSystem(pool);

    //Java3DParticlesGeometry model = new Java3DParticlesGeometry(pool.getPositionArray());

    // MVC
    Java3DAtomsModel model = new Java3DAtomsModel(pool.getPositionArray());
    Java3DAtomsCtr ctr = new Java3DAtomsCtr(model);
    TomskMainUI.getInstance().addWorldView(ctr.makeView());

    //Java3DParticlesView view = new Java3DParticlesView(model);
    //TomskMainUI.getInstance().addWorldView(view.makeView());
  }
}
