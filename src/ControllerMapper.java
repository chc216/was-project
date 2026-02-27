import controller.Controller;
import controller.DummyController;
import controller.StaticFileHandler;

import java.util.HashMap;

public class ControllerMapper {
    private final HashMap<String, Controller> mapper = new HashMap<>();
    private final StaticFileHandler staticFileHandler = new StaticFileHandler();
    public ControllerMapper() {
        mapper.put("/", new DummyController());
    }

    public Controller getController(String url) {
        if (url.contains(".")) {
            return staticFileHandler;
        } else {
            if (!mapper.containsKey(url)) {
                return null;
            }
            return mapper.get(url);
        }
    }
}
