package util;

import request.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpParserUtils {
    static public HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        HttpStartLine requestStratLine = parseStartLine(br.readLine());
        HttpHeader httpHeader = parseHeaders(br);
        HttpBody httpBody = parseBody(br, httpHeader.getContentLength());
        return new HttpRequest(requestStratLine, httpHeader, httpBody);
    }

    static private HttpStartLine parseStartLine(String startLine) throws IOException{
        if (startLine == null || startLine.isEmpty()) {
            throw new IOException("start line is null");
        }
        StringTokenizer st = new StringTokenizer(startLine, " ");
        HttpRequestMethod requestMethod = HttpRequestMethod.httpMethodMapping(st.nextToken());
        String requestUrl = st.nextToken();
        String requestProtocol = st.nextToken();
        return new HttpStartLine(requestMethod, requestUrl, requestProtocol);
    }

    static private HttpHeader parseHeaders(BufferedReader bufferedReader) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String header = bufferedReader.readLine();
        while (!header.isEmpty()) {
            String[] splitedHeader = header.split(":");
            String key = splitedHeader[0];
            String value = splitedHeader[1].trim();
            headers.put(key.toLowerCase(), value.toLowerCase());
            header = bufferedReader.readLine();
        }
        return new HttpHeader(headers);
    }

    static private HttpBody parseBody(BufferedReader bufferedReader, int length) throws IOException {
        if(length == 0) {
            return null;
        }
        char[] body = new char[length];
        bufferedReader.read(body, 0, length);

        return new HttpBody(body);
    }
}
