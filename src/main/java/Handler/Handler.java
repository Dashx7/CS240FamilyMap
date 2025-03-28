package Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Handler {
    //Allows for the readString to be shared across the handlers
    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
