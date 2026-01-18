package request;

public class RequestStratLine {
    HttpRequestMethod method = null;
    String url = null;
    String protocol = null;

    public RequestStratLine(HttpRequestMethod method, String url, String protocol) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
    }
}
