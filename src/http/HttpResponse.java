package http;
import java.io.OutputStream;

public class HttpResponse {
    //startline은 규격이 정해져있고 코드만 달라지기 때문에 클래스가 아닌 열거형을 사용했다.
    private final HttpHeader httpHeader;
    private final HttpBody httpBody;
    private final OutputStream out;
    private StatusCode status;

    public HttpResponse(OutputStream out) {
        this.httpHeader = new HttpHeader();
        this.httpBody = new HttpBody();
        this.out = out;
    }

    public void setStatus(StatusCode status) {
        this.status = status;
    }

    public void setContentType(String medeaType, String charSet) {
        httpHeader.put("Content-Type", medeaType + "; charset=" + charSet);
    }

    private int getContentLength() {
        String tmp = httpHeader.get("Content-Length");
        if (tmp == null) {
            return 0;
        }
        return Integer.parseInt(tmp);
    }

    public void setByteBody(byte[] body) {
        httpBody.setBody(body);
        httpHeader.put("content-length", String.valueOf(body.length));
    }

    private byte[] getByteBody() {
        return httpBody.getBody();
    }

    public void send() {
        try {
            StringBuilder response = new StringBuilder();
            response.append("HTTP/1.1 " + status.responseMessage());

            for (String key : httpHeader.getKeys()) {
                response.append(key + ": " + httpHeader.get(key) + "\r\n");
            }
            response.append("\r\n");
            out.write(response.toString().getBytes());

            if (getContentLength() != 0) {
                out.write(getByteBody());
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
