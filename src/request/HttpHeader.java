package request;

import java.util.Map;

public class HttpHeader {
    private Map<String, String> headers = null;

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Object getHeader(String key) {
        if (headers.containsKey(key)) {
            return headers.get(key);
        }
        return null;
    }

    public boolean setHeader(String key, String value) {
        if (headers.containsKey(key)) {
            return false;
        }
        headers.put(key, value);
        return true;
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
