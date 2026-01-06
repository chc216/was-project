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
    private Map<String, String> urlParametersMap = new HashMap<>();
    private Map<String, String> headersMap = new HashMap<>();
    private Object body;

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getUrlParameters() {
        return urlParametersMap;
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public HttpRequest(InputStream request) throws IOException {
        if (request == null) {
            throw new IOException("request cannot be null");
        }
        parse(request);
    }

    private void parse(InputStream request) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request));

        String startLine = bufferedReader.readLine();
        if (startLine == null || startLine.isEmpty()) {
            throw new IOException("Empty start line");
        }
        parseStartLine(startLine);

        String header = bufferedReader.readLine();
        while (!header.isEmpty()) {
            parseHeaderLine(header);
            header = bufferedReader.readLine();
        }

        //TODO: http body 유무에 따라 다르게 body 파싱 여부 다르게 처리하기
        int len = Integer.parseInt(headersMap.get("content-length"));
        if (len > 0) {
            char[] body = new char[len];
            bufferedReader.read(body, 0, len);
            RequestBodyParser requestBodyParser = switch (headersMap.get("content-type")) {
                case "application/x-www-form-urlencoded" -> new UrlEncodedBodyParser();
                default -> throw new UnsupportedContentTypeException(headersMap.get("content-type") + " doesn't supported");
            };
            this.body = requestBodyParser.parse(String.valueOf(body));
        }
    }

    private void parseStartLine(String startLine) {

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
            urlParametersMap.put(key, value);
        }

    }

    private void parseHeaderLine(String header) {
        StringTokenizer headerTokenizer = new StringTokenizer(header, ": ");
        headersMap.put(headerTokenizer.nextToken().toLowerCase(), headerTokenizer.nextToken());
    }

//    private void parseBody(char[] body, int len, String contentType) {
//        //x-www-form-urlencoded 형식만 지원
//        //TODO: contentType enum 만들기, 파싱 지원하지 않는 contentType 예외처리 로직 만들기
//        System.out.println(body);
//        StringTokenizer bodyTokenizer = new StringTokenizer(String.valueOf(body), "&");
//        while (bodyTokenizer.hasMoreTokens()) {
//            StringTokenizer keyValueTokenizer = new StringTokenizer(bodyTokenizer.nextToken(), "=");
//            String key = keyValueTokenizer.nextToken();
//            String value = keyValueTokenizer.nextToken();
//            System.out.println("value = " + value);
//            System.out.println("key = " + key);
//            //TODO: body content 종류에 따라 저장하는 방식도 달라져야함
//        }
//
//    }

}
