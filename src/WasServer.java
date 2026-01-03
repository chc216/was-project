//목표: 프로그램을 실행하고 브라우저로 localhost:8080에 접속했을 때, 프로그램이 꺼지지 않고 뭔가 반응이 있게 하기.


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class WasServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);

            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String head = br.readLine();
                if (head != null) {
                    System.out.println(head);
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
