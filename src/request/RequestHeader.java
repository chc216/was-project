package request;

import java.util.Map;

public class RequestHeader {
    private Map<String, String> headers = null;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Object getHeader(String key) {
        return headers.get(key);
    }

    public Integer getContentLength() {
        if (headers.containsKey("content-length")) {
            return Integer.parseInt(headers.get("content-length"));
        }
        else {
            return 0;
        }
    }
}
