package http;

public class HttpStartLine {
    private final HttpRequestMethod method;
    private final String url;
    private final String protocol;

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
