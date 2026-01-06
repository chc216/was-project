import exception.UnsupportedContentTypeException;

import java.io.IOException;

public class HttpBodyParserFactory {
    public HttpBodyParser createBodyParser(String type) throws IOException {
        if (type.equals("application/x-www-form-urlencoded")) {
            return new UrlEncodedBodyParser();
        } else {
            throw new UnsupportedContentTypeException("can't handle another type");
        }
    }
}
