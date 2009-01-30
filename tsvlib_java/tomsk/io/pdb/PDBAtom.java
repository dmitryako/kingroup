package tomsk.io.pdb;
import javax.vecmath.Vector3d;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 8/05/2007, 16:54:28
 */
public class PDBAtom
{
  public static final String TYPE = "ATOM";
  private int serialNum;
  private String name;
  private Vector3d coord;

  public PDBAtom() {
    coord = new Vector3d();
  }

  public void setSerialNum(int serialNum)
  {
    this.serialNum = serialNum;
  }

  public int getSerialNum()
  {
    return serialNum;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setX(double x)
  {
    this.coord.x = x;
  }

  public double getX()
  {
    return coord.x;
  }

  public void setY(double y)
  {
    this.coord.y = y;
  }

  public double getY()
  {
    return coord.y;
  }

  public void setZ(double z)
  {
    this.coord.z = z;
  }

  public double getZ()
  {
    return coord.z;
  }

  public Vector3d getCoord() {return coord;}
}
