package http;

import http.HttpBody;
import http.HttpHeader;
import http.HttpStartLine;

import java.io.OutputStream;


//TODO: 리팩토링해야함 httpbody와 header는 재사용가능하지만 startline은 구조가 다르기 때문이다.
public class HttpResponse {
    //startline은 따로 클래스화하지 않고 일단 멤버변수로만 다루자
    private int status;

    //이 객체들을 final 지정한 이유는 생성할때 주입 빼고는 불변성을 유지하기 위해 (내부 멤버변수는 setter, getter로만 접근하게 하기 위해서이다.)
    private final HttpHeader httpHeader;
    private final HttpBody httpBody;

    public HttpResponse(HttpHeader httpHeader, HttpBody httpBody) {
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
