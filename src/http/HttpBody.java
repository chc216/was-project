package http;

public class HttpBody {
    private byte[] body;
    public byte[] getBody() {
        return body;
    }

    public HttpBody(byte[] body) {
        this.body = body;
    }

}
