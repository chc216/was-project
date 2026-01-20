import controller.Controller;
import request.HttpRequest;
import response.HttpResponse;
import util.HttpParserUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Router {
    public static final Router instance = new Router();

    //굳이 싱글톤을 써야할 이유가 있나?
    // -> 요청마다 라우터를 생성할 필요가 없음
    private Router() {
    }

    public static Router getInstance() {
        return instance;
    }

    //webserver layer이므로 해당 메서드에서 요청/응답 객체를 생성하여 어플리케이션 레이어에서 처리하고 가져와야함
    // -> 그리고 webserver layer가 응답의 책임을 갖기 때문이다.
    public void start(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        HttpRequest request = HttpParserUtils.parse(in);
        HttpResponse response = new HttpResponse();
        route(request, response);
    }

    public void route(HttpRequest request, HttpResponse response) {
        ControllerMapper mapper = new ControllerMapper();
        Controller controller = mapper.getController(request.getRequestStratLine().getUrl());
        controller.service(request, response);
    }
}
