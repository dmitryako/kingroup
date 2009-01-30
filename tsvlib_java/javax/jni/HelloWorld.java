public class HelloWorld {
  public native void displayHelloWorld();
  static {
    System.loadLibrary("helloworld_dll");
  }
  public static void main(String[] args) {
    new HelloWorld().displayHelloWorld();
  }
}

