package http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HttpHeader {
    private Map<String, String> headers;

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }
    public HttpHeader() { this.headers = new HashMap<>();}

    public String get(String key) {
        if (headers.containsKey(key.toLowerCase())) {
            return headers.get(key.toLowerCase());
        }
        return null;
    }

    public Set<String> getKeys() {
        return headers.keySet();
    }

    public boolean put(String key, String value) {
        if (headers.containsKey(key)) {
            return false;
        }
        headers.put(key.toLowerCase(), value);
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
