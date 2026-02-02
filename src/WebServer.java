//목표: 프로그램을 실행하고 브라우저로 localhost:8080에 접속했을 때, 프로그램이 꺼지지 않고 뭔가 반응이 있게 하기.


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    static private final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws IOException {
        int port = DEFAULT_PORT;
        if (args != null && args.length != 0 ) {
            port = Integer.parseInt(args[0]);
        }
        ServerSocket serverSocket = new ServerSocket(port);
        while(true) {
            try{
                //try with resource문을 사용하면 안되는 이유는 소켓 close는 각 쓰레드 내부에서 호출해야함
                //try with resource문을 사용하면 쓰레드는 시작 됐는데 외부에서는 try문 안의 코드가 다 실행된 것으로 간주하여 자동 close호출해버림
                //쓰레드는 시작도안했늗ㄴ ㅔ
                Socket connection = serverSocket.accept();

                //동시 요청 처리가 필요한데 코드가 길어지기 때문에 + webserver책임이 많아지기 때문에-> start메서드를 따로 객체로 처리해야한다.
                //스레드 풀은 나중에 적용할 예정. 일단은 동시처리만 염두에 뒀음
                RequestHandler requestHandler = new RequestHandler(connection);
                Thread thread = new Thread(requestHandler);
                thread.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
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
