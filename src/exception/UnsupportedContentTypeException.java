package exception;

public class UnsupportedContentTypeException extends RuntimeException {
    public UnsupportedContentTypeException(String message) {
        super(message);
    }
}
