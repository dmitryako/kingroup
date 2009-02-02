package kingroup_v2.ucm;
import kingroup_v2.KinGroupV2Project;
import tsvlib.project.UCDShowHelpAbout;

import javax.swing.*;
import javax.langx.SysProp;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/09/2005, Time: 16:04:26
 */
public class UCShowHelpAboutKinGroupV2 extends UCDShowHelpAbout {
    public UCShowHelpAboutKinGroupV2(JFrame frame) {
        super(frame);
        String help = "<HTML><H2><A href=\"www.kingroup.org\">www.kingroup.org</A>   "
                + KinGroupV2Project.getInstance().getAppName()
                + " " + KinGroupV2Project.getInstance().getAppVersion()
                + "</H2><HR>"
                + "Konovalov DA, Manning C, Henshaw MT (2004)<br>"
                + "KINGROUP: a program for pedigree relationship reconstruction<br>"
                + "and kin group assignments using genetic markers."
                + "<br><em>Molecular Ecology Notes</em> <strong>4</strong>, 779-782."
                + "<br>"
                + "<br>"
                + "For more info search scholar.google.com with 'konovalov kingroup'."
                + "<br><hr>"
                + "Copyright &copy 2002-2009 dmitry.konovalov@jcu.edu.au"
                + "<br>"
                + "<br>"
                + "Your Computer Info:"
                + "<br>"
                + "<pre>" + SysProp.getSystemProperties() + "</pre>"
                + "</HTML>"
                ;
        setHelpMessage(help);
    }
}