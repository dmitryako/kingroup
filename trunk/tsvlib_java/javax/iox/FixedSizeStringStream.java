package javax.iox;

import java.io.IOException;
import java.io.OutputStream;

public class FixedSizeStringStream extends OutputStream
{
  private int bufferSize = 1000;
  protected final StringBuffer buff;

  public void setBufferSize(int size) {
    bufferSize = size;
  }

  public FixedSizeStringStream() {
    buff = new StringBuffer();
  }
  public FixedSizeStringStream(int size) {
    buff = new StringBuffer();
    setBufferSize(size);
  }
  public void write(int b) throws IOException {
    buff.append(b);
    if (buff.length() <= bufferSize)
      return;
    buff.deleteCharAt(0);
  }
  public String toString() {
    return buff.toString();
  }
  public void flush() throws IOException {
  }
  public void close() throws IOException {
    buff.setLength(0);
  }

}
