import java.util.Map;

public interface HttpBodyParser {
    public Map<String, Object> parse(String body);
}
