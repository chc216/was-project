//목표: 프로그램을 실행하고 브라우저로 localhost:8080에 접속했을 때, 프로그램이 꺼지지 않고 뭔가 반응이 있게 하기.


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;


//수정 내용: main메서드는 진입점 역할만 해야하고, 따로 thread에 작업을 할당하여 accept를 기다릴 수 있도록 한다. 그 동안 main에서는 다른 작업을 할 수 있다는 장점이 생기며
//객체지향적으로 책임이 분리된다는 장점도 생긴다.
public class WebServer {
    static private final int DEFAULT_PORT = 8080;
    static private final int DEFAULT_POOL_SIZE = 200;
    //톰캣을 따라 만드는게 목적이기 때문에 톰캣의 스레드풀 맥스 사이즈를 사용함

    public static void main(String[] args) throws IOException {
        int port = DEFAULT_PORT;
        int poolSize = DEFAULT_POOL_SIZE;
        if (args != null && args.length != 0) {
            if (args.length == 1) {
                port = Integer.parseInt(args[0]);
            } else if (args.length == 2) {
                port = Integer.parseInt(args[0]);
                poolSize = Integer.parseInt(args[1]);
            }
        }
        Thread thread = new Thread(new NetworkService(port, poolSize));
        thread.start();
    }
    //webserver layer이므로 해당 메서드에서 요청/응답 객체를 생성하여 어플리케이션 레이어에서 처리하고 가져와야함
    // -> 그리고 webserver layer가 응답의 책임을 갖기 때문이다.
//    static public void start(Socket socket) throws IOException {
//        Router router = Router.getInstance();
//        InputStream in = socket.getInputStream();
//        HttpRequest http = HttpParserUtils.parse(in);
//        HttpResponse response = new HttpResponse();
//        router.route(http, response);
//    }
}
