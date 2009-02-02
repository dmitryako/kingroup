package kingroup.model;
import javax.swing.table.DefaultTableModel;
import java.io.PrintWriter;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Jul 29, 2004, Time: 9:44:43 AM
 */
public class KinshipFormatWriterV1 {
  private KinshipFileModelV1 model_;
  public KinshipFormatWriterV1(KinshipFileModelV1 model) {
    model_ = model;
  }
  public void printTableModel(PrintWriter stream, DefaultTableModel data
    , boolean asComment) {
    if (stream == null || model_ == null)
      return;
    String comment = "" + model_.getPreferredCommentMarker() + " ";
    String delim = "" + model_.getPreferredColumnDelimiter() + " ";
    if (data == null) {
      stream.println(comment + delim + " NO DATA");
      return;
    }
    for (int c = 0; c < data.getColumnCount(); c++) {
      if (asComment && c == 0)
        stream.print(comment + delim);
      if (c == data.getColumnCount() - 1)
        stream.println(data.getColumnName(c)); // last column
      else
        stream.print(data.getColumnName(c) + delim);
    }
    for (int r = 0; r < data.getRowCount(); r++) {
      for (int c = 0; c < data.getColumnCount(); c++) {
        if (asComment && c == 0)
          stream.print(comment + delim);
        String str = (String) data.getValueAt(r, c);
        if (str == null || str.length() < 1)
          str = " ";
        if (c == data.getColumnCount() - 1)
          stream.println(str);
        else
          stream.print(str + delim);
      }
    }
  }
  public void printTransposed(PrintWriter stream, DefaultTableModel data
    , boolean asComment, int limit_col) {
    if (stream == null || model_ == null)
      return;
    String comment = "" + model_.getPreferredCommentMarker();
    String delim = "" + model_.getPreferredColumnDelimiter();
    if (data == null) {
      stream.println(comment + delim + " NO DATA");
      return;
    }
    int max_col = data.getRowCount();
    if (limit_col > 0 && limit_col < max_col) {
      stream.print(comment + delim);
      stream.println("NOTE that only first " + limit_col
        + " of " + max_col + " columns were displayed.");
      max_col = limit_col;
    }

    // write rows into columns
    for (int r = 0; r < data.getColumnCount(); r++) {
      if (asComment)
        stream.print(comment + delim);
      stream.print(data.getColumnName(r) + delim);
      for (int c = 1; c <= max_col; c++) {
        String str = (String) data.getValueAt(c - 1, r);
        if (str.length() < 1)
          str = " ";
        if (c == max_col)
          stream.println(str);
        else
          stream.print(str + delim);
      }
    }
  }
}
