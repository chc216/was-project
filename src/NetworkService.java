import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkService implements Runnable {
    final private ServerSocket server;
    final private ExecutorService threadPool;

    public NetworkService(int port, int poolSize) throws IOException {
        server = new ServerSocket(port);
        threadPool = Executors.newFixedThreadPool(poolSize);
        //가장 기본적인 쓰레드 풀 구현체를 사용했다.
    }

    @Override
    public void run() {
        try {
            while (true) {
                threadPool.execute(new RequestHandler(server.accept()));
            }
        } catch (IOException e) {
            threadPool.shutdown();
        }
    }

    public void stopService() {
        try {
            //더 이상 요청이 들어오지 않게 먼저 소켓을 닫는다.
            server.close();
        } catch (IOException e) {
            System.out.println("소켓 닫는 중 문제 발생");
        }
        threadPool.shutdown();
    }
}
