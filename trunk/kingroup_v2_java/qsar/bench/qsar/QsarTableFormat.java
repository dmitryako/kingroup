package qsar.bench.qsar;
import javax.iox.TableFormat;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 13/03/2007, 17:19:45
 */
public class QsarTableFormat extends TableFormat
  implements QsarTypeI
{
  private int qsarType;

  public int getQsarType()
  {
    return qsarType;
  }

  public void setQsarType(int qsarType)
  {
    this.qsarType = qsarType;
  }

}
