package request;


public class HttpRequest {

/*
기존 httpRequest객체가 모든 정보를 파싱하여 담고 관련 연산을 제공했는데
모든 데이터를 처음부터 파싱하면 비즈니스 로직에서 재가공해야할 가능성이 있다. request객체는 raw데이터만 담고 유의미한 데이터로 가공은 비즈니스 로직에서 한다.
데이터 가공 연산은 request객체에서 제공 (parser는 단지 inputstream을 raw데이터로 저장할 뿐이다.)
모든 변수를 가지고 있던데에서 composition관계로 수정하여 각 메서드는 헤더는 헤더끼리, 바디는 바디끼리 묶어서 가독성과 유지보수성을 높임
*/
    final private HttpStartLine requestStratLine;
    final private HttpHeader httpHeader;
    final private HttpBody httpBody;

    public HttpRequest(HttpStartLine requestStratLine, HttpHeader httpHeader, HttpBody httpBody) {
        this.requestStratLine = requestStratLine;
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public HttpStartLine getRequestStratLine() {
        return requestStratLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }
}
