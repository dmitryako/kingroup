package kingroup.model;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tsvlib.project.Project;

import java.io.File;
/**
 * Testing <code>tsvlib.project.Project</code>.
 */
public class ProjectTest extends TestCase {
   //private String userDir_ = "C:\\Documents and Settings\\dmitry";
   private String userDir_ = System.getProperty("user.home");
                                         
   private int count_ = 0;
   private String ot_ = "orgType";
   private String an_ = "appName";
   private String av_ = "appVersion";
   private String dn_ = "dirName";
   private String fn_ = "fileName";

   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(ProjectTest.class);
   }
   protected void setUp() {    }


   final public void testCopyTo() {
      Project data = new Project();
      setNewAttributes(data);

      Project data2 = new Project();
      setNewAttributes(data2); // data != data2

      data2.copyTo(data); // data == data2
      assertAttributes(data);
      assertAttributes(data2);
   }

   public void testGetSet() {
      Project data = new Project();

      setNewAttributes(data);
      assertAttributes(data);

      setNewAttributes(data);
      assertAttributes(data);
   }

   public void testGetDefaultUserDirName() {
      Project data = new Project();
      assertEquals(userDir_, data.getDefaultUserDirName());
   }

   public void testBuildDefaultDirName() {
      Project data = new Project();
      data.setOrgType(ot_);
      data.setAppName(an_);
      data.setAppVersion(av_);

      String dirName = userDir_
         + File.separator + data.getOrgType()
         + File.separator + data.getAppName()
         + File.separator + data.getAppVersion();

      assertEquals(dirName, data.buildDefaultDirName());

      // no org
      dirName = userDir_
         + File.separator + "org"
         + File.separator + data.getAppName()
         + File.separator + data.getAppVersion();

      data.setOrgType("");
      assertEquals(dirName, data.buildDefaultDirName());

      data.setOrgType(null);
      assertEquals(dirName, data.buildDefaultDirName());

      // no application name/version
      dirName = userDir_
         + File.separator + "org"
         + File.separator + "project";

      data.setAppName("");
      data.setAppVersion("");
      assertEquals(dirName, data.buildDefaultDirName());

      data.setAppName(null);
      data.setAppVersion(null);
      assertEquals(dirName, data.buildDefaultDirName());
   }

   public void setNewAttributes(Project data) {
      generateNewAttributes();

      data.setOrgType(ot_);
      data.setAppName(an_);
      data.setAppVersion(av_);
      data.setDirName(dn_);
      data.setFileName(fn_);
   }

   private void assertAttributes(Project data) {
      assertEquals(ot_, data.getOrgType());
      assertEquals(an_, data.getAppName());
      assertEquals(av_, data.getAppVersion());
      assertEquals(dn_, data.getDirName());
      assertEquals(fn_, data.getFileName());
   }

   private void generateNewAttributes() {
      ot_ += Integer.toString(count_++);
      an_ += Integer.toString(count_++);
      av_ += Integer.toString(count_++);
      dn_ += Integer.toString(count_++);
      fn_ += Integer.toString(count_++);
   }
}

