package kingroup.io;
import kingroup.model.KinshipFileModelV1;
import kingroup.project.KinGroupProjectV1;

import javax.iox.DataSourceModel;
import java.io.File;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/04/2005, Time: 14:20:08
 */
public class KinshipDataSource extends DataSourceModel {
  public KinshipDataSource(File file) {
    super(file);
    separateDelimiters();
  }
  public void separateDelimiters() {
    KinshipFileModelV1 format = KinGroupProjectV1.getInstance().getKinshipFormatModel();
    String str = " ";
    for (int idx = 0; idx < size(); idx++) {
      String line = get(idx);
      if (format.ignoreInputLine(line))
        continue;
      line = separateColumnAndAlleleDelimiters(line, str);
      set(idx, line);
    }
  }
  private String separateColumnAndAlleleDelimiters(String from, String str) {
    KinshipFileModelV1 format = KinGroupProjectV1.getInstance().getKinshipFormatModel();
    StringBuffer buff = new StringBuffer(from);
    if (format.isColumnDelimiter(buff.charAt(0))
      || format.isAlleleDelimiter(buff.charAt(0)))
      buff.insert(0, str);
    for (int i = 1; i < buff.length(); i++) {  // NOTE: 1
      if (format.isColumnDelimiter(buff.charAt(i))    // >,,<
        && format.isColumnDelimiter(buff.charAt(i - 1)))
        buff.insert(i, str);
      if (format.isColumnDelimiter(buff.charAt(i))    // >,/<
        && format.isAlleleDelimiter(buff.charAt(i - 1)))
        buff.insert(i, str);
      if (format.isColumnDelimiter(buff.charAt(i - 1))  // >,/<
        && format.isAlleleDelimiter(buff.charAt(i)))
        buff.insert(i, str);
      if (format.isAlleleDelimiter(buff.charAt(i))      // >//<
        && format.isAlleleDelimiter(buff.charAt(i - 1)))
        buff.insert(i, str);
    }
    return buff.toString();
  }
}
