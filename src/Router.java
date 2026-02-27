import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;

public class Router {
    final static public Router instance = new Router();
    //컨트롤러매퍼에 의존 중이지만 추후 변경 가능성이 생긴다면 그때가서 인터페이스로 리팩토링을 하는게 더 유연하다.
    //멤버 변수에서 직접 객체를 생성하는 것 또한 외부에서 주입하는게 이상적이겠지만 규모가 크지 않기 때문에 내부에서 생성한다.
    final public ControllerMapper controllerMapper = new ControllerMapper();
    //router객체는 싱글톤이지만 내부 변수는 모두 싱글톤 객체의 생명주기를 따라가기 때문에 static으로 선언할 필요가 없다.

    private Router() {
    }

    public static Router getInstance() {
        return instance;
    }


    //라우터는 라우팅의 책임만 가져야한다. start 이런거 가질 필요없음
    public void route(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        Controller controller = controllerMapper.getController(url);
        if (controller == null) {
            response.setStatus(StatusCode.BAD_REQUEST);
        } else {
            controller.service(request, response);
        }
    }
}
