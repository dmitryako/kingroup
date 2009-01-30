package tomsk.ucm.world;
import pattern.ucm.UCController;
import tomsk.TomskMainUI;
import tomsk.project.Tomsk;
import tomsk.project.TomskProject;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import javax.swingx.OpenFileUI;
import java.io.File;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 22, 2004, Time: 9:25:39 AM
 */
public class UCOpenPDBFile extends OpenFileUI
  implements UCController {

  private static ProjectLogger log = ProjectLogger.getLogger(UCOpenPDBFile.class);

  public boolean run()
  {
    Tomsk project = TomskProject.getInstance();
    String name = project.getPdbFileName();
    File file = project.makeFile(name);

    if (!file.exists()) { // TRY other file names
//      name = model.getTestTableName();
//      file = model.makeFile(name);
    }

    file = selectFile(TomskMainUI.getInstance(), file);
    if (file == null)
      return false;

    project.setPdbFileName(FileX.getFileName(file));
    project.saveProjectToDefaultLocation();

    return new UCImportPDBFile(file).run();
  }
}
