import java.io.IOException;
import java.util.Scanner;


//수정 내용: main메서드는 진입점 역할만 해야하고, 따로 thread에 작업을 할당하여 accept를 기다릴 수 있도록 한다. 그 동안 main에서는 다른 작업을 할 수 있다는 장점이 생기며
//객체지향적으로 책임이 분리된다는 장점도 생긴다.
public class WebServer {
    final static private int DEFAULT_PORT = 8080;
    final static private int DEFAULT_POOL_SIZE = 200;
    //톰캣을 따라 만드는게 목적이기 때문에 톰캣의 스레드풀 맥스 사이즈를 사용했다.

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
        NetworkService service = new NetworkService(port, poolSize);
        Thread thread = new Thread(service);
        thread.start();
        System.out.println("서버가 시작되었습니다. q를 누르면 종료됩니다.");
        Scanner sc = new Scanner(System.in);
        if (sc.nextLine().equals("q")) {
            System.out.println("서버를 종료합니다.");
            service.stopService();
        }
    }
}
