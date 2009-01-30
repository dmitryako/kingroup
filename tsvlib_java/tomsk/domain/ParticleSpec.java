package tomsk.domain;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 8/05/2007, 13:40:04
 */
public class ParticleSpec
{
  public static int H = 1;
  public static int O = 8;
  public static int C = 8;
  public static int Na = 2;

  private String name;
  private double weight;
//  private int code;

  public double getWeight()
  {
    return weight;
  }

  public void setWeight(double weight)
  {
    this.weight = weight;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

//  public int getCode()
//  {
//    return code;
//  }
//
//  public void setCode(int code)
//  {
//    this.code = code;
//  }
}
