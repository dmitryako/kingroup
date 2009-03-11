package javax.swingx.text_fieldx;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/04/2006, Time: 16:49:56
 */
public class FloatTextField extends FormattedNumberField
{
  public static void main(String[] args) {
      FloatTextField test = new FloatTextField();
      System.exit(0);
  }
  public FloatTextField() {
    super(new Float(0), new Float(10), new DecimalFormat());
    FloatTextFieldTest test = new FloatTextFieldTest();
  }
//  public FloatTextField(float min, float max) {
//    super(new Float(min), new Float(max), new DecimalFormat());
//    setValue(new Float(min));
//  }
  public FloatTextField(int columns, float min, float max) {
    super(new Float(min), new Float(max), new DecimalFormat());
    setColumns(columns);
  }
  public FloatTextField(int columns, float value, float min, float max) {
    super(new Float(min), new Float(max), new DecimalFormat());
    setColumns(columns);
    setValue(new Float(value));
  }

  public float getInput()
  {
    return ((Float)getValue()).floatValue();
  }

  public void setValue(float v)
  {
    setValue(new Float(v));
  }

private class FloatTextFieldTest extends JFrame {

    public FloatTextFieldTest() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(7, 2));
        JPanel field = new JPanel();

//        field = new JPanel();
//        field.add(new FloatTextField(2, 3f));  // columns=2
//        panel.add(field);
//        panel.add(new JLabel("FloatTextField(columns=2, value=3)"));

        field = new JPanel();
        field.add(new FloatTextField(1, 0f, 9f));  // number [0, 9]
        panel.add(field);
        panel.add(new JLabel("FloatTextField(columns=1, value=minIdx=0, maxIdx=9)"));

        field = new JPanel();
        field.add(new FloatTextField(3, 1f, 0f, 9.9f));  // number [0, 9]
        panel.add(field);
        panel.add(new JLabel("FloatTextField(columns=3, 1, minIdx=0, maxIdx=9)"));

        field = new JPanel();
        field.add(new FloatTextField(4, -1f, 0f, 9f)); // number [0, 9]
        panel.add(field);
        panel.add(new JLabel("FloatTextField(columns=4, -1, minIdx=0, maxIdx=9)"));

        field = new JPanel();
        field.add(new FloatTextField(5, -1f, 9f, 0f)); // any number
        panel.add(field);
        panel.add(new JLabel("FloatTextField(columns=5, -1, minIdx=9, maxIdx=0 [note: minIdx > maxIdx!])"));

        ApplyDialogUI dlg = new ApplyDialogUI(panel, this, true);
        dlg.setVisible(true);
    }
}}
