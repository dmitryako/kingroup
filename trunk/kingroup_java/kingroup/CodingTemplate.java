// PACKAGE FIRST
package kingroup;

// copyright etc
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */

// imports 
//import com..project.*;

// Application imports first

// java imports nextIdx
public class CodingTemplate {
  // STATIC attributes
  public static int SOME_CLASS_CONST = 10;
  // PRIVATE attributes
  private int somePriv_;  // use postfix "_" for privates
  private int some_priv_ = 0;
  private int size_ = 0;
  // PUBLIC operations (including statics)
  public CodingTemplate(int val) {
    somePriv_ = val;
  }
  public int size() {
    return size_;
  }
  public void size(int _size) {
    size_ = _size;
  } // may use prefix "_" for parameters
  public void setSize(int _size) {
    size_ = _size;
  }
  // PRIVATE operations
  private void doSomething() {
    // note spacing everywhere: e.g. see nextIdx line
    for (int i = 0; i < SOME_CLASS_CONST; i++) {
      // not
      // for(int i=0;i<SOME_CLASS_CONST;i++){
      int j = i * 2;
    }
  }
}
