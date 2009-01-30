package javax.swingx.text_fieldx;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/11/2005, Time: 10:46:50
 */
public class IntegerListModel
{
  private int fieldSize = 2;
  private int minValue = 0;
  private int maxValue = 10;
  private int width = 50;
  private int height = 50;

  public int getMinValue()
  {
    return minValue;
  }

  public void setMinValue(int minValue)
  {
    this.minValue = minValue;
  }

  public int getFieldSize()
  {
    return fieldSize;
  }

  public void setFieldSize(int fieldSize)
  {
    this.fieldSize = fieldSize;
  }

  public int getMaxValue()
  {
    return maxValue;
  }

  public void setMaxValue(int maxValue)
  {
    this.maxValue = maxValue;
  }

  public int getWidth()
  {
    return width;
  }

  public void setWidth(int width)
  {
    this.width = width;
  }

  public int getHeight()
  {
    return height;
  }

  public void setHeight(int height)
  {
    this.height = height;
  }
}
