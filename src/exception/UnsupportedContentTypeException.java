package exception;

import java.io.IOException;

public class UnsupportedContentTypeException extends IOException {
    public UnsupportedContentTypeException(String message) {
        super(message);
    }
}
