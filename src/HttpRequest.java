import exception.UnsupportedContentTypeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {
    //http 요청에 관련된 책임을 맡은 클래스
    private HttpRequestMethod httpRequestMethod;
    private String url;
    private Map<String, String> queryParametersMap;
    private Map<String, String> headersMap;
    private Map<String, Object> bodyMap = null;

    public void setBodyMap(Map<String, Object> bodyMap) {
        this.bodyMap = bodyMap;
    }

    public void setHttpRequestMethod(HttpRequestMethod httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setQueryParametersMap(Map<String, String> queryParametersMap) {
        this.queryParametersMap = queryParametersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public Map<String, String> getQueryParametersMap() {
        return queryParametersMap;
    }

    public Map<String, Object> getBodyMap() {
        if (bodyMap == null) {
            throw new RuntimeException();
        }
        return bodyMap;
    }

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public String getUrl() {
        return url;
    }


    public Map<String, String> getHeadersMap() {
        return headersMap;
    }
}
