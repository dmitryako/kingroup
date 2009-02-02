package multi_t.ucm;
import multi_t.MultiTProject;
import tsvlib.project.UCDShowHelpAbout;

import javax.langx.SysProp;
import javax.swing.*;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/11/2006, Time: 12:58:12
 */
public class UCShowHelpAboutMultiT extends UCDShowHelpAbout {
  public UCShowHelpAboutMultiT(JFrame frame) {
    super(frame);
    String help = "<HTML><H2><A href=\"www.kingroup.org\">www.kingroup.org</A>   "
      + MultiTProject.getInstance().getAppName()
      + " " + MultiTProject.getInstance().getAppVersion()
      + "</H2><HR>"
      + "Konovalov DA, Manning C, Henshaw MT (2004)<br>"
      + "KINGROUP: a program for pedigree relationship reconstruction<br>"
      + "and kin group assignments using genetic markers."
      + "<br><em>Molecular Ecology Notes</em> <strong>4</strong>, 779-782.<br><hr>"
      + "Copyright &copy 2002-2006 dmitry.konovalov@jcu.edu.au"
      + "<br>"
      + "<br>"
      + "System Info:"
      + "<br>"
      + "<pre>" + SysProp.getSystemProperties() + "</pre>"
      + "</HTML>"
      ;
    setHelpMessage(help);
  }
}

