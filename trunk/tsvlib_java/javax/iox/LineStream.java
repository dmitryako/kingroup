package javax.iox;
import java.io.IOException;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/04/2007, 13:29:15
 */
public class LineStream extends FixedSizeStringStream
{
  public void write(int b) throws IOException {
    if (b == '\n')
      buff.setLength(0);
    else
      super.write(b);
  }
}
