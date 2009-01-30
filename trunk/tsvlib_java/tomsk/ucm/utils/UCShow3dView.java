package tomsk.ucm.utils;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.utils.UCShowViewInFrame;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/05/2007, 14:36:19
 */
public class UCShow3dView extends UCShowViewInFrame
{
  {
    JPopupMenu.setDefaultLightWeightPopupEnabled( false );
    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

    ProjectLogger.getRootLogger().start("UCShow3dView");
    ProjectLogger.getRootLogger().setAll();

  }
  public UCShow3dView(JPanel view, String title)
  {
    super(view, title);

  }
}
