package javax.utilx.log;
import javax.swingx.textx.TextView;
import java.io.OutputStream;
/**
 * Copyright dmitry.konovalov@jcu.edu.au Date: 11/09/2008, Time: 15:54:23
 */
// from http://www.jcreator.com/forums/index.php?showtopic=773
public class OStreamToTextView extends OutputStream {
    private TextView to;
    public OStreamToTextView(TextView from) {
        to = from;
    }
    public void write( int b )  {
        to.print(String.valueOf( (char)b ) );
    }
}