package http;

import http.HttpBody;
import http.HttpHeader;
import http.HttpStartLine;

import java.io.OutputStream;
import java.io.OutputStreamWriter;


//TODO: 리팩토링해야함 httpbody와 header는 재사용가능하지만 startline은 구조가 다르기 때문이다.
// -> header, body객체는 재사용을 위해 모든 메서드를 열어두었고 response에서 메서드를 제한하여 내부 구현을 감춘다.
public class HttpResponse {
    //startline은 따로 클래스화하지 않고 일단 멤버변수로만 다루자
    private StatusCode status;

    //이 객체들을 final 지정한 이유는 생성할때 주입 빼고는 불변성을 유지하기 위해 (내부 멤버변수는 setter, getter로만 접근하게 하기 위해서이다.)
    // -> 취소함 -> 왜냐하면 request는 파싱 후 불변객체이어야 데이터 유실이 일어나지 않겠지만 response는 생성 후 만들어져가는 객체라서 final을 유지할 필요가 없음

    private HttpHeader httpHeader;
    private HttpBody httpBody;
    private OutputStream out;

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
    public String getContentType() {
        return httpHeader.get("Content-Type");
    }

    public int getContentLength() {
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

    public byte[] getByteBody() {
        return httpBody.getBody();
    }

    public String getStringBody() {
        return new String(httpBody.getBody());
    }

    public void send() {
        //아직은 200 ok답밖에 못함;;
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
