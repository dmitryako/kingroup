package javax.utilx.log;
import javax.swing.*;
import java.awt.*;
import java.util.logging.LogRecord;

/**
 * <code>LoggingCellRender</code>
 * <p/>
 * Date: 3/02/2006
 * Time: 10:36:56
 *
 * @author Nigel Blair
 */
public class LoggingCellRender extends DefaultListCellRenderer {

  public Component getListCellRendererComponent(
      JList list,
      Object value,
      int index,
      boolean isSelected,
      boolean cellHasFocus) {
    LogRecord v = (LogRecord) value;
    setComponentOrientation(list.getComponentOrientation());
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    if (value instanceof Icon) {
      setIcon((Icon) value);
      setText("");
    } else {
      setIcon(null);
      setText((value == null) ? "" : v.getLevel() + ": " + v.getMessage());
    }

    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setBorder((cellHasFocus) ? UIManager
        .getBorder("List.focusCellHighlightBorder") : noFocusBorder);

    return this;
  }
}
