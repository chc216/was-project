import controller.Controller;
import controller.DummyController;
import controller.StaticFileController;

import java.util.HashMap;

public class ControllerMapper {
    private final HashMap<String, Controller> mapper = new HashMap<>();
    private final StaticFileController staticFileController = new StaticFileController();
    public ControllerMapper() {
        mapper.put("/", new DummyController());
        mapper.put("/*.*", new StaticFileController());
    }

    public Controller getController(String url) {
        if (url.contains(".")) {
            return mapper.get("/*.*");
        } else {
            if (!mapper.containsKey(url)) {
                return null;
            }
            return mapper.get(url);
        }
    }
}
