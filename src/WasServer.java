//목표: 프로그램을 실행하고 브라우저로 localhost:8080에 접속했을 때, 프로그램이 꺼지지 않고 뭔가 반응이 있게 하기.


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.StringTokenizer;

public class WasServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080);) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort());
                System.out.println(socket.getInetAddress());
                try {

                    HttpParser httpParser = new HttpParser(new HttpBodyParserFactory());
                    HttpRequest request = httpParser.parse(socket.getInputStream());
                    System.out.println("-------------request created-------------------");
                    System.out.println("request.getHttpRequestMethod() = " + request.getHttpRequestMethod());
                    System.out.println("request.getUrl() = " + request.getUrl());
                    System.out.println("request.getQueryParametersMap() = " + request.getQueryParametersMap());
                    System.out.println("request.getHeadersMap() = " + request.getHeadersMap());
                    System.out.println("request.getBodyMap() = " + request.getBodyMap());

                    OutputStream out = socket.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(out);
                    byte[] body = "hello".getBytes();
                    dataOutputStream.writeBytes("HTTP/1.1 200 OK\n");
                    dataOutputStream.writeBytes("Content-type:text/html\n");
                    dataOutputStream.writeBytes("Content-length:" + body.length + "\n\n");
                    dataOutputStream.write(body, 0, body.length);
                    dataOutputStream.flush();
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("close socket");
                    socket.close();
                } finally {
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
