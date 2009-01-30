package javax.swingx;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 27, 2004, Time: 12:08:41 PM
 */
public class SaveFileUI {
  private JFileChooser chooser = null;
  public File selectFile(Component parent, File file) {
    if (chooser == null) {
      chooser = new JFileChooser(file);
      if (chooser == null)
        return null;
    }
    if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION)
      return null;
    return chooser.getSelectedFile();
  }
  public File selectFile(Component parent, File file, FileFilter filter) {
    if (chooser == null) {
      chooser = new JFileChooser(file);
      if (chooser == null)
        return null;
      chooser.setFileFilter(filter);
    }
    if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION)
      return null;
    return chooser.getSelectedFile();
  }

  public void setFileFilter(FileFilter filter) {
    chooser.setFileFilter(filter);
  }

}
