package kingroup.genetics;
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
import kingroup.model.KinshipFileModelV1;
public class GenotypeFormat extends KinshipFileModelV1 {
  private GenotypeFormat() {
    setAlleleFreqs(ALLELE_FREQ_FILE);
    setAlleleDelimiters("/");
    setColumnDelimiters(",");
    setGroupIdColumn(1);
    setIdColumn(2);
    setLocusColumn(3);
    setMaxNumLoci(-1);
  }
}

