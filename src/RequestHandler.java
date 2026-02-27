import http.HttpBody;
import http.HttpHeader;
import http.HttpRequest;
import http.HttpResponse;
import util.HttpParserUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

public class RequestHandler implements Runnable{
    //각 요청별 스레드 생성 및 관리하여 멀티 스레딩 환경을 유지한다. -> 다시 말해 라우팅 전의 작업을 관리한다.
    private final Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //참고로 try-resource문을 사용한 이유는 inputStream을 닫으면 소켓도 닫힌다는 것을 이용했다.
        try (InputStream inputStream = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
            HttpRequest request = HttpParserUtils.parse(inputStream);
            HttpResponse response = new HttpResponse(out);
            Router router = Router.getInstance();
            router.route(request, response);
            response.send();
        } catch (IllegalArgumentException e) {
            //parsing중 생성된 예외에 대해 처리하는 부분
        } catch (IOException e) {
            //소켓을 닫을때 생성된 에러이므로 딱히 할게 없음..
        }
    }
}
