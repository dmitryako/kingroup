package javax.swingx.text_fieldx;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/04/2006, Time: 09:49:36
 */
public class JFormattedTextFieldX  extends JFormattedTextField
{
  private final Comparable min;
  private final Comparable max;
  private NumberFormat format;

//  public void setText(String str) {
//    NumberFormat format = getFormat();
//    try {
//      setValue(format.parse(str));
//    } catch (ParseException e) {
//      throw new RuntimeException("use setValue instead");
//    }
//  }
  protected JFormattedTextFieldX(Comparable min, Comparable max, NumberFormat format) {
    if (min.compareTo(max) <= 0) {
      this.min = min;
      this.max = max;
    }
    else {
      this.min = max;
      this.max = min;
    }
    this.format = format;
    setInputVerifier(new ThisInputVerifier());

    NumberFormatter formatter = new NumberFormatter(format);
    formatter.setFormat(format);
    formatter.setMinimum(min);
    formatter.setMaximum(max);
    setFormatterFactory(new DefaultFormatterFactory(formatter));
    setValue(min);
    setHorizontalAlignment(JTextField.TRAILING);
    setFocusLostBehavior(JFormattedTextField.PERSIST);
//    setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);

    //React when the user presses Enter while the editor is
    //active.  (Tab is handled as specified by
    //JFormattedTextField's focusLostBehavior property.)
//    getInputMap().put(KeyStroke.getKeyStroke(
//      KeyEvent.VK_ENTER, 0),
//      "check");
//    getActionMap().put("check", new AbstractAction() {
//      public void actionPerformed(ActionEvent e) {
//        if (!isEditValid()) { //The text is invalid.
//          if (userSaysRevert()) { //reverted
//            postActionEvent(); //inform the editor
//          }
//        } else try {              //The text is valid,
//          commitEdit();     //so use it.
//          postActionEvent(); //stop editing
//        } catch (java.text.ParseException exc) { }
//      }
//    });
  }

  public void setValue(Object obj) {
    Comparable c = (Comparable)obj;
    if (c.compareTo(min) < 0)
      super.setValue(min);
    else if (c.compareTo(max) > 0)
      super.setValue(max);
    else
      super.setValue(obj);
  }

  public boolean userSaysRevert() {
    Toolkit.getDefaultToolkit().beep();
    selectAll();
    Object[] options = {"Edit",
      "Revert"};
    int answer = JOptionPane.showOptionDialog(
      SwingUtilities.getWindowAncestor(this),
      "The value must be between "
        + format.format(min) + " and "
        + format.format(max) + ".\n"
        + "You can either continue editing "
        + "or revert to the last valid value.",
      "Invalid Text Entered",
      JOptionPane.YES_NO_OPTION,
      JOptionPane.ERROR_MESSAGE,
      null,
      options,
      options[1]);

    if (answer == 1) { //Revert!
      setValue(getValue());
      return true;
    }
    return false;
  }

  public NumberFormat getFormat()
  {
    return format;
  }



  private class ThisInputVerifier extends InputVerifier {
    public boolean verify(JComponent input) {
      if (!(input instanceof JFormattedTextField))
        return true;
      JFormattedTextField ftf = (JFormattedTextField)input;
      if (ftf.isEditValid()) {
        try {
          ftf.commitEdit();     //so use it.
        } catch (ParseException e) {
          return false;
//          e.printStackTrace();
        }
        return true;
      }
      if (userSaysRevert())
        return true;
      return false;
    }
    public boolean shouldYieldFocus(JComponent input) {
      return verify(input);
    }
  }

}
