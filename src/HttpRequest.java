import java.io.IOException;
import java.util.Map;


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

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public String getUrl() {
        return url;
    }


    //header및 body는 빈값을 조회할때 예외를 던지면 안되는이유
    //1. header, body는 빈값이 존재할 수도 있는 유연한 데이터임
    //2. 근데 무조건 예외를 던져버리면 유연한 처리가 어려움 (바로 소켓 종료 로직으로 이어지기 때문에)
    //3. 따라서 null값을 보낸 후 클라이언트에서 처리한다.
    public String getHeader(String key) {
        if (!headersMap.containsKey(key.toLowerCase())) {
            return null;
        }
        return headersMap.get(key.toLowerCase());
    }

    public Object getBodyValue(String key) {
        if (!bodyMap.containsKey(key)) {
        }
        return bodyMap.get(key);
    }
}
