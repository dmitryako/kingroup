package kingroup.io;
/** JKinship    Copyright (C) 2003  Dr.D.A.Konovalov
 *  
 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation;

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details (file license_gpl.txt).

 You should have received a copy of the GNU General Public License
 along with this program (file license_gpl.txt); if not, see http://www.gnu.org .

 * @see www.it.jcu.edu.au/kinship for full JKinship Licensing and more details
 * or contact Dmitry.Konovalov@jcu.edu.au
 */
import java.io.File;
public class KUtils {
  public final static String log = "log";
  public final static String proj = "xml";
  /*
  * Get the extension of a file.
  */
  public static String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');
    if (i > 0 && i < s.length() - 1) {
      ext = s.substring(i + 1).toLowerCase();
    }
    return ext;
  }
}

