package http;

public class HttpBody {
    private byte[] body;
    public HttpBody(byte[] body) {
        this.body = body;
    }

    public  HttpBody() {
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
