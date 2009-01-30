package javax.utilx.log;
import javax.swingx.textx.TextView;
import java.io.PrintStream;
/**
 * Copyright dmitry.konovalov@jcu.edu.au Date: 15/09/2008, Time: 12:35:32
 */
public class PStreamToTextView extends PrintStream {
    public PStreamToTextView(TextView from) {
      super(new OStreamToTextView(from));
    }
}
