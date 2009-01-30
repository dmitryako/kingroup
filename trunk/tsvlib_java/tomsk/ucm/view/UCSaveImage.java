package tomsk.ucm.view;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import pattern.ucm.UCController;
import tomsk.project.Tomsk;
import tomsk.project.TomskProject;
import tomsk.view.tomsk3d.Tomsk3dView;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import javax.swing.*;
import javax.swingx.SaveFileUI;
import javax.swingx.filechooserx.JPEGFileFilter;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/05/2007, 15:08:22
 */
public class UCSaveImage extends SaveFileUI
  implements UCController
{
  private static ProjectLogger log = ProjectLogger.getLogger(UCSaveImage.class);

  private Tomsk3dView view;
  public UCSaveImage(Tomsk3dView view)
  {
    this.view = view;
  }

  public boolean run() {
    Tomsk project = TomskProject.getInstance();
    String name = project.getImageFileName();

    if (name == null || name.length() == 0)
      name = project.getPdbFileName();

    File file = project.makeFile(name);

    file = selectFile(view, file, new JPEGFileFilter());
//    file = selectFile(view, file);
    if (file == null)
      return false;
    if (file.exists()) {
      if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(view
        , "Replace existing \"" + file.getName() + "\" ?"))
        return false;
    }
    project.setImageFileName(FileX.getFileName(file));
    project.saveProjectToDefaultLocation();

    saveImageToFile(file);

    file = null;  System.gc();
    return true;
  }

  protected void saveImageToFile(File file)
  {
    log.trace( "saveImageToFile()" );
    try
    {
//      FileOutputStream fileOut = new FileOutputStream( "image.jpg" );
      FileOutputStream fileOut = new FileOutputStream(file);
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( fileOut );
      encoder.encode( view.getImage() );
      fileOut.flush( );
      fileOut.close( );
      fileOut = null;
    }
    catch( Exception e )
    {
      log.error("Failed to save image: ", e);
    }
    log.trace("Saved image.");
  }
}
