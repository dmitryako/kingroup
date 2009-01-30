package ledax;
import javax.utilx.HashMapToBool;
import java.util.Iterator;
import java.util.Set;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 27, 2004, Time: 11:33:22 AM
 */
public class node_array_bool extends HashMapToBool {
  public node_array_bool(graph g, boolean b) {
    set_forall(g.list_node(), b);
  }
  public void set_forall(list_node list, boolean b) {
    for (int j = 0; j < list.size(); j++) {
      node n = (node) list.get(j);
      put(n, new Boolean(b));
    }
  }
  public String toString() {
    return toString(false);
  }
  public String toString(boolean byRows) {
    StringBuffer buff = new StringBuffer();
    Set keys = keySet();
    buff.append('{');
    for (Iterator it = keys.iterator(); it.hasNext();) {
      node n = (node) it.next();
      Boolean b = (Boolean) get(n);
      buff.append(n.toString()).append("->").append(b.toString());
      if (!it.hasNext()) {
        buff.append('}');
        break;
      }
      if (byRows)
        buff.append('\n');
      else
        buff.append(", ");
    }
    return buff.toString();
  }
}
