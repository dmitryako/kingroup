package tomsk.view.j3dx;
import tomsk.domain.particle.ParticleGroup;
import tomsk.domain.particle.ParticleGroupFactory;
import tomsk.domain.particle.visitor.AvrCoordVisitor;
import tomsk.io.pdb.PDBModel;
import tomsk.ucm.utils.UCShow3dView;
import tomsk.view.theme.View3dFactory;
import tomsk.view.theme.View3dFactoryParticleVisitor;
import tomsk.view.tomsk3d.Tomsk3dModel;
import tomsk.view.tomsk3d.Tomsk3dUI;
import tsvlib.project.ProjectLogger;

import javax.media.j3d.BranchGroup;
import javax.swing.*;
import javax.vecmath.Vector3d;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/05/2007, 13:18:32
 */
public class PDB3dView extends Tomsk3dUI
{
  private static ProjectLogger log = ProjectLogger.getLogger(PDB3dView.class);
//  private Tomsk3dModel tomsk3d;
  public PDB3dView(PDBModel model, Tomsk3dModel t3d)
  {
    super(t3d);
    loadFrom(model);
    assemble();
  }

  private void loadFrom(PDBModel model)
  {
    ParticleGroup group = ParticleGroupFactory.makeFromPDB(model);
    Vector3d c = new AvrCoordVisitor().calcCoord(group); // geometrical center
    c.scale(-1.);
    group.addCoordSLOW(c);

    View3dFactoryParticleVisitor vis = new View3dFactoryParticleVisitor(new View3dFactory());
    BranchGroup bg = vis.makeView3d(group);

    rebuild(bg);
  }

//  private void init() {
//    setLayout(new BorderLayout());
//  }

  private void assemble() {
    // SimpleUniverse extends VirtualUniverse
//    Tomsk3dView u = new Tomsk3dView(tum);
  }


  public static void main( String[] args )
  {
    JPopupMenu.setDefaultLightWeightPopupEnabled( false );
    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    new UCShow3dView(new Tomsk3dUI(null), "Tomsk3dUI").run();
//    new UCShowViewInFrame(swingTest, "Tomsk3dView").run();
  }

}
