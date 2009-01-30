package javax.swingx.text_fieldx;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/04/2006, Time: 17:18:51
 */
public class IntTextField extends JFormattedTextFieldX
{
  public static void main(String[] args) {
    new IntTextField();
    System.exit(0);
  }
  public IntTextField() {
    super(new Integer(0), new Integer(10), NumberFormat.getIntegerInstance());
    ThisTest test = new IntTextField.ThisTest();
  }
  public IntTextField(int min, int max) {
    super(new Integer(min), new Integer(max), NumberFormat.getIntegerInstance());
    setValue(new Integer(min));
  }
  public IntTextField(int columns, int min, int max) {
    super(new Integer(min), new Integer(max), NumberFormat.getIntegerInstance());
    setColumns(columns);
  }
  public IntTextField(int columns, int value, int min, int max) {
    super(new Integer(min), new Integer(max), NumberFormat.getIntegerInstance());
    setColumns(columns);
    setValue(new Integer(value));
  }


  public void setValue(int i)
  {
    setValue(new Integer(i));
  }

  public int getInput()
  {
    return ((Integer)getValue()).intValue();
  }

  private class ThisTest extends JFrame
  {
    public ThisTest() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel panel = new JPanel(new GridLayout(7, 2));
      JPanel field = new JPanel();

      field = new JPanel();
      field.add(new IntTextField(2, 3));  // columns=2
      panel.add(field);
      panel.add(new JLabel("IntTextField(columns=2, value=3)"));

      field = new JPanel();
      field.add(new IntTextField(10, 0, 9));  // number [0, 9]
      panel.add(field);
      panel.add(new JLabel("IntTextField(columns=10, value=minIdx=0, maxIdx=9)"));

      field = new JPanel();
      field.add(new IntTextField(3, 1, 0, 9));  // number [0, 9]
      panel.add(field);
      panel.add(new JLabel("IntTextField(columns=3, 1, minIdx=0, maxIdx=9)"));

      field = new JPanel();
      field.add(new IntTextField(4, -1, 0, 9)); // number [0, 9]
      panel.add(field);
      panel.add(new JLabel("IntTextField(columns=4, -1, minIdx=0, maxIdx=9)"));

      field = new JPanel();
      field.add(new IntTextField(5, -1, 9, 0)); // any number
      panel.add(field);
      panel.add(new JLabel("IntTextField(columns=5, -1, minIdx=9, maxIdx=0 [note: minIdx > maxIdx!])"));

      ApplyDialogUI dlg = new ApplyDialogUI(panel, this, true);
      dlg.setVisible(true);
    }
  }
}
