public enum HttpRequestMethod {
    GET,POST, PUT, PATCH, DELETE;

    public static HttpRequestMethod httpMethodMapping(String str) {
        return switch (str) {
            case "POST" -> HttpRequestMethod.POST;
            case "GET" -> HttpRequestMethod.GET;
            case "PUT" -> HttpRequestMethod.PUT;
            case "PATCH" -> HttpRequestMethod.PATCH;
            case "DELETE" -> HttpRequestMethod.DELETE;
            default -> throw new RuntimeException("request method that cant handle");
        };
    }
}
