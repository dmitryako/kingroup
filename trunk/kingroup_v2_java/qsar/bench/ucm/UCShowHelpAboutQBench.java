package qsar.bench.ucm;
import qsar.bench.QBenchProject;
import qsar.papers.chem2007_LogBB.LogBB_2007_paper;
import qsar.papers.chem2007b_PValue.submission_0708.PValue_2007_paper;
import tsvlib.project.UCDShowHelpAbout;

import javax.langx.SysProp;
import javax.langx.MemoryInfo;
import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2007, Time: 11:20:20
 */
public class UCShowHelpAboutQBench extends UCDShowHelpAbout {
  public UCShowHelpAboutQBench(JFrame frame) {
    super(frame);
//    String help = "<HTML><H2><A href=\"www.dmitrykonovalov.org\">www.dmitrykonovalov.org</A>   "
    String help = "<HTML><H2>   "
      + QBenchProject.getInstance().getAppName()
      + " " + QBenchProject.getInstance().getAppVersion()
      + "</H2><HR>"
      + PValue_2007_paper.REFERENCE_HTML
      + "<br>"
      + "<br>"
      + LogBB_2007_paper.REFERENCE_HTML
      + "<br>"
      + "<br>"
      + "Copyright &copy 2007-2008 dmitry.konovalov@jcu.edu.au"
      + "<br>"
      + "<br>"
      + "System Info:"
      + "<br>"
      + "<pre>" + new MemoryInfo() + "</pre>"
      + "<pre>" + SysProp.getSystemProperties() + "</pre>"
      + "</HTML>"
      ;
    setHelpMessage(help);
  }
}

