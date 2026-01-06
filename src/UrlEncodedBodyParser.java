import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class UrlEncodedBodyParser implements RequestBodyParser {
    @Override
    public Map<String, String> parse(String body) {
        StringTokenizer stringTokenizer = new StringTokenizer(body, "&");
        Map<String, String> keyValueMap = new HashMap<>();
        while (stringTokenizer.hasMoreTokens()) {
            StringTokenizer keyValueTokenizer = new StringTokenizer(stringTokenizer.nextToken(), "=");
            String key = keyValueTokenizer.nextToken();
            String value = keyValueTokenizer.nextToken();
            keyValueMap.put(key, value);
        }
        return keyValueMap;
    }
}
