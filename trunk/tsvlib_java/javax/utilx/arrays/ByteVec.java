package javax.utilx.arrays;

/**
 * Created by: jc1386591
 * Date: 15/06/2006. Time: 13:31:45
 */
public class ByteVec {
  static public String toString(byte[] arr) {
    StringBuffer buff = new StringBuffer();
    buff.append("{");
    buff.append(toCSV(arr));
    buff.append("}");
    return buff.toString();
  }
  static public String toCSV(byte[] arr) {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < arr.length; i++) {
      buff.append(arr[i]);
      if (i != arr.length - 1)
        buff.append(", ");
    }
    return buff.toString();
  }
}
