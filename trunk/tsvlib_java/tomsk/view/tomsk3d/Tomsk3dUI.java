package tomsk.view.tomsk3d;
import pattern.ucm.AdapterUCCToAL;
import pattern.ucm.UCController;
import tomsk.TomskLogger;
import tomsk.project.Tomsk;
import tomsk.project.TomskProject;
import tomsk.ucm.utils.UCShow3dView;
import tomsk.ucm.view.UCChangeNumRotationSecs;
import tomsk.ucm.view.UCSaveImage;
import tomsk.ucm.view.UCToggleRotation;
import tsvlib.project.ProjectLogger;

import javax.media.j3d.BranchGroup;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swingx.panelx.ViewWithApplyUI;
import java.awt.event.MouseEvent;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/05/2007, 14:59:01
 */
public class Tomsk3dUI extends ViewWithApplyUI
{
  private static ProjectLogger log = ProjectLogger.getLogger(Tomsk3dUI.class);

  private Tomsk3dOptView optView;
  private Tomsk3dView view;

  public Tomsk3dUI(Tomsk3dModel model) {
    init();
    loadFrom(model);
    assemble();
  }

  private void init()
  {
  }

  private void loadFrom(Tomsk3dModel model)
  {
    log.debug("model=", model);
    optView = new Tomsk3dOptView(model);
    view = new Tomsk3dView(model);
  }

  private void addUI() {
    JPopupMenu popup = makePopupMenu();
    MouseInputListener mouseL = new ThisPopupListener(popup);
    view.addMouseListener(mouseL);
    view.addMouseMotionListener(mouseL);
    log.trace("view.getMouseListeners=", view.getMouseListeners());
  }

  public void rebuild(BranchGroup bg)
  {
    view.rebuild(bg);
  }

  private void assemble()
  {
    addUI();

    setOptView(optView, new UCController() {
      public boolean run()
      {
        log.trace("run on APPLY");
        Tomsk project = TomskProject.getInstance();
        Tomsk3dModel model = project.getTomsk3dModel();
        log.debug("current model=", model);
        optView.loadTo(model);
        log.debug("new model=", model);
        project.saveProjectToDefaultLocation();
        view.loadFrom(model);
        return true;
      }
    });
    setView(view);
    assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
  }


  /**
   * From http://java.sun.com/docs/books/tutorial/uiswing/components/menu.html#popup
   * @return JPopupMenu
   */
  private JPopupMenu makePopupMenu() {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem mi;
    JMenu m;

    // ROTATION
    m = new JMenu("Rotation");
    popup.add(m);

    mi = new JMenuItem("Toggle On/Off");
    mi.addActionListener(new AdapterUCCToAL(new UCToggleRotation(view, optView)));
    m.add(mi);

    mi = new JMenuItem("Increase");
    mi.addActionListener(new AdapterUCCToAL(new UCChangeNumRotationSecs(view, optView, -1, 0.5)));
    m.add(mi);

    mi = new JMenuItem("Descrease");
    mi.addActionListener(new AdapterUCCToAL(new UCChangeNumRotationSecs(view, optView, +1, 1.5)));
    m.add(mi);

    popup.addSeparator();

    mi = new JMenuItem("Save image");
    mi.addActionListener(new AdapterUCCToAL(new UCSaveImage(view)));
    popup.add(mi);

    return popup;
  }

  private class ThisPopupListener extends MouseInputAdapter
  {
    private JPopupMenu thisPopup;
    private boolean allowPopup;

    public ThisPopupListener(JPopupMenu popup)
    {
      this.thisPopup = popup;
    }

    public void mousePressed(MouseEvent e) {
      log.trace("mousePressed(", e);
      allowPopup = true;
      maybeShowPopup(e);
    }
    public void mouseReleased(MouseEvent e) {
      log.trace("mouseReleased(", e);
      maybeShowPopup(e);

      Tomsk project = TomskProject.getInstance();
      Tomsk3dModel model = project.getTomsk3dModel();
      optView.loadFrom(model);
    }
    private void maybeShowPopup(MouseEvent e) {
      if (allowPopup  &&  e.isPopupTrigger()) {
        log.trace("isPopupTrigger(", e.getComponent());
        thisPopup.show(e.getComponent(), e.getX(), e.getY());
      }
      else {
      }
    }
    public void mouseDragged(MouseEvent e)
    {
      allowPopup = false;
      log.trace("mouseDragged()", e);
//      Tomsk3dView view = (Tomsk3dView) e.getComponent(); // WRONG!!!
//      Tomsk3dView view = (Tomsk3dView)e.getSource();
      Tomsk project = TomskProject.getInstance();
      Tomsk3dModel model = project.getTomsk3dModel();
      model.setEnableRotation(false);
      view.setEnableRotation(false);
    }
    public void mouseMoved(MouseEvent e)
    {
//      log.trace("mouseMoved()", e);
    }
  }


  public static void main( String[] args )
  {
    String appName = "Tomsk3dUI";
    TomskLogger log = TomskLogger.getInstance(appName);
    log.setInfo();
    ProjectLogger.getRootLogger().setAll();
    ProjectLogger.getLogger(Tomsk3dUI.class).setInfo();

    Tomsk project = TomskProject.makeInstance(appName, "070524");
    Tomsk3dModel model = project.getTomsk3dModel();

    JPopupMenu.setDefaultLightWeightPopupEnabled( false );
    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    new UCShow3dView(new Tomsk3dUI(model), appName).run();
//    new UCShowViewInFrame(swingTest, "Tomsk3dView").run();
  }
}
