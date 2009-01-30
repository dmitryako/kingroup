package tomsk.ucm;
import tomsk.project.TomskProject;
import tsvlib.project.ProjectFrame;
import tsvlib.project.UCDShowHelpAbout;

import java.awt.event.ActionEvent;
public class DisplayHelpAboutTomskHnd extends UCDShowHelpAbout {
  public DisplayHelpAboutTomskHnd() {
    super(ProjectFrame.getInstance());
  }
  public void actionPerformed(ActionEvent event) {
    String help = "<HTML><H1>"
      + TomskProject.getInstance().getAppName()
      + "</H1><HR>"
      + "<H2>Towards Molecular Structure Kinetics</H2>"
      + "<H3>Version: "
      + TomskProject.getInstance().getAppVersion()
      + "</H3><br>"
      + "www.it.jcu.edu.au/tomsk<br>"
      + "Contact: dmitry.konovalov@jcu.edu.au<br>"
      + "</HTML>";
    setHelpMessage(help);
    super.actionPerformed(event);
  }
}
