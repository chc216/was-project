import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;

public class Router {
    public static final Router instance = new Router();
    //Controller매퍼에 의존중 -> 상관없음 만약 추후 변경 가능성이 생긴다면 그때가서 인터페이스로 리팩토링을 하는게 더 유연하다.
    public static final ControllerMapper controllerMapper = new ControllerMapper();
    //멤버변수를 굳이 static으로 해야할 이유를 모르겠음.. router객체 자체가 싱글톤이면 내부 변수가 인스턴스 객체여도 static하게 유지되는거 아님?
    //router가 싱글톤인데 싱글톤 객체 내부에 composition관계로 존재하는 객체는 싱글톤이어야하는가?
    //-> router의 생명주기에 따라가야하므로 그냥 인스턴스 객체로 존재하면 된다. (이래도 멤버 객체들은 싱글톤으로 존재할 수 있음)


    //굳이 멤버변수에 저장 후 -> 가져오는 패턴을 써야하는 이유를 모르겠음
    //이 패턴을 사용하지 않으면 객체에게 협력을 요청하기 위해서는 함수 내부에서 그때그때 new를 통해 생성 후 요청해야함 -> dip위반

    //굳이 싱글톤을 써야할 이유가 있나?
    // -> 요청마다 라우터를 생성할 필요가 없음 요청이 1000개 들어와도 라우팅 객체는 한개가 처리가능하므로 (저장하는 상태도 없음)
    // -> 쓰래드마다 요청 객체는 생기지만 라우팅 객체는 한개만 있으면 됨

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
