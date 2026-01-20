package request;

public class HttpStartLine {
    HttpRequestMethod method = null;
    String url = null;
    String protocol = null;

    public HttpStartLine(HttpRequestMethod method, String url, String protocol) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }
}
