package qsar.dragon;
import javax.utilx.arrays.StrVec;
import javax.utilx.arrays.vec.Vec;
import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/10/2006, Time: 12:24:30
 */
public class DragonResultsFile
{
  private ArrayList<String> names;
  private ArrayList<Double> arr;
  private String molId;
  private String molName;

  public DragonResultsFile() {
  }

  public void setNames(ArrayList<String> names)
  {
    this.names = names;
  }

  public ArrayList<String> getNames()
  {
    return names;
  }

  public ArrayList<Double> getArr()
  {
    return arr;
  }
  public String formatArrToCSV() {
    double[] a = Vec.asArray(arr);
    return Vec.toCSV(a);
  }
  public String formatNamesToCSV() {
    String[] a = StrVec.asArray(names);
    return StrVec.toCSV(a);
  }

  public void setArr(ArrayList<Double> arr)
  {
    this.arr = arr;
  }

  public void setMolId(String molId)
  {
    this.molId = molId;
  }

  public String getMolId()
  {
    return molId;
  }

  public void setMolName(String molName)
  {
    this.molName = molName;
  }

  public String getMolName()
  {
    return molName;
  }

  public void addFirst(Double absorp, String name)
  {
    arr.add(0, absorp);
    names.add(0, name);
  }
}
