//목표: 프로그램을 실행하고 브라우저로 localhost:8080에 접속했을 때, 프로그램이 꺼지지 않고 뭔가 반응이 있게 하기.


import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) throws Exception{
        int port = 8080;
        if (args == null || args.length == 0 ) {
            port = Integer.parseInt(args[0]);
        }
        ServerSocket serverSocket = new ServerSocket(port);
        while(true) {
            Socket socket = serverSocket.accept();
            try {

            } catch (Exception e) {
                String msg = e.getMessage();
                System.out.println(msg);
            } finally {
                socket.close();
            }

        }
    }
}
