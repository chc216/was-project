package util;

import http.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class HttpParserUtils {

    //request객체는 raw데이터를 가지고 있다가 필요할 때 가공하여 제공하는 것이 더 효율적일 것이다.
    //  왜냐하면 httpbody클래스를 response객체에서도 재사용할 예정인데 응답을 위해선 이미지 데이터를 위해 내부를 byte[]로 구현해야하기 때문이다.
    //  또한 request객체 생성과 동시에 데이터를 가공하여 집어넣는다면 나중에 비즈니스 로직에서 어떤 데이터가 필요해질지 모르기 때문에 최대한 마지막까지 raw데이터를 가지고 있다가 필요할때 가공을 하는게 더 낫다.
    //  bufferedReader가 아니라 Inputstream의 read메서드를 이용해야 스트림이 꼬이지 않고 바디 byte[]파싱이 가능하다.
    //따라서 Parse메서드는 bufferedReader가 아닌 InputStream을 인수로 받는다.
    static public HttpRequest parse(InputStream in) throws IOException {
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
        //각 http메서드 문자열을 열거형으로 매핑하는 책임은 위임한다.
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
        //바이트 가변 배열을 사용한다.
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b = in.read();
        while (b != '\n' && b != -1) {
            //http프로토콜에서 줄바꿈 문자는 "\r\n"이기 때문에 '\r'은 무시한다.
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
