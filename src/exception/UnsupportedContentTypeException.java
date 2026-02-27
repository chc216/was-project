package exception;

import java.io.IOException;


//신기해서 만들어봄
public class UnsupportedContentTypeException extends IOException {
    public UnsupportedContentTypeException(String message) {
        super(message);
    }
}
