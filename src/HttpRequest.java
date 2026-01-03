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
    private Map<String, String> urlParameters = new HashMap<>();
    private String host;
    private int bodyLength;
    private String contentType;
    private byte[] messageBody;

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getUrlParameters() {
        return urlParameters;
    }

    public HttpRequest(InputStream request) throws IOException {
        if (request == null) {
            throw new IOException("request cannot be null");
        }
        parse(request);
    }

    private void parse(InputStream request) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request));
        parseStartLine(bufferedReader);
        parseHeaders(bufferedReader);
    }


    private void parseStartLine(BufferedReader bufferedReader) throws IOException {
        String startLine = bufferedReader.readLine();
        if (startLine == null || startLine.isEmpty()) {
            throw new IOException("Empty request");
        }
        StringTokenizer stringTokenizer = new StringTokenizer(startLine, " ");

        String httpRequestMethod = stringTokenizer.nextToken();
        this.httpRequestMethod = HttpRequestMethod.httpMethodMapping(httpRequestMethod);

        String url = stringTokenizer.nextToken();
        StringTokenizer urlTokenizer = new StringTokenizer(url, "?");
        this.url = urlTokenizer.nextToken();
        if (urlTokenizer.hasMoreTokens()) {
            parseUrlParameter(urlTokenizer.nextToken());
        }


    }

    private void parseUrlParameter(String parameters) {

        StringTokenizer parameterTokenizer = new StringTokenizer(parameters, "&");
        while (parameterTokenizer.hasMoreTokens()) {
            String parameter = parameterTokenizer.nextToken();
            StringTokenizer keyValueTokenizer = new StringTokenizer(parameter, "=");
            String key = keyValueTokenizer.nextToken();
            String value = keyValueTokenizer.nextToken();
            urlParameters.put(key, value);
        }

    }

    private void parseHeaders(BufferedReader bufferedReader) throws IOException {

    }

}
