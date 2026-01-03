//목표: 프로그램을 실행하고 브라우저로 localhost:8080에 접속했을 때, 프로그램이 꺼지지 않고 뭔가 반응이 있게 하기.


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.StringTokenizer;

public class WasServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort());
                System.out.println(socket.getInetAddress());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String line;
                while((line = bufferedReader.readLine()) != null && !line.isEmpty()){
                    System.out.println(line);
                }

                if(line == null) {
                    socket.close();
                    continue;
                }

                OutputStream out = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(out);
                byte[] body = "hello".getBytes();
                dataOutputStream.writeBytes("HTTP/1.1 200 OK\n");
                dataOutputStream.writeBytes("Content-type:text/html\n");
                dataOutputStream.writeBytes("Content-length:" + body.length + "\n\n");
                dataOutputStream.write(body, 0, body.length);
                dataOutputStream.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
