package util;

import http.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpParserUtils {
    //body를 byte로 받기 위해선 bufferedReader가 아니라 inputstream으로 처리하는 리팩토링이 필요함. -> 완료
    //body를 byte로 받아야하는 이유는 없음 모름 나중에 생각하기로.. -> 생김. 왜냐하면 httpbody클래스를 composition관계로 재사용할건데 응답시 byte로 담아야하기 때문이다.

    static public HttpRequest parse(InputStream in) throws IOException {
        //bufferedReader가 아니라 Inputstream의 read메서드를 이용해야 스트림이 꼬이지 않고 바디 byte[]파싱이 가능하다.
        String line = readLine(in);

        HttpStartLine requestStratLine = parseStartLine(line);
        HttpHeader httpHeader = parseHeaders(in);
        HttpBody httpBody = parseBody(in, httpHeader.getContentLength());

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

    static private HttpHeader parseHeaders(InputStream in) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String header = readLine(in);
        while (!header.isEmpty()) {
            String[] splitedHeader = header.split(":", 2);
            String key = splitedHeader[0];
            String value = splitedHeader[1].trim();
            headers.put(key.toLowerCase(), value.toLowerCase());
            header = readLine(in);
        }
        return new HttpHeader(headers);
    }

    static private HttpBody parseBody(InputStream in, int length) throws IOException {
        if(length == 0) {
            return new HttpBody(null);
        }
        byte[] body = readNByte(in, length);
        return new HttpBody(body);
    }


    static private String readLine(InputStream in) throws IOException{
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b = in.read();
        while (b != '\n' && b != -1) {
            if (b != '\r') {
                buffer.write(b);
            }
            b = in.read();
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    //inputSTream클래스에 readNbyte가 있지만 학습을 위해 구현
    static private byte[] readNByte(InputStream in, int length) throws IOException {
        byte[] body = new byte[length];
        int totalRead = 0;
        while (totalRead < length) {
            int read = in.read(body, totalRead, length - totalRead);
            totalRead += read;
        }
        return body;
    }
}
