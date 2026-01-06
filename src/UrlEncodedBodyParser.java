import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class UrlEncodedBodyParser implements HttpBodyParser {
    @Override
    public Map<String, Object> parse(String body) {
        StringTokenizer stringTokenizer = new StringTokenizer(body, "&");
        Map<String, Object> keyValueMap = new HashMap<>();
        while (stringTokenizer.hasMoreTokens()) {
            StringTokenizer keyValueTokenizer = new StringTokenizer(stringTokenizer.nextToken(), "=");
            String key = keyValueTokenizer.nextToken();
            String value = keyValueTokenizer.nextToken();
            keyValueMap.put(key, value);
        }
        return keyValueMap;
    }
}
