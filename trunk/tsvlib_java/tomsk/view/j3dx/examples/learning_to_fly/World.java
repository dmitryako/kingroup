/*
--------------------------------------------------------------------------------
All the CubeColorView's a Stage
Listing 1. Java 3D objects are stored in a node graph inside a Locale of a VirtualUniverse.
*/
package tomsk.view.j3dx.examples.learning_to_fly;
import javax.media.j3d.*;
public class World {
  VirtualUniverse universe;
  Locale locale;
  BranchGroup viewGroup
  ,
  objGroup;
  public World() {
    universe = new VirtualUniverse();
    locale = new Locale(universe);
    viewGroup = new BranchGroup();
    objGroup = new BranchGroup();
    viewGroup.setCapability(
      Group.ALLOW_CHILDREN_EXTEND);
    objGroup.setCapability(
      Group.ALLOW_CHILDREN_EXTEND);
  }
  public void addViewer(Viewer viewer) {
    viewGroup.addChild(viewer.getRoot());
  }
  public void addBehavior(Behavior behavior) {
    viewGroup.addChild(behavior);
  }
  public void addChild(Node obj) {
    objGroup.addChild(obj);
  }
  public void activate() {
    viewGroup.compile();
    objGroup.compile();
    locale.addBranchGraph(viewGroup);
    locale.addBranchGraph(objGroup);
  }
}



