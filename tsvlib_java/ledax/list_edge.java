package ledax;
import java.util.TreeSet;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 26, 2004, Time: 11:29:36 AM
 */
//public class list_edge extends HashSet {
public class list_edge extends TreeSet {
  public void append(edge e) {
    this.add(e);
  }
}
