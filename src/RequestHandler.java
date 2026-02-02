import http.HttpBody;
import http.HttpHeader;
import http.HttpRequest;
import http.HttpResponse;
import util.HttpParserUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class RequestHandler implements Runnable{
    //각 요청별 스레드 생성 및 관리하여 멀티 스레딩 환경을 유지한다. -> 다시 말해 routing전 작업을 관리한다.
    private final Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //소켓으로 부터 스트림 받아와서 파싱 후 리퀘스트 생성 및 routing 지시
        //참고로 inputStream을 닫으면 소켓도 닫는다는 것을 이용한다.
        try (InputStream inputStream = socket.getInputStream()) {
            HttpRequest request = HttpParserUtils.parse(inputStream);
            HttpResponse response = new HttpResponse();
            Router router = Router.getInstance();
            router.route(request, response);
            //응답 처리

        } catch (IllegalArgumentException e) {
            //parsing중 생성된 예외에 대해 처리하는 부분
            e.printStackTrace();
        } catch (IOException e) {
            //소켓을 닫을때 생성된 에러이므로 딱히 할게 없음..
            e.printStackTrace();
        }
    }
}
