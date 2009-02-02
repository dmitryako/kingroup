package kingroup_v2.io;

import javax.swing.*;
import javax.iox.TextFileView;
import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/01/2006, Time: 08:38:26
 */
public class FileIO {
  public static JPanel combine(JPanel formatView, TextFileView fileView) {
    JPanel res = new JPanel(new BorderLayout());
    res.add(formatView, BorderLayout.NORTH);
    res.add(fileView, BorderLayout.CENTER);
    return res;
  }
}
