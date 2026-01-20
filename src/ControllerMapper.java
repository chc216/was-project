import controller.Controller;
import controller.DummyController;

import java.util.HashMap;

public class ControllerMapper {
    static private final HashMap<String, Controller> mapper = new HashMap<>();
    public ControllerMapper() {
        mapper.put("/", new DummyController());
    }

    public Controller getController(String url) {
        if (!mapper.containsKey(url)) {
            return null;
        }
        return mapper.get(url);
    }

}
