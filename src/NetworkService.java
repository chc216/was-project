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
}
