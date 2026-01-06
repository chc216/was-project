import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpParser {
    private final HttpBodyParserFactory httpBodyParserFactory;

    public HttpParser(HttpBodyParserFactory httpBodyParserFactory) {
        this.httpBodyParserFactory = httpBodyParserFactory;
    }

    public HttpRequest parse(InputStream inputStream) throws IOException {

        HttpRequest request = new HttpRequest();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        parseStartLine(bufferedReader.readLine(), request);
        parseHeaders(bufferedReader, request);

        Map<String, String> headersMap = request.getHeadersMap();
        int contentLength = Integer.parseInt(headersMap.get("content-length"));
        if (contentLength > 0) {
            String contentType = headersMap.get("content-type");
            HttpBodyParser bodyParser = httpBodyParserFactory.createBodyParser(contentType);
            char[] body = new char[contentLength];
            bufferedReader.read(body, 0, contentLength);
            Map<String, Object> bodyMap = bodyParser.parse(String.valueOf(body));
            request.setBodyMap(bodyMap);
        }

        return request;
    }

    private void parseStartLine(String startLine, HttpRequest request) throws IOException{
        if (startLine == null || startLine.isEmpty()) {
            throw new IOException("start line is null");
        }
        StringTokenizer stringTokenizer = new StringTokenizer(startLine, " ");
        String unparsedRequestMethod = stringTokenizer.nextToken();
        String unparsedUrl = stringTokenizer.nextToken();
        String unparsedHttpProtocol = stringTokenizer.nextToken();
        request.setHttpRequestMethod(HttpRequestMethod.httpMethodMapping(unparsedRequestMethod));

        StringTokenizer urlTokenizer = new StringTokenizer(unparsedUrl, "?");
        String endpoint = urlTokenizer.nextToken();
        request.setUrl(endpoint);

        String queryString = urlTokenizer.nextToken();
        StringTokenizer queryParameterTokenizer = new StringTokenizer(queryString, "&");
        Map<String, String> queryParametersMap = new HashMap<>();
        while (queryParameterTokenizer.hasMoreTokens()) {
            queryParametersMap.put(queryParameterTokenizer.nextToken(), queryParameterTokenizer.nextToken());
        }
        request.setQueryParametersMap(queryParametersMap);
    }

    private void parseHeaders(BufferedReader bufferedReader, HttpRequest request) throws IOException {
        Map<String, String> headersMap = new HashMap<>();
        String header = bufferedReader.readLine();
        while (!header.isEmpty()) {
            StringTokenizer keyValueTokenizer = new StringTokenizer(header, ": ");
            String key = keyValueTokenizer.nextToken();
            String value = keyValueTokenizer.nextToken();
            headersMap.put(key.toLowerCase(), value.toLowerCase());
            header = bufferedReader.readLine();
        }

        request.setHeadersMap(headersMap);
    }

}
